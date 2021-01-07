package com.apache.fastandroid.demo.performance.practice

import android.os.Bundle
import android.view.LayoutInflater
import com.apache.fastandroid.R
import com.tesla.framework.common.util.log.FastLog
import com.tesla.framework.ui.fragment.ABaseFragment
import kotlinx.android.synthetic.main.performance_anr_watchdog.*

/**
 * Created by Jerry on 2020/12/28.
 */
class AnrWatchDogDemoFragment : ABaseFragment() {
    override fun inflateContentView(): Int {
        return R.layout.performance_anr_watchdog
    }

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceSate: Bundle?) {
        super.layoutInit(inflater, savedInstanceSate)
        btn_anr_watchdog.setOnClickListener {
            FastLog.d(TAG, "sleep begin")
            Thread.sleep(5000);
            FastLog.d(TAG, "sleep done")
//            NotifyManager.getInstance(Global.getContext()).showQiFuLampBlessNotify(Global.getContext());
        }
    }

}