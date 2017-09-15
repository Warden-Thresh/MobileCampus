package com.warden.mobilecampus.view.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.warden.mobilecampus.R;
import com.warden.mobilecampus.adapter.NewsListAdapter;
import com.warden.mobilecampus.bean.Recruitment;
import com.warden.mobilecampus.contract.NewsListContract;
import com.warden.mobilecampus.presenter.NewsListPresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link NewsListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewsListFragment extends Fragment implements NewsListContract.View {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private NewsListPresenter mNewsListPresenter;
    private NewsListAdapter adapter;
    private List recruitmentList = new ArrayList<>();
    Unbinder unbinder;
    @BindView(R.id.news_list_rv)
    RecyclerView newsListRv;
    @BindView(R.id.news_list_srl)
    SwipeRefreshLayout newsListSrl;
    @BindView(R.id.news_list_pb)
    ProgressBar newsListPb;
    @BindView(R.id.news_list_load_ll)
    LinearLayout newsListLoadLl;
    @BindView(R.id.news_list_retry_btn)
    Button newsListRetryBtn;
    private int page = 1;
    int lastVisibleItem = 0;
    LinearLayoutManager linearLayoutManager;

    // TODO: Rename and change types of parameters
    private String mHint;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public NewsListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param hint   栏目.
     * @return A new instance of fragment NewsListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NewsListFragment newInstance(String hint) {
        NewsListFragment fragment = new NewsListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, hint);
        //args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mHint = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_news_list, container, false);
        unbinder = ButterKnife.bind(this, view);
        mNewsListPresenter = new NewsListPresenter(this);
        mNewsListPresenter.loadData(mHint);
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void showView() {
        newsListLoadLl.setVisibility(View.GONE);

        newsListSrl.setVisibility(View.VISIBLE);
        newsListSrl.setProgressBackgroundColorSchemeResource(android.R.color.white);
        newsListSrl.setColorSchemeResources(android.R.color.holo_blue_light,
                android.R.color.holo_red_light, android.R.color.holo_orange_light,
                android.R.color.holo_green_light);
        newsListSrl.setProgressViewOffset(false, 0, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources().getDisplayMetrics()));
        newsListSrl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page = 1;
                mNewsListPresenter.loadAndRefresh(mHint);
            }
        });
        newsListRv.setVisibility(View.VISIBLE);
        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayout.VERTICAL, false);
        newsListRv.setLayoutManager(linearLayoutManager);
        newsListRv.setItemAnimator(new DefaultItemAnimator());
        adapter = new NewsListAdapter(getActivity(), recruitmentList,mHint);

        newsListRv.setAdapter(adapter);

        newsListRv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE&&lastVisibleItem+1==adapter.getItemCount()){
                    page += 1;
                    mNewsListPresenter.loadMoreData(mHint,page);
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItem =linearLayoutManager.findLastVisibleItemPosition();
            }
        });
    }

    @Override
    public void retry() {

    }

    @Override
    public void setList(List list) {
        recruitmentList = list;
    }

    @Override
    public void addList(List list) {
        adapter.addList(list);
    }

    @Override
    public void changeList(List list) {
        adapter.changeList(list);
        newsListSrl.setRefreshing(false);
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
