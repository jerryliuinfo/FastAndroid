package com.apache.fastandroid.demo.blacktech.permissionmonitor

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.provider.ContactsContract
import android.view.LayoutInflater
import com.apache.fastandroid.R
import com.tesla.framework.ui.fragment.BaseStatusFragmentNew
import kotlinx.android.synthetic.main.fragment_black_tech_permission_monitor.*

/**
 * Created by Jerry on 2022/1/15.
 */
class PermissionMonitorFragment:BaseStatusFragmentNew() {
    override fun inflateContentView(): Int {
        return R.layout.fragment_black_tech_permission_monitor
    }

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)


        tv_privacy_call.setOnClickListener {
            val host = Host()
            host.fuck()
            host.fuck(612)
            activity?.packageManager?.getInstalledPackages(0)
            var phoneCurosr = activity?.contentResolver?.query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                arrayOf(
                    ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                    ContactsContract.CommonDataKinds.Phone.NUMBER,
                    ContactsContract.CommonDataKinds.Phone.PHOTO_ID,
                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID,
                ), null, null, null
            )
            phoneCurosr?.moveToNext()

            val clip = activity?.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            clip.setPrimaryClip(ClipData.newPlainText("t","text"))
        }
    }

    override fun onResume() {
        super.onResume()



    }
}