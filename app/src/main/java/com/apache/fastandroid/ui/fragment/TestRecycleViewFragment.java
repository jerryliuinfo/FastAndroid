package com.apache.fastandroid.ui.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.apache.fastandroid.HeadItemViewHolder;
import com.apache.fastandroid.R;
import com.tesla.framework.network.task.TaskException;
import com.tesla.framework.support.bean.HeaderBean;
import com.tesla.framework.support.bean.HomeBean;
import com.tesla.framework.support.bean.HomeBeans;
import com.tesla.framework.support.bean.RefreshConfig;
import com.tesla.framework.support.inject.ViewInject;
import com.tesla.framework.support.paging.IPaging;
import com.tesla.framework.support.paging.index.IndexPaging;
import com.tesla.framework.support.sdk.TeslaSDK;
import com.tesla.framework.ui.fragment.ARecycleViewSwipeRefreshFragment;
import com.tesla.framework.ui.fragment.itemview.ARecycleViewItemViewHolder;
import com.tesla.framework.ui.fragment.itemview.IITemView;
import com.tesla.framework.ui.fragment.itemview.IItemViewCreator;
import com.tesla.framework.ui.fragment.itemview.header.AHeaderItemViewCreator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by jerryliu on 2017/4/3.
 */

public class TestRecycleViewFragment extends ARecycleViewSwipeRefreshFragment<HomeBean,ArrayList<HomeBean>,HeaderBean> {

    public static TestRecycleViewFragment newFragment() {
        Bundle args = new Bundle();
        TestRecycleViewFragment fragment = new TestRecycleViewFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private HeadItemViewHolder headItemViewHolder;


    @Override
    protected IPaging<HomeBean, ArrayList<HomeBean>> newPaging() {
        return new IndexPaging<>();
    }

    @Override
    protected void setUpRefreshConfig(RefreshConfig refreshConfig) {
        super.setUpRefreshConfig(refreshConfig);
        refreshConfig.positionKey = "main";
    }

    public void requestData(RefreshMode mode) {
        super.requestData(mode);
        if (mode == RefreshMode.refresh){
            mode = RefreshMode.reset;
        }
        new LoadDataTask(mode).execute();

    }

    @Override
    protected AHeaderItemViewCreator<HeaderBean> configHeadItemViewCreator() {
        return new AHeaderItemViewCreator<HeaderBean>() {
            @Override
            public int[][] setHeaders() {
                return new int[][]{{R.layout.home_item_header, 9}};
            }

            @Override
            public IITemView<HeaderBean> newItemView(View contentView, int viewType) {
                headItemViewHolder = new HeadItemViewHolder(getActivity(), contentView);
                return headItemViewHolder;
            }
        };
    }

    @Override
    protected IItemViewCreator<HomeBean> configItemViewCreator() {
        return new IItemViewCreator<HomeBean>() {
            @Override
            public View newContentView(LayoutInflater inflater, ViewGroup parent, int viewType) {
                return inflater.inflate(PersonItemView.LAY_RES,parent,false);
            }

            @Override
            public IITemView<HomeBean> newItemView(View contentView, int viewType) {
                return new PersonItemView(getActivity(), contentView);
            }
        };
    }
    
    
    static class PersonItemView extends ARecycleViewItemViewHolder<HomeBean>{
        private static final int LAY_RES = R.layout.item_person;
                @ViewInject(idStr = "tv_name")
         TextView tv_name;

        @ViewInject(idStr = "tv_age")
         TextView tv_age;
        public PersonItemView(Activity context, View itemView) {
            super(context, itemView);
        }

        @Override
        public void onBindData(View convertView, HomeBean data, int position) {
            tv_name.setText(data.name);
            tv_age.setText(data.age);
        }
    }
    


    class LoadDataTask extends APagingTask<Void,Void,ArrayList<HomeBean>>{

        public LoadDataTask(RefreshMode mode) {
            super(mode);
        }

        @Override
        protected List<HomeBean> parseResult(ArrayList<HomeBean> persons) {
            return persons;
        }

        @Override
        protected ArrayList<HomeBean> workInBackground(RefreshMode mode, String previousPage, String nextPage, Void... params) throws TaskException {
            SystemClock.sleep(2000);
            HomeBeans result = TeslaSDK.newInstance(getTaskCacheMode(this)).loadPerson(Integer.parseInt(nextPage), getRefreshConfig().pageSize);
            if (result != null){
                return result.beans ;
            }
            return  null;

        }

        @Override
        protected void onFinished() {
            super.onFinished();
            if (headItemViewHolder != null){
                HeaderBean headerBean = new HeaderBean("header:"+new Random().nextInt(20));
                headItemViewHolder.updateHeaderView(headerBean);
            }
        }
    }



}
