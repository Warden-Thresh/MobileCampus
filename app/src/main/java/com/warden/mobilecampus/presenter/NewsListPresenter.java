package com.warden.mobilecampus.presenter;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import com.google.gson.Gson;
import com.warden.mobilecampus.bean.Career;
import com.warden.mobilecampus.bean.Recruitment;
import com.warden.mobilecampus.contract.NewsListContract;
import com.warden.mobilecampus.util.HttpUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by Warden on 2017/9/13.
 */

public class NewsListPresenter implements NewsListContract.Presenter{
    private final int ON_SUCCESS = 0;
    private final int ON_FAILURE = 1;
    private NewsListContract.View mView;
    private List<Recruitment> list = new ArrayList<>();
    Handler mHandler = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case ON_SUCCESS:
                    mView.showView();
                    break;
                case ON_FAILURE:
                    mView.retry();
                    break;
                default:
                    super.handleMessage(msg);
                    break;
            }
        }
    };

    public NewsListPresenter(NewsListContract.View view) {
        mView = view;
    }
    @Override
    public void loadData(final String hint) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                getList(hint);
            }
        }).start();

    }

    @Override
    public void loadAndRefresh(String hint) {

    }

    @Override
    public void loadMoreData(String hint) {

    }

    private List<Recruitment> getList(String tab) {

        String url = "http://kmlg.bibibi.net/module/getcareers?start_page=1&keyword=&type=inner&day=&count=15&start=2&_=1505306588928";
        HttpUtil.sendOkHttpRequest(url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                mHandler.sendEmptyMessage(ON_FAILURE);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseText = response.body().string();
                Gson gson = new Gson();
                Career career = gson.fromJson(responseText, Career.class);
                list = career.getData();
                mView.setList(list);
                Log.d("GSON", ""+list.toString());
                mHandler.sendEmptyMessage(ON_SUCCESS);
            }
        });


        return list;
    }
}
