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
import com.warden.mobilecampus.model.CareerModel;
import com.warden.mobilecampus.model.ICareerModel;
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

public class NewsListPresenter implements NewsListContract.Presenter {


    private NewsListContract.View mView;
    private ICareerModel iCareerModel;
    private CareerModel.ModelListener listener;
    private List<Recruitment> recruitmentList = new ArrayList<>();

    public NewsListPresenter(NewsListContract.View view) {
        iCareerModel = new CareerModel();
        mView = view;
    }

    @Override
    public void loadData(String hint) {
        iCareerModel.getRecruitmentList(hint,1, new CareerModel.ModelListener() {
            @Override
            public void onSuccess(List<Recruitment> list) {
                mView.setList(list);
                mView.showView();
            }

            @Override
            public void onFailure() {
                mView.retry();
            }
        });
    }

    @Override
    public void loadAndRefresh(String hint) {
        iCareerModel.getRecruitmentList(hint,1, new CareerModel.ModelListener() {
            @Override
            public void onSuccess(List<Recruitment> list) {
                mView.changeList(list);
            }

            @Override
            public void onFailure() {

            }
        });
    }

    @Override
    public void showView() {
        mView.showView();
    }

    @Override
    public void loadMoreData(String hint,int page) {
        iCareerModel.getRecruitmentList(hint,page, new CareerModel.ModelListener() {
            @Override
            public void onSuccess(List<Recruitment> list) {
               mView.addList(list);
            }

            @Override
            public void onFailure() {
                mView.retry();
            }
        });
    }
}
