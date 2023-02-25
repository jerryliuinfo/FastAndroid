package com.apache.fastandroid.demo.domain

/**
 * Created by Jerry on 2023/2/23.
 */
object DomainConfig {

   fun getBaseNLpUrl():String{
      return getIEnvUrl().nlpUrl()
   }

    fun getBaseDiagnoseUrl():String{
        return getIEnvUrl().diagnosePUrl()
    }


    private fun getIEnvUrl():IEnvUrl{
        return when(System.getProperty("env")){
            "0" -> TestEnv()
            "1" -> PreProductEnv()
            else  -> ProductEnv()
        }
    }
}