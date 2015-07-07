package com.github.gfx.rxjavaexample;

import android.os.Handler;
import android.util.Log;

import rx.functions.Action1;
import rx.subjects.BehaviorSubject;

public class EventBusLike {

    static class ItemAddedEvent {

    }

    Handler handler = new Handler();

    BehaviorSubject<ItemAddedEvent> subject = BehaviorSubject.create();

    public void run() {
        runPublisher();
        runSubscriber();
    }

    void runPublisher() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.d("XXX", "start to send ItemAddedEvent");
                subject.onNext(new ItemAddedEvent());
                Log.d("XXX", "finished to send ItemAddedEvent");
            }
        }, 2000);
    }

    void runSubscriber() {
        subject.subscribe(new Action1<ItemAddedEvent>() {
            @Override
            public void call(ItemAddedEvent itemAddedEvent) {
                Log.d("XXX", "received: " + itemAddedEvent + ", thread: " + Thread.currentThread());
            }
        });
    }
}
