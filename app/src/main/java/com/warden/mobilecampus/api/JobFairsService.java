package com.warden.mobilecampus.api;

import com.warden.mobilecampus.bean.Career;
import com.warden.mobilecampus.bean.Recruitment;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Warden on 2017/9/14.
 */

public interface JobFairsService {
    @GET("getjobfairs?")
    Observable<Career<List<Recruitment>>> getjobfairs(@Query("type") String type,
                                                      @Query("day") String date,
                                                      @Query("count") int count,
                                                      @Query("start") int start);

    @GET("getjobfairs?")
    Observable<Career<List<Recruitment>>> getjobfairs(@Query("is_total" )int isTotal,
                                                      @Query("type") String type,
                                                      @Query("day") String date,
                                                      @Query("count") int count,
                                                      @Query("start") int start);
}
