package com.warden.mobilecampus.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.warden.mobilecampus.R;
import com.warden.mobilecampus.bean.Recruitment;

import java.util.List;

/**
 * Created by Warden on 2017/9/13.
 */

public class NewsListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public final int HEADER = 0;
    public final int NORMAL = 1;
    public final int MULTIIMAGE = 2;
    public final int FOOTER = 3;
    public static final int LOADING = 5;
    public static final int NO_MORE_DATA = 6;
    private int load_status = LOADING;
    private Context mContext;
    private List<Recruitment> mRecruitmentList;
    private View headerView;
    private String mHint;
    public NewsListAdapter(Context context, List<Recruitment> list,String hint){
        mContext = context;
        mHint = hint;
        mRecruitmentList = list;

    }

    public void setHeaderView(View headerView) {
        this.headerView = headerView;
        notifyItemInserted(0);
    }

    @Override
    public int getItemViewType(int position) {
        if (headerView != null && position == 0) {
            return HEADER;
        }
        if (position + 1 == getItemCount()) return FOOTER;
        return NORMAL;

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (headerView !=null && viewType == HEADER) {
            Log.d("holder","header");
            return new ViewHolderNormal(headerView);
        } else if (viewType ==FOOTER){
            View view =  LayoutInflater.from(mContext).inflate(R.layout.layout_footer, parent, false);
            return new ViewHolderFooter(view);
        }
        if (mHint.equals("双选会")) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_news_list_rv_jobfair, parent, false);
            return new ViewHolderNormal(view);
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_news_list_rv, parent, false);
        return new ViewHolderNormal(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == HEADER) {
            return;
        }
        if (getItemViewType(position) == FOOTER) {
            ViewHolderFooter holderFooter = (ViewHolderFooter) holder;
            switch (load_status) {
                case LOADING:
                    holderFooter.progressBar.setVisibility(View.VISIBLE);
                    holderFooter.text.setText("正在加载");
                    break;
                case NO_MORE_DATA:
                    holderFooter.progressBar.setVisibility(View.GONE);
                    holderFooter.text.setText("没有更多数据");
                    break;
            }
            return;
        }

        if (getItemViewType(position) == NORMAL) {
            final int pos = getRealPosition(holder);
            final Recruitment recruitment = mRecruitmentList.get(pos);
            ViewHolderNormal holderNormal = (ViewHolderNormal) holder;
            holderNormal.tv_title.setText(recruitment.getCompany_name());
            if (mHint.equals("双选会")){
                holderNormal.tv_title.setText(recruitment.getTitle());
            } else if (mHint.equals("正式岗位")){
                holderNormal.tv_title.setText(recruitment.getJob_name());
            }else if (mHint.equals("在线招聘")) {
                Glide.with(mContext).load(recruitment.getLogo_url()).into(holderNormal.iv_item_news_detail_rv);
            } else {
                Glide.with(mContext).load(recruitment.getLogo()).into(holderNormal.iv_item_news_detail_rv);
            }

            String detail = recruitment.getCompany_property()+"\n" +recruitment.getIndustry_category();
            String address = recruitment.getSchool_name() + "\n" + recruitment.getAddress();
            String date = recruitment.getMeet_day() + recruitment.getMeet_time();
            holderNormal.tv_time.setText(date);
            holderNormal.tv_detail.setText(detail);
            holderNormal.tv_author.setText(address);
            holderNormal.tv_zan.setText(recruitment.getView_count());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(mContext,recruitment.getCompany_name(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public void changeList(List<Recruitment> list) {
        mRecruitmentList.clear();
        mRecruitmentList.addAll(list);
        notifyDataSetChanged();
    }

    public void addList(List<Recruitment> list) {
        mRecruitmentList.addAll(list);
        if (list.size() < 1) {
            load_status = NO_MORE_DATA;
        }
        notifyDataSetChanged();

    }

    @Override
    public int getItemCount() {
        return headerView == null ? mRecruitmentList.size() + 1 : mRecruitmentList.size() + 2;
    }
    public class ViewHolderNormal extends RecyclerView.ViewHolder {
        private ImageView iv_item_news_detail_rv;
        private TextView tv_title;
        private TextView tv_author;
        private TextView tv_zan;
        private TextView tv_detail;
        private TextView tv_time;

        public ViewHolderNormal(View itemView) {
            super(itemView);
            if (itemView == headerView) return;
            tv_detail = (TextView) itemView.findViewById(R.id.tv_detail_news_detail);
            iv_item_news_detail_rv = (ImageView) itemView.findViewById(R.id.iv_item_news_detail_rv);
            tv_title = (TextView) itemView.findViewById(R.id.tv_title_news_detail);
            tv_author = (TextView) itemView.findViewById(R.id.tv_author_news_detail);
            tv_zan = (TextView) itemView.findViewById(R.id.tv_zan_news_detail);
            tv_time = (TextView) itemView.findViewById(R.id.tv_time_news_detail);
        }
    }


    public class ViewHolderFooter extends RecyclerView.ViewHolder {
        private TextView text;
        private ProgressBar progressBar;

        public ViewHolderFooter(View itemView) {
            super(itemView);
            text = (TextView) itemView.findViewById(R.id.tv_footer);
            progressBar = (ProgressBar) itemView.findViewById(R.id.progressBar2);
        }
    }
    private int getRealPosition(RecyclerView.ViewHolder holder) {
        int position = holder.getLayoutPosition();
        return headerView == null ? position : position - 1;
    }
}
