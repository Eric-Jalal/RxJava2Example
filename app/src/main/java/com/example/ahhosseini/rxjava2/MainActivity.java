package com.example.ahhosseini.rxjava2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.ahhosseini.rxjava2.network.CityResponse;
import com.example.ahhosseini.rxjava2.network.CityService;
import com.example.ahhosseini.rxjava2.network.Geoname;
import com.example.ahhosseini.rxjava2.network.RetrofitHelper;

import org.w3c.dom.Text;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {


    @NonNull
    private CityService mCityService;

    @NonNull
    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    private TextView mOutputTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mOutputTextView = (TextView) findViewById(R.id.output);
        mCityService = new RetrofitHelper().getCityService();

        requestGeonames();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mCompositeDisposable.clear();
    }

    private void displayGeonames(@NonNull final List<Geoname> geonames) {
        final StringBuilder output = new StringBuilder();
        for (final Geoname geoname : geonames) {
            output.append(geoname.name).append("\n");
        }
        mOutputTextView.setText(output.toString());
    }

    private void requestGeonames() {
        mCompositeDisposable.add(mCityService.queryGeoname(44.1, -9.9, -22.4, 55.2, "de")
                .subscribeOn(Schedulers.io()) // "work" on io thread
                .observeOn(AndroidSchedulers.mainThread()) // "listen" on UIThread
                .map(new Function<CityResponse, List<Geoname>>() {
                    @Override
                    public List<Geoname> apply(
                            @io.reactivex.annotations.NonNull final CityResponse cityResponse)
                            throws Exception {
                        // we want to have the geonames and not the wrapper object
                        return cityResponse.geonames;
                    }
                })
                .subscribe(new Consumer<List<Geoname>>() {
                    @Override
                    public void accept(
                            @io.reactivex.annotations.NonNull final List<Geoname> geonames)
                            throws Exception {
                        displayGeonames(geonames);
                    }
                })
        );
    }
}