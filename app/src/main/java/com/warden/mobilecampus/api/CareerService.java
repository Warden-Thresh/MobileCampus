package com.warden.mobilecampus.api;
import com.warden.mobilecampus.bean.Career;
import com.warden.mobilecampus.bean.Recruitment;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Warden on 2017/9/14.
 */

public interface CareerService {
    @GET("getcareers?")
    Observable<Career<List<Recruitment>>> getCareers(@Query("type") String type,
                                  @Query("day") String date,
                                  @Query("count") int count,
                                  @Query("start") int start);

    @GET("getcareers?")
    Observable<Career<List<Recruitment>>> getCareers(@Query("is_total" )int isTotal,
                                                     @Query("type") String type,
                                                     @Query("day") String date,
                                                     @Query("count") int count,
                                                     @Query("start") int start);
    @GET("getjobfairs?")
    Observable<Career<List<Recruitment>>> getjobfairs(@Query("count") int count,
                                                      @Query("start") int start);

    @GET("getjobfairs?")
    Observable<Career<List<Recruitment>>> getjobfairs(@Query("is_total" )int isTotal,
                                                      @Query("count") int count,
                                                      @Query("start") int start);
}
