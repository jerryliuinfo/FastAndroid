package com.apache.fastandroid.demo.designmode.decorator

import android.app.Application

/**
 * Created by Jerry on 2023/7/4.
 */
object FTakt {

    private val program = Program()

    fun prepare(application: Application): Program {
        return program.prepare(application)
    }

    fun play() {
        program.play()
    }

    fun finish() {
        program.stop()
    }

    class Program {
        fun prepare(application: Application): Program {
            return this
        }

        fun play() {

        }

        fun stop() {

        }
    }
}