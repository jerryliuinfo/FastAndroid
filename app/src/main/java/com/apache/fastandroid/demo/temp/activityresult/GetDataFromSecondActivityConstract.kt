package com.apache.fastandroid.demo.temp.activityresult

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract

/**
 * Created by Jerry on 2022/3/10.
 */
class GetDataFromSecondActivityConstract: ActivityResultContract<Void, String?>() {
    override fun createIntent(context: Context, input: Void?): Intent {
        return Intent(context,SecondActivityDemoActivity::class.java)
    }

    override fun parseResult(resultCode: Int, intent: Intent?): String? {
        if (resultCode == Activity.RESULT_OK){
            if (intent != null){
                return intent.getStringExtra("data")
            }
        }
        return null
    }
}