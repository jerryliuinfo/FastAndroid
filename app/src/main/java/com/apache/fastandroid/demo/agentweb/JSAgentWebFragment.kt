package com.apache.fastandroid.demo.agentweb

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.webkit.ValueCallback
import com.apache.fastandroid.R
import com.github.lzyzsd.jsbridge.BridgeWebView
import kotlinx.android.synthetic.main.fragment_agent_web_js.*
import org.json.JSONObject

/**
 * Created by Jerry on 2021/9/23.
 */
class JSAgentWebFragment:AgentWebFragment() {
    override fun inflateContentView(): Int {
        return R.layout.fragment_agent_web_js
    }
    private var mBridgeWebView: BridgeWebView? = null


    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)



        if (mAgentWeb != null) {
            //注入对象 JS调用Android代码
            mAgentWeb.jsInterfaceHolder.addJavaObject("android", AndroidInterface(mAgentWeb, this.activity))
        }

        callJsNoParamsButton.setOnClickListener {
            mAgentWeb.jsAccessEntrace.quickCallJs("callByAndroid")
        }
        callJsOneParamsButton.setOnClickListener {
            mAgentWeb.jsAccessEntrace.quickCallJs("callByAndroidParam", "Hello ! Agentweb")
        }
        callJsMoreParamsButton.setOnClickListener {
            mAgentWeb.jsAccessEntrace.quickCallJs("callByAndroidMoreParams",
                ValueCallback { value -> Log.i("Info", "value:$value") },
                getJson(),
                "say:",
                " Hello! Agentweb"
            )
        }
        jsJavaCommunicationButton.setOnClickListener {
            mAgentWeb.jsAccessEntrace.quickCallJs("callByAndroidInteraction", "你好Js")

        }

    }

    private fun getJson(): String? {
        var result = ""
        try {
            val mJSONObject = JSONObject()
            mJSONObject.put("id", 1)
            mJSONObject.put("name", "Agentweb")
            mJSONObject.put("age", 18)
            result = mJSONObject.toString()
        } catch (e: Exception) {
        }
        return result
    }



    override fun getUrl(): String {
        return "file:///android_asset/js_interaction/hello.html"
    }
}