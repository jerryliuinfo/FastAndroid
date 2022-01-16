package com.apache.fastandroid.demo;

import com.apache.fastandroid.demo.designmode.DesignModeDemoFragment;
import com.tesla.framework.ui.activity.FragmentContainerActivity;
import com.tesla.framework.ui.fragment.BaseFragment;

/**
 * Created by Jerry on 2021/9/8.
 */
class DemoFragment extends BaseFragment {
   @Override
   public int getLayoutId() {
      return 0;
   }

   class Proxy{
      void toMain(){
         FragmentContainerActivity.launch(DemoFragment.this.getActivity(), DesignModeDemoFragment.class,null);
      }
   }
}
