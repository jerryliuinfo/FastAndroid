package com.apache.fastandroid.demo.widget

import android.os.Bundle
import android.view.LayoutInflater
import com.apache.fastandroid.databinding.FragmentProgressViewBinding
import com.tesla.framework.ui.fragment.BaseBindingFragment

/**
 * Created by Jerry on 2022/5/1.
 */
class ProgressViewDemoFragment:BaseBindingFragment<FragmentProgressViewBinding>(FragmentProgressViewBinding::inflate){
    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)

        with(mBinding) {
            progressView.setOnProgressChangeListener { progressView.labelText = "heart ${it.toInt()}%" }
            progressView1.setOnProgressChangeListener { progressView1.labelText = "star ${it.toInt()}%" }
            progressView2.setOnProgressChangeListener {
                progressView2.labelText = "achieve ${it.toInt()}%"
            }
            progressView3.setOnProgressChangeListener {
                progressView3.labelText = "score ${it.toInt()}/100"
            }
            progressView4.setOnProgressChangeListener {
                progressView4.labelText = "achieve ${it.toInt()}%"
            }
            progressView5.setOnProgressChangeListener {
                progressView5.labelText = "achieve ${it.toInt()}%"
            }

           /* progressView.setOnProgressClickListener {
                customTagBalloon.showAlignTop(progressView.highlightView)
            }

            progressView1.setOnProgressClickListener {
                customStarBalloon.showAlignTop(progressView1.highlightView)
            }*/

            button.setOnClickListener {
                progressView.progress += 25
                progressView1.progress += 10
                progressView2.progress += 25
                progressView3.progress += 10
                progressView4.progress += 5
                progressView5.progress += 15
            }
        }
    }
}