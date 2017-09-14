package com.warden.mobilecampus.presenter;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import com.google.gson.Gson;
import com.warden.mobilecampus.api.CareerService;
import com.warden.mobilecampus.api.ServiceManager;
import com.warden.mobilecampus.bean.Career;
import com.warden.mobilecampus.bean.Recruitment;
import com.warden.mobilecampus.contract.NewsListContract;
import com.warden.mobilecampus.util.HttpUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Callback;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Scheduler;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Warden on 2017/9/13.
 */

public class NewsListPresenter implements NewsListContract.Presenter{

    public static final String INNER_RECRUITMENT_HINT = "校内宣讲会";
    public static final String OUTER_RECRUITMENT_HINT = "校外宣讲会";
    public static final String JOB_FAIRS_HINT = "双选会";
    public static final String ONLINE_RECRUITMENT = "在线招聘";
    public static final String JOBS_HINR = "正式岗位";
    private NewsListContract.View mView;
    private List<Recruitment> list = new ArrayList<>();


    public NewsListPresenter(NewsListContract.View view) {
        mView = view;
    }
    @Override
    public void loadData(final String hint) {
        List<Recruitment> list = getList(hint);
    }

    @Override
    public void loadAndRefresh(String hint) {

    }

    @Override
    public void loadMoreData(String hint) {

    }

    private List<Recruitment> getList(String tab) {
        switch (tab) {
            case INNER_RECRUITMENT_HINT:

        }

        ServiceManager.getInstance()
                .getCareerService()
                .getCareers("inner", "", 10, 1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Career<List<Recruitment>>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Career<List<Recruitment>> listCareer) {
                        mView.setList(listCareer.getData());
                        mView.showView();
                    }
                });
        return list;
    }
}
