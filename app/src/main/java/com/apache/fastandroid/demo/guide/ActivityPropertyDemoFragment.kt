package com.apache.fastandroid.demo.guide

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.CalendarContract
import android.view.LayoutInflater
import com.apache.fastandroid.databinding.ActivityPropertyBinding
import com.apache.fastandroid.demo.guide.activity.FinishOnTaskLaunchActivity
import com.tesla.framework.kt.launchActivity
import com.tesla.framework.kt.startActivity
import com.tesla.framework.ui.fragment.BaseBindingFragment
import java.util.*


class ActivityPropertyDemoFragment : BaseBindingFragment<ActivityPropertyBinding>(ActivityPropertyBinding::inflate) {

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)

        mBinding.btnFinishOnTaskLaunch.setOnClickListener {
            requireContext().startActivity<FinishOnTaskLaunchActivity>()
        }

        mBinding.btnManifestProperty.setOnClickListener {
            launchActivity<ManifestDemoActivity>(requireContext())
        }

        mBinding.btnIntentFilter.setOnClickListener {
            launchActivity<IntentFilterDemoActivity>(requireContext())
        }

        mBinding.btnDialPhone.setOnClickListener {
            buildDialPhoneIntent()
        }
        mBinding.btnQueryMap.setOnClickListener {
            buildQueryMapIntent()
        }
        mBinding.btnQueryWebPage.setOnClickListener {
            buildQueryWebPage()
        }

        mBinding.btnCreateCalendar.setOnClickListener {
            buildCreateCalendarIntent()
        }

    }

    private fun buildCreateCalendarIntent() {
        val calendarIntent = Intent(Intent.ACTION_INSERT, CalendarContract.Events.CONTENT_URI).apply {
            val beginTime: Calendar = Calendar.getInstance().apply {
                set(2021, 0, 23, 7, 30)
            }
            val endTime = Calendar.getInstance().apply {
                set(2021, 0, 23, 10, 30)
            }
            putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, beginTime.timeInMillis)
            putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endTime.timeInMillis)
            putExtra(CalendarContract.Events.TITLE, "Ninja class")
            putExtra(CalendarContract.Events.EVENT_LOCATION, "Secret dojo")
        }
        startActivity(calendarIntent)
    }

    private fun buildQueryWebPage() {
        val webIntent: Intent = Uri.parse("https://www.android.com").let { webpage ->
            Intent(Intent.ACTION_VIEW, webpage)
        }
        startActivity(webIntent)
    }

    private fun buildDialPhoneIntent(){
        val callIntent = Uri.parse("tel:5551234").let { number ->
            Intent(Intent.ACTION_DIAL, number)
        }
        startActivity(callIntent)
    }

    private fun buildQueryMapIntent(){
        val mapIntent: Intent = Uri.parse(
            "geo:0,0?q=1600+Amphitheatre+Parkway,+Mountain+View,+California"
        ).let { location ->
            // Or map point based on latitude/longitude
            // val location: Uri = Uri.parse("geo:37.422219,-122.08364?z=14") // z param is zoom level
            Intent(Intent.ACTION_VIEW, location)
        }
        try {
            startActivity(mapIntent)
        } catch (e: ActivityNotFoundException) {
        }
    }


}