package com.warden.mobilecampus.view.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupWindow;

import com.warden.mobilecampus.adapter.NewsAdapter;
import com.warden.mobilecampus.R;
import com.warden.mobilecampus.util.JobHint;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link NewsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String[] tabs ={"校内宣讲会","校外宣讲会","双选会","在线招聘","正式岗位"};
    @BindView(R.id.tab_news)
    TabLayout newsTab;
    @BindView(R.id.btn_tab_select)
    ImageButton btnTabSelect;
    @BindView(R.id.vp_news)
    ViewPager newsViewPage;
    private PopupWindow popupWindow;
    private boolean isPopupWindowShowing = false;
    private List<String> myChannels;
    private List<String> otherChannels;

    private NewsAdapter vpAdapter;

    private RecyclerView PopRecyclerView;
    Unbinder unbinder;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public NewsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NewsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NewsFragment newInstance(String param1, String param2) {
        NewsFragment fragment = new NewsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_news, container, false);
        unbinder = ButterKnife.bind(this, view);

        SharedPreferences sp = getActivity().getSharedPreferences("tabs", Context.MODE_PRIVATE);
        String tabString = sp.getString("tabs", tabsToString());
        tabs = stringToTabs(tabString);
        String otherString = sp.getString("others", "");
        String[] others = stringToTabs(otherString);
        myChannels = (tabs == null ? new ArrayList<String>(): Arrays.asList(tabs));
        otherChannels = (others == null ? new ArrayList<String>() : Arrays.asList(others));
        //tab效果
        vpAdapter = new NewsAdapter(getChildFragmentManager(),myChannels);
        newsViewPage.setAdapter(vpAdapter);
        newsViewPage.setOffscreenPageLimit(1);
        newsTab.setupWithViewPager(newsViewPage);
        if (myChannels.size() < 5) {
            newsTab.setTabMode(TabLayout.MODE_FIXED);
        }else {
            newsTab.setTabMode(TabLayout.MODE_SCROLLABLE);
        }

        btnTabSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
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
    private String tabsToString() {
        String s = "";
        for (String str : tabs)
            s += str + "-";
        return s;
    }

    private String[] stringToTabs(String tabString) {
        if (!tabString.equals(""))
            return tabString.split("-");
        else
            return null;
    }
}
