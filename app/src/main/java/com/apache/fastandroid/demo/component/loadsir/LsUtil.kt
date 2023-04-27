package com.apache.fastandroid.demo.component.loadsir

/**
 * Created by Jerry on 2023/4/16.
 */
object LsUtil {


    fun getTargetContext(target:Any, targetContextList:List<ILSTarget>):ILSTarget{
        for (targetContext in targetContextList){
            if (targetContext.isEquals(target)){
                return targetContext
            }
        }
        throw IllegalArgumentException("No TargetContext fit it")
    }
}