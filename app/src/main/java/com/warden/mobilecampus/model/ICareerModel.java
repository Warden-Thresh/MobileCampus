package com.warden.mobilecampus.model;

import com.warden.mobilecampus.bean.Recruitment;

import java.util.List;

/**
 * Created by Warden on 2017/9/14.
 */

public interface ICareerModel {
   void getRecruitmentList(String hint, CareerModel.ModelListener listener);
}
