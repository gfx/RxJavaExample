package com.github.gfx.rxjavaexample;

import com.github.gfx.rxjavaexample.api.ApiClient;
import com.github.gfx.rxjavaexample.api.ApiClientImpl;
import com.github.gfx.rxjavaexample.api.ApiResponse;
import com.github.gfx.rxjavaexample.api.Entries;

import android.text.TextUtils;
import android.util.Log;

import rx.functions.Action1;

public class SimpleActions {

    ApiClient apiClient = new ApiClientImpl();

    public void useCallbacks() {
        apiClient.getEntries(new ApiClient.ResponseListener<Entries>() {
            @Override
            public void onComplete(ApiResponse<Entries> r) {
                Log.d("XXX", "entry Ids: " + TextUtils.join(", ", r.content.entryIds));
            }
        });
    }

    public void useObservables() {
        apiClient.entries().subscribe(new Action1<ApiResponse<Entries>>() {
            @Override
            public void call(ApiResponse<Entries> r) {
                Log.d("XXX", "entry Ids: " + TextUtils.join(", ", r.content.entryIds));
            }
        });
    }
}
