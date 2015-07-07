package com.github.gfx.rxjavaexample;

import com.github.gfx.rxjavaexample.api.ApiClient;
import com.github.gfx.rxjavaexample.api.ApiClientImpl;
import com.github.gfx.rxjavaexample.api.ApiResponse;
import com.github.gfx.rxjavaexample.api.Entries;
import com.github.gfx.rxjavaexample.api.Entry;

import android.text.TextUtils;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;

public class DependentActions {

    ApiClient apiClient = new ApiClientImpl();

    public void useCallbacks() {
        apiClient.getEntries(new ApiClient.ResponseListener<Entries>() {
            @Override
            public void onComplete(ApiResponse<Entries> r) {
                final int count = r.content.entryIds.size();
                final List<Entry> entries = new ArrayList<>();

                for (String id : r.content.entryIds) {
                    apiClient.getEntry(id, new ApiClient.ResponseListener<Entry>() {
                        @Override
                        public void onComplete(ApiResponse<Entry> response) {
                            synchronized (entries) {
                                entries.add(response.content);
                                if (entries.size() == count) {
                                    // entriesをviewに反映させる
                                    Log.d("XXX", TextUtils.join(", ", entries));
                                }
                            }
                        }
                    });

                }
            }
        });
    }

    public void useObservables() {
        apiClient.entries().flatMap(new Func1<ApiResponse<Entries>, Observable<String>>() { // 1
            @Override
            public Observable<String> call(ApiResponse<Entries> r) {
                return Observable.from(r.content.entryIds);
            }
        }).flatMap(new Func1<String, Observable<ApiResponse<Entry>>>() { // 2
            @Override
            public Observable<ApiResponse<Entry>> call(String entryId) {
                return apiClient.entry(entryId);
            }
        }).map(new Func1<ApiResponse<Entry>, Entry>() { // 3
            @Override
            public Entry call(ApiResponse<Entry> r) {
                return r.content;
            }
        }).toList() // 4
                .subscribe(new Action1<List<Entry>>() { // 5
                    @Override
                    public void call(List<Entry> entries) {
                        // entriesをviewに反映させる
                        Log.d("XXX", TextUtils.join(", ", entries));
                    }
                });
    }

}
