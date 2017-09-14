package com.warden.mobilecampus.model;

import com.warden.mobilecampus.api.CareerService;
import com.warden.mobilecampus.api.ServiceManager;
import com.warden.mobilecampus.bean.Career;
import com.warden.mobilecampus.bean.Recruitment;

import java.util.List;

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
    public void getRecruitmentList(String hint, final ModelListener listener) {
        switch (hint) {
            case INNER_RECRUITMENT_HINT:
                careerService
                        .getCareers("inner", "", 10, 1)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<Career<List<Recruitment>>>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {
                                e.printStackTrace();
                            }

                            @Override
                            public void onNext(Career<List<Recruitment>> listCareer) {
                                listener.onSuccess(listCareer.getData());
                            }
                        });
                break;
            case OUTER_RECRUITMENT_HINT:
                careerService
                        .getCareers("outer", "", 10, 1)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<Career<List<Recruitment>>>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {
                                e.printStackTrace();
                            }

                            @Override
                            public void onNext(Career<List<Recruitment>> listCareer) {
                                listener.onSuccess(listCareer.getData());
                            }
                        });
                break;
            case JOB_FAIRS_HINT:
                careerService
                        .getjobfairs(10,1)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<Career<List<Recruitment>>>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {
                                e.printStackTrace();
                            }

                            @Override
                            public void onNext(Career<List<Recruitment>> listCareer) {
                                listener.onSuccess(listCareer.getData());
                            }
                        });
                break;
            case ONLINE_RECRUITMENT:
                break;
            case JOBS_HINR:
                break;
        }
    }


    public interface ModelListener {
        void onSuccess(List<Recruitment> list);

        void onFailure();
    }
}
