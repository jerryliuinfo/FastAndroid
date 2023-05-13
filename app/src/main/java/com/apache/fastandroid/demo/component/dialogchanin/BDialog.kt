package com.apache.fastandroid.demo.component.dialogchanin

import android.content.Context
import android.os.Bundle
import android.view.View
import com.apache.fastandroid.R
import com.csd.dialogchain.DialogChain

/**

 * Author：岑胜德 on 2021/11/26 21:42

 * 说明：

 */
class BDialog(context: Context) : BaseDialog(context), View.OnClickListener {
    private var data: String? = null

    // 这里注意：intercept(chain: DialogChain)方法与 onDataCallback(data: String)方法被调用的先后顺序是不确定的
    fun onDataCallback(data: String) {
        this.data = data
        tryToShow()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_b)
        findViewById<View>(R.id.tv_confirm)?.setOnClickListener(this)
        findViewById<View>(R.id.tv_cancel)?.setOnClickListener(this)
        // 弹窗消失时把请求移交给下一个拦截器。
        setOnDismissListener {
            chain()?.process()
        }
    }

    override fun onClick(p0: View?) {
        dismiss()
    }
   // 这里注意：intercept(chain: DialogChain)方法与 onDataCallback(data: String)方法被调用的先后顺序是不确定的
    override fun intercept(chain: DialogChain) {
        super.intercept(chain)
        tryToShow()
    }

    private fun tryToShow() {
        // 只有同时满足这俩条件才能弹出来。
        if (data != null && chain() != null) {
            this.show()
        }
    }
}