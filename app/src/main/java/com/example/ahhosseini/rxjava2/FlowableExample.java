package com.example.ahhosseini.rxjava2;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import io.reactivex.Flowable;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;

/**
 * Created by ahHosseini on 2/2/18.
 */

public class FlowableExample extends AppCompatActivity {

    private static final String TAG = FlowableExample.class.getSimpleName();

    Button btn;
    TextView textView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn = (Button) findViewById(R.id.main_helloWorld_btn);
        textView = (TextView) findViewById(R.id.main_helloWorld_textView);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doSomeWork();
            }
        });
    }

    private void doSomeWork() {

        Flowable<Integer> observable = Flowable.just(1, 2, 3, 4);

        observable.reduce(50, new BiFunction<Integer, Integer, Integer>() {
            @Override
            public Integer apply(Integer t1, Integer t2) {
                return t1 + t2;
            }
        }).subscribe(getObserver());
    }

    private SingleObserver<Integer> getObserver() {
        return new SingleObserver<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {

            }
            @Override
            public void onSuccess(Integer integer) {
                Log.i(TAG, "onSuccess " +integer);
            }
            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "onError" + e.getMessage());
            }
        };
    }
}
