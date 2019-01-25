package com.apache.fastandroid;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.apache.fastandroid.topic.support.IRecaculateFee;
import com.apache.fastandroid.topic.support.StandardPkgItemView;
import com.apache.fastandroid.topic.support.bean.StandardPkgBean;
import com.tesla.framework.support.bean.RefreshConfig;
import com.tesla.framework.ui.activity.FragmentArgs;
import com.tesla.framework.ui.activity.FragmentContainerActivity;
import com.tesla.framework.ui.fragment.ARecycleViewFragment;
import com.tesla.framework.ui.fragment.itemview.IITemView;
import com.tesla.framework.ui.fragment.itemview.IItemViewCreator;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 01370340 on 2018/11/7.
 */
public class TestRecycleViewActivity extends ARecycleViewFragment<StandardPkgBean,ArrayList<StandardPkgBean>,StandardPkgBean> implements IRecaculateFee {

    public static void launch(Activity from) {
        FragmentArgs args =  new FragmentArgs();
        FragmentContainerActivity.launch(from,TestRecycleViewActivity.class,args);
    }
    @Override
    public IItemViewCreator<StandardPkgBean> configItemViewCreator() {
        return new IItemViewCreator<StandardPkgBean>() {
            @Override
            public View newContentView(LayoutInflater inflater, ViewGroup parent, int viewType) {
                return inflater.inflate(R.layout.layout_item_standard,parent,false);
            }

            @Override
            public IITemView<StandardPkgBean> newItemView(View contentView, int viewType) {
                return new StandardPkgItemView(TestRecycleViewActivity.this.getActivity(),contentView,TestRecycleViewActivity.this);
            }
        };
    }

    @Override
    protected void setUpRefreshConfig(RefreshConfig refreshConfig) {
        super.setUpRefreshConfig(refreshConfig);

        refreshConfig.footerMoreEnable = false;
    }

    @Override
    protected void layoutInit(LayoutInflater inflater, Bundle savedInstanceSate) {
        super.layoutInit(inflater, savedInstanceSate);

        List<StandardPkgBean> list = new ArrayList<>();
        list.add(new StandardPkgBean("1号纸箱", 1));
        list.add(new StandardPkgBean("2号纸箱", 1));
        list.add(new StandardPkgBean("3号纸箱", 1));
        list.add(new StandardPkgBean("4号纸箱", 1));
        list.add(new StandardPkgBean("5号纸箱", 1));

        setItems(list);

        getAdapter().notifyDataSetChanged();
    }

    public TextView mTvTotalMoney;

    private TextView mTvTotalCount;

    @Override
    public void reCaculateFee() {
        for (StandardPkgBean standardPkgBean : getAdapterItems()) {
            Log.d("test", "standardPkgBean = "+standardPkgBean);
        }
    }
}
