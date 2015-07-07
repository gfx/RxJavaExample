package com.github.gfx.rxjavaexample.api;

import rx.Observable;

/**
 * API client interface。原稿用にインターフェイスを抜き出しています。
 */
public interface ApiClient {

    /**
     * /entries を呼んでエントリのIDリストを得る
     */
    void getEntries(ResponseListener<Entries> listener);

    /**
     * /entries/:id を呼んで特定のエントリの内容を得る
     */
    void getEntry(String id, ResponseListener<Entry> listener);

    interface ResponseListener<T> {

        void onComplete(ApiResponse<T> response);
    }

    // RxJava

    Observable<ApiResponse<Entries>> entries();

    Observable<ApiResponse<Entry>> entry(String id);
}
