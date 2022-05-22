package com.apache.fastandroid.demo.blacktech

import android.os.Bundle
import android.view.LayoutInflater
import com.apache.fastandroid.databinding.FragmentSenstiveWordBinding
import com.github.houbb.sensitive.word.api.IWordResult
import com.github.houbb.sensitive.word.core.SensitiveWordHelper
import com.github.houbb.sensitive.word.support.result.WordResultHandlers
import com.tesla.framework.ui.fragment.BaseVBFragment
import kotlin.system.measureTimeMillis


/**
 * Created by Jerry on 2022/4/15.
 * https://github.com/houbb/sensitive-word
 */
class SensitiveWordDemoFragment:BaseVBFragment<FragmentSenstiveWordBinding>(FragmentSenstiveWordBinding::inflate) {
    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)

        val costTime = measureTimeMillis {
            val text = "五星红旗迎风飘扬，毛主席的画像屹立在天安门前。"
            val contains = SensitiveWordHelper.contains(text)
            println("contains :${contains}")


            val findFirst = SensitiveWordHelper.findFirst(text)
            println("findFirst :${findFirst}")

            val result: IWordResult = SensitiveWordHelper.findFirst(text, WordResultHandlers.raw())
            println("word :${result.word()}, startIndex:${result.startIndex()}, endIndex:${result.endIndex()}")

            val allWordList = SensitiveWordHelper.findAll(text)
            println("allWordList :${allWordList}")


            val defaultReplace = SensitiveWordHelper.replace(text)
            println("defaultReplace :${defaultReplace}")


            val givenReplaceResult = SensitiveWordHelper.replace(text, '0');
            println("givenReplaceResult :${givenReplaceResult}")


        }
        println("costTime:${costTime}")



    }

    companion object{
        private const val TAG = "SensitiveWordDemoFragment"
    }




}