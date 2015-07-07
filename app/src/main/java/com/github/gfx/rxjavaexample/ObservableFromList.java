package com.github.gfx.rxjavaexample;

import android.text.TextUtils;
import android.util.Log;

import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.functions.Func2;
import rx.schedulers.Schedulers;

public class ObservableFromList {

    void run() {
        String[] s = {"10", "20", "30"};

        Observable.from(s)
                .map(new Func1<String, Double>() {
                    @Override
                    public Double call(String s) {
                        return Double.parseDouble(s);
                    }
                })
                .reduce(0.0, new Func2<Double, Double, Double>() {
                    @Override
                    public Double call(Double a, Double b) {
                        return a + b;
                    }
                })
                .subscribe(new Action1<Double>() {
                    @Override
                    public void call(Double x) {
                        Log.d("XXX", "value=" + x);
                    }
                });

        runWithSchedulers();
    }

    void runWithSchedulers() {
        Log.d("XXX", "thread id (main): " + Thread.currentThread().getId());

        String[] s = {"10", "20", "30"};

        Observable.from(s)
                .observeOn(Schedulers.computation())
                .map(new Func1<String, Double>() {
                    @Override
                    public Double call(String s) {
                        Log.d("XXX", "thread id (map): " + Thread.currentThread().getId());
                        return Double.parseDouble(s);
                    }
                })
                .reduce(0.0, new Func2<Double, Double, Double>() {
                    @Override
                    public Double call(Double a, Double b) {
                        Log.d("XXX", "thread id (reduce): " + Thread.currentThread().getId());
                        return a + b;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Double>() {
                    @Override
                    public void call(Double x) {
                        Log.d("XXX", "thread id (subscribe): " + Thread.currentThread().getId());

                        Log.d("XXX", "value=" + x);
                    }
                });

        runToBlocking();
    }

    void runToBlocking() {

        String[] s = {"10", "20", "30"};

        List<String> list = Observable.from(s).toList().toBlocking().single();
        Log.d("XXX", "toBlocking: " + TextUtils.join(", ", list));

        for (String item : Observable.from(s).toBlocking().toIterable()) {
            Log.d("XXX", "toBlocking().toIterable(): " + item);
        }
    }
}
