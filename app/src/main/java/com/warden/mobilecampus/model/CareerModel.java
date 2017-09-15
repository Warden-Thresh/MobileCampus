package com.warden.mobilecampus.model;

import android.util.Log;

import com.warden.mobilecampus.api.CareerService;
import com.warden.mobilecampus.api.ServiceManager;
import com.warden.mobilecampus.bean.Career;
import com.warden.mobilecampus.bean.Recruitment;

import java.util.List;


import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Warden on 2017/9/14.
 */

public class CareerModel implements ICareerModel {
    private static final String INNER_RECRUITMENT_HINT = "校内宣讲会";
    private static final String OUTER_RECRUITMENT_HINT = "校外宣讲会";
    private static final String JOB_FAIRS_HINT = "双选会";
    private static final String ONLINE_RECRUITMENT = "在线招聘";
    private static final String JOBS_HINR = "正式岗位";
    private List<Recruitment> list;
    private ServiceManager serviceManager = ServiceManager.getInstance();
    private CareerService careerService = serviceManager.getCareerService();
    @Override
    public void getRecruitmentList(String hint,int page, final ModelListener listener) {
        Log.d("Request", "hint:" + hint + "\npage:" + page);
        Subscriber subscriber = new Subscriber<Career>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                listener.onFailure();
            }

            @Override
            public void onNext(Career listCareer) {
                listener.onSuccess(listCareer.getData());
                Log.d("log:", listCareer.getCode() + "");
            }
        };

        if (hint.equals(INNER_RECRUITMENT_HINT) || hint.equals(OUTER_RECRUITMENT_HINT)) {
            String type = hint.equals(INNER_RECRUITMENT_HINT) ? "inner" : "outer";
            careerService
                    .getCareers(type, "",1, 10, page)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(subscriber);

        } else if (hint.equals(JOB_FAIRS_HINT)) {
            careerService
                    .getjobfairs(1,10,page)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(subscriber);
        } else if (hint.equals(ONLINE_RECRUITMENT)) {
            careerService
                    .getOnlines(1, 10, page)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(subscriber);

        } else if (hint.equals(JOBS_HINR)) {
            careerService
                    .getJobs(1, 10, page)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(subscriber);

        }
    }


    public interface ModelListener {
        void onSuccess(List<Recruitment> list);

        void onFailure();
    }

    private void sendRequest(String hint) {


    }
}
