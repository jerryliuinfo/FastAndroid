package com.apache.fastandroid.demo.lyric

import android.os.Bundle
import android.view.LayoutInflater
import androidx.lifecycle.lifecycleScope
import com.apache.fastandroid.R
import com.apache.fastandroid.databinding.FragmentLyricBinding
import com.tesla.framework.ui.fragment.BaseBindingFragment
import kotlinx.coroutines.*

/**
 * Created by Jerry on 2022/3/20.
 */
class LyricDemoFragment:BaseBindingFragment<FragmentLyricBinding>(FragmentLyricBinding::inflate) {

    private var sentenceIndex = 0

    private var job:Job ?= null

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)



        mBinding.btnPlay.setOnClickListener {
            mBinding.lyricView.run {
                val lyric = LyricUtils.parseLyric(resources.openRawResource(R.raw.testfile), "UTF-8")
                setLyric(lyric)
                setLyricIndex(0)
                play()
            }
        }

        lifecycleScope.launch {
            while (isActive){
                try {
                    mBinding.lyricView.setLyricIndex(sentenceIndex++)
                }catch (e:Exception){
                    e.printStackTrace()
                    if (e is CancellationException) throw e
                }

                delay(500)
            }
        }


    }
}