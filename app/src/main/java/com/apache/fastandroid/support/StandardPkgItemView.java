package com.apache.fastandroid.support;

import android.app.Activity;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import com.apache.fastandroid.R;
import com.apache.fastandroid.support.bean.StandardPkgBean;
import com.tesla.framework.common.util.SimpleTextWatcher;
import com.tesla.framework.ui.fragment.itemview.ARecycleViewItemViewHolder;

/**
 * Created by 01370340 on 2018/11/7.
 */
public class StandardPkgItemView extends ARecycleViewItemViewHolder<StandardPkgBean> {

    CheckBox mCbSelect;
    EditText mEtNum;
    TextView mTvAdd;

    private IRecaculateFee iRecaculateFee;
    public StandardPkgItemView(Activity context, View itemView,IRecaculateFee iRecaculateFee) {
        super(context, itemView);
        mCbSelect = (CheckBox) itemView.findViewById(R.id.checkbox);
        mEtNum = (EditText) itemView.findViewById(R.id.et_num);
        mTvAdd = (TextView) itemView.findViewById(R.id.item_add_img);
        this.iRecaculateFee = iRecaculateFee;
    }

    @Override
    public void onBindData(View convertView, final StandardPkgBean data, int position) {
        mCbSelect.setText(data.getName());
        mEtNum.setText(String.valueOf(data.getNum()));
        mCbSelect.setChecked(data.isSelected());
        mEtNum.addTextChangedListener(new SimpleTextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(s)){
                    int num = Integer.parseInt(s.toString());
                    if (num < 1){//纸箱默认数量为1
                        num = 1;
                        mEtNum.setText(String.valueOf(1));

                    }
                    data.setNum(num);
                    if (num == 1){//防止重置成1时光标跑到初始位置
                        mEtNum.setSelection(1);
                    }
                    else if (num > 1){//数量大于1时，自动勾选
                        data.setSelected(true);
                        mCbSelect.setChecked(true);
                    }
                }
                reCaculateFee();

            }
        });

        mTvAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* int num = mPackings.get(getLayoutPosition()).getNum() + 1;
                mPackings.get(getLayoutPosition()).setNum(num);
                mEtNum.setText(String.valueOf(mPackings.get(getLayoutPosition()).getNum()));
                reCaculateFee();*/


                data.setNum(data.getNum() + 1);
                mEtNum.setText(String.valueOf(data.getNum()));



            }
        });
    }

    private void reCaculateFee(){
        if (iRecaculateFee != null){
            iRecaculateFee.reCaculateFee();
        }
    }
}
