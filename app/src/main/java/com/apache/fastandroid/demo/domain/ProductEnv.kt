package com.apache.fastandroid.demo.domain

/**
 * Created by Jerry on 2023/2/23.
 */
class ProductEnv:IEnvUrl {
    override fun nlpUrl(): String {
        return "www.nlp.com"
    }

    override fun diagnosePUrl(): String {
        return "www.diagnose.com"
    }
}