package com.apache.fastandroid.demo.blacktech.encrypt

import io.michaelrocks.paranoid.Obfuscate

/**
 * Created by Jerry on 2022/4/28.
 * //https://github.com/MichaelRocks/paranoid

 */
@Obfuscate
class ParanoidSample {

    fun text1():String{
        return String.format(QUESTION, "How does it work?")
    }

    fun text2():String{
        return String.format(ANSWER, "It's magic?")
    }

    companion object {
        private const val QUESTION = "Q:\r\n%s"
        private const val ANSWER = "A:\r\n%s"
    }
}