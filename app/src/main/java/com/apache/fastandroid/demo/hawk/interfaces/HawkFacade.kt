package com.apache.fastandroid.demo.hawk.interfaces

import java.lang.IllegalStateException

/**
 * Created by Jerry on 2022/1/20.
 */
interface HawkFacade {

    fun <T> put(key:String, value:T):Boolean

    fun <T> get(key: String):T?

    fun <T> get(key: String, defaultValue:T):T?

    fun delete(key: String):Boolean

    fun deleteAll():Boolean


    fun contains(key: String):Boolean

    fun count():Long

    fun isBuilt():Boolean

    fun destroy()



    class EmptyHawkFacade: HawkFacade {
        override fun <T> put(key: String, value: T): Boolean {
            throwValidation()
            return false
        }

        override fun <T> get(key: String): T? {
            throwValidation()
            return null
        }

        override fun <T> get(key: String, defaultValue: T): T? {
            throwValidation()
            return null
        }

        override fun delete(key: String): Boolean {
            throwValidation()
            return false
        }

        override fun deleteAll(): Boolean {
            throwValidation()
            return false
        }

        override fun contains(key: String): Boolean {
            throwValidation()
            return false
        }

        override fun count(): Long {
            throwValidation()
            return 0
        }

        override fun isBuilt(): Boolean {
            throwValidation()
            return false
        }

        override fun destroy() {
        }


        private fun throwValidation(){
            throw IllegalStateException("Hawk#build is not called ,please call build and wait for the initiatation finished")
        }
    }
}