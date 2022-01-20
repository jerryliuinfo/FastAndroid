package com.apache.fastandroid.demo.hawk

import android.content.Context
import com.apache.fastandroid.demo.hawk.impl.DefaultHawkFacade
import com.apache.fastandroid.demo.hawk.interfaces.HawkFacade
import com.tesla.framework.common.util.Preconditions
import com.xuexiang.xui.utils.Utils.checkNull

/**
 * Created by Jerry on 2022/1/19.
 */
object Hawk {
    private fun Hawk() {
        // no instance
    }

    var hawkFacade: HawkFacade? = HawkFacade.EmptyHawkFacade()

    /**
     * This will init the hawk without password protection.
     *
     * @param context is used to instantiate context based objects.
     * ApplicationContext will be used
     */
    fun init(context: Context): XHawkBuilder {
        Preconditions.checkNotNull( context,"Context")
        hawkFacade = null
        return XHawkBuilder(context)
    }

    fun build(hawkBuilder: XHawkBuilder?) {
        hawkFacade = DefaultHawkFacade(hawkBuilder)
    }

    /**
     * Saves any type including any collection, primitive values or custom objects
     *
     * @param key   is required to differentiate the given data
     * @param value is the data that is going to be encrypted and persisted
     * @return true if the operation is successful. Any failure in any step will return false
     */
    fun <T> put(key: String?, value: T): Boolean {
        return hawkFacade!!.put(key!!, value)
    }

    /**
     * Gets the original data along with original type by the given key.
     * This is not guaranteed operation since Hawk uses serialization. Any change in in the requested
     * data type might affect the result. It's guaranteed to return primitive types and String type
     *
     * @param key is used to get the persisted data
     * @return the original object
     */
    operator fun <T> get(key: String?): T? {
        return hawkFacade!!.get(key!!)
    }

    /**
     * Gets the saved data, if it is null, default value will be returned
     *
     * @param key          is used to get the saved data
     * @param defaultValue will be return if the response is null
     * @return the saved object
     */
    operator fun <T> get(key: String?, defaultValue: T): T? {
        return hawkFacade!!.get(key!!, defaultValue)
    }

    /**
     * Size of the saved data. Each key will be counted as 1
     *
     * @return the size
     */
    fun count(): Long {
        return hawkFacade!!.count()
    }

    /**
     * Clears the storage, note that crypto data won't be deleted such as salt key etc.
     * Use resetCrypto in order to deleteAll crypto information
     *
     * @return true if deleteAll is successful
     */
    fun deleteAll(): Boolean {
        return hawkFacade!!.deleteAll()
    }

    /**
     * Removes the given key/value from the storage
     *
     * @param key is used for removing related data from storage
     * @return true if delete is successful
     */
    fun delete(key: String?): Boolean {
        return hawkFacade!!.delete(key!!)
    }

    /**
     * Checks the given key whether it exists or not
     *
     * @param key is the key to check
     * @return true if it exists in the storage
     */
    operator fun contains(key: String?): Boolean {
        return hawkFacade!!.contains(key!!)
    }

    /**
     * Use this method to verify if Hawk is ready to be used.
     *
     * @return true if correctly initialised and built. False otherwise.
     */
    fun isBuilt(): Boolean {
        return hawkFacade!!.isBuilt()
    }

    fun destroy() {
        hawkFacade!!.destroy()
    }
}