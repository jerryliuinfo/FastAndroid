package com.apache.fastandroid.demo.widget.lyric

import android.content.res.AssetFileDescriptor
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import android.widget.TextView
import androidx.fragment.app.viewModels
import com.apache.fastandroid.R
import com.apache.fastandroid.databinding.FragmentLyric2Binding
import com.blankj.utilcode.util.ToastUtils
import com.blankj.utilcode.util.Utils
import com.tesla.framework.ui.fragment.BaseBindingFragment
import java.io.IOException


class LyricFragment: BaseBindingFragment<FragmentLyric2Binding>(FragmentLyric2Binding::inflate){

    private val mMediaPlayer = MediaPlayer()
    private lateinit var mLrcView: LrcView
    private val mHandler = Handler()
    private lateinit var mSeekBar: SeekBar
    private lateinit var mTvStart: TextView
    private lateinit var mTvEnd: TextView

    private val viewModel:LyricViewModel by viewModels()


    private fun play() {
        try {
            val descriptor: AssetFileDescriptor = Utils.getApp().getAssets().openFd("Rolling In The Deep.mp3")
            mMediaPlayer.setDataSource(descriptor.fileDescriptor)
            mMediaPlayer.setOnPreparedListener {
                mMediaPlayer.start()
                mSeekBar!!.max = mMediaPlayer.duration
                mTvEnd.setText(LrcHelper.formatTime(mMediaPlayer.duration.toLong()))
            }
            mMediaPlayer.prepareAsync()
            mHandler.post(mRunnable)
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private val mRunnable: Runnable = object : Runnable {
        override fun run() {
            val currentPosition = mMediaPlayer.currentPosition
            mLrcView.updateTime(currentPosition.toLong())
            mSeekBar!!.progress = currentPosition
            mTvStart.setText(LrcHelper.formatTime(currentPosition.toLong()))
            mHandler.postDelayed(this, 100)
        }
    }

    private fun init() {
//        val lrcs: List<Lrc> = LrcHelper.parseLrcFromAssets(context, "Rolling in the Deep-Adele.lrc")
        mLrcView = findViewById(R.id.lrc_view)
        mSeekBar = findViewById(R.id.seek_play)
        mTvStart = findViewById(R.id.tv_start)
        mTvEnd = findViewById(R.id.tv_end)
        mBinding.btnPlay.setOnClickListener {
            ToastUtils.showShort("begin play")

            mMediaPlayer.start()
            mLrcView.resume()
        }
        mBinding.btnPause.setOnClickListener {
            ToastUtils.showShort("begin pause")
            mMediaPlayer.pause()
            mLrcView.pause()
        }
        viewModel.list.observe(this){
            mLrcView.setLrcData(it)
        }

        viewModel.loadData()



        mLrcView.setOnPlayIndicatorLineListener(object : LrcView.OnPlayIndicatorLineListener {

            override fun onPlay(time: Long, content: String?) {
                mMediaPlayer.seekTo(time.toInt())
            }
        })
        mSeekBar.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    mHandler.removeCallbacks(mRunnable)
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {}
            override fun onStopTrackingTouch(seekBar: SeekBar) {
                mHandler.post(mRunnable)
                mMediaPlayer.seekTo(seekBar.progress)
            }
        })
    }


    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)

        init()
        play()
    }





}