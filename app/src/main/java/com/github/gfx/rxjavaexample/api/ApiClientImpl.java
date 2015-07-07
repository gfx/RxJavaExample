package com.github.gfx.rxjavaexample.api;

import android.os.Handler;
import android.os.Looper;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import rx.Observable;
import rx.Subscriber;

/**
 * APiClientの実装です。
 */
public class ApiClientImpl implements ApiClient {

    final Handler handler = new Handler(Looper.getMainLooper());

    Map<String, Entry> entries = new HashMap<>();

    {
        entries.put("1", new Entry("1", "The First Entry", "Hello, world! #1"));
        entries.put("2", new Entry("2", "The Second Entry", "Hello, world! #2"));
        entries.put("3", new Entry("3", "The Third Entry", "Hello, world! #3"));
    }

    @Override
    public void getEntries(final ResponseListener<Entries> listener) {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Entries entries = new Entries(0, 100, Arrays.asList("1", "2", "3"));
                listener.onComplete(new ApiResponse<>(entries));
            }
        }, 100);
    }

    @Override
    public void getEntry(final String id, final ResponseListener<Entry> listener) {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Entry entry = entries.get(id);
                listener.onComplete(new ApiResponse<>(entry));
            }
        }, 100);
    }

    // RxJava

    public Observable<ApiResponse<Entries>> entries() {
        return Observable.create(new Observable.OnSubscribe<ApiResponse<Entries>>() {
            @Override
            public void call(final Subscriber<? super ApiResponse<Entries>> subscriber) {
                getEntries(new ApiClient.ResponseListener<Entries>() {
                    @Override
                    public void onComplete(ApiResponse<Entries> response) {
                        subscriber.onNext(response);
                        subscriber.onCompleted();
                    }
                });
            }
        });
    }

    public Observable<ApiResponse<Entry>> entry(final String id) {
        return Observable.create(new Observable.OnSubscribe<ApiResponse<Entry>>() {
            @Override
            public void call(final Subscriber<? super ApiResponse<Entry>> subscriber) {
                getEntry(id, new ApiClient.ResponseListener<Entry>() {
                    @Override
                    public void onComplete(ApiResponse<Entry> response) {
                        subscriber.onNext(response);
                        subscriber.onCompleted();
                    }
                });
            }
        });
    }


}
