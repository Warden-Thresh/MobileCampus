package com.warden.mobilecampus.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
    private Context mContext;
    private List<Recruitment> mRecruitmentList;
    private View headerView;
    public NewsListAdapter(Context context, List<Recruitment> list){
        mContext = context;
        mRecruitmentList = list;
    }

    public void setHeaderView(View headerView) {
        this.headerView = headerView;
        notifyItemInserted(0);
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) return HEADER;
        if (position + 1 == getItemCount()) return FOOTER;
        return NORMAL;

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (headerView !=null && viewType == HEADER) {
            return new ViewHolderNormal(headerView);
        } else if (viewType ==FOOTER){
            TextView view = (TextView) LayoutInflater.from(mContext).inflate(R.layout.layout_footer, parent, false);
            return new ViewHolderFooter(view);
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_news_list_rv, parent, false);
        return new ViewHolderNormal(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == HEADER) {
            return;
        }
        final int pos = getRealPosition(holder);
        final Recruitment recruitment = mRecruitmentList.get(pos);
        if (getItemViewType(position) == NORMAL) {
            ViewHolderNormal holderNormal = (ViewHolderNormal) holder;
            holderNormal.tv_title.setText(recruitment.getCompany_name());
            holderNormal.tv_author.setText(recruitment.getAddress());
            holderNormal.tv_zan.setText(recruitment.getView_count());
            Glide.with(mContext).load(recruitment.getLogo()).into(holderNormal.iv_item_news_detail_rv);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext,recruitment.getCompany_name(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void updateList(List<Recruitment> list) {
        mRecruitmentList.clear();
        mRecruitmentList.addAll(list);
        notifyDataSetChanged();
    }

    public void addList(List<Recruitment> list) {
        mRecruitmentList.addAll(list);
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

        public ViewHolderNormal(View itemView) {
            super(itemView);
            if (itemView == headerView) return;
            iv_item_news_detail_rv = (ImageView) itemView.findViewById(R.id.iv_item_news_detail_rv);
            tv_title = (TextView) itemView.findViewById(R.id.tv_title_news_detail);
            tv_author = (TextView) itemView.findViewById(R.id.tv_author_news_detail);
            tv_zan = (TextView) itemView.findViewById(R.id.tv_zan_news_detail);
        }
    }


    public class ViewHolderFooter extends RecyclerView.ViewHolder {
        private TextView text;

        public ViewHolderFooter(View itemView) {
            super(itemView);
            text = (TextView) itemView;
        }
    }
    private int getRealPosition(RecyclerView.ViewHolder holder) {
        int position = holder.getLayoutPosition();
        return headerView == null ? position : position - 1;
    }
}
