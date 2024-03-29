package com.warden.mobilecampus.contract;

import java.util.List;

/**
 * Created by Warden on 2017/9/13.
 */

public interface NewsListContract {
    interface View<T>{
        void showView();

        void retry();

        void setList(List<T> list);

        void addList(List<T> list);

        void changeList(List<T> list);


    }
    interface Presenter{
        void loadData(String hint);

        void loadAndRefresh(String hint);

        void showView();

        void loadMoreData(String hint,int page);
    }
}
