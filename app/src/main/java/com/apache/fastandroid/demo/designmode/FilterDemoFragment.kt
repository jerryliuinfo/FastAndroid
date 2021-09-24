package com.apache.fastandroid.demo.designmode

import android.os.Bundle
import android.view.LayoutInflater
import com.apache.fastandroid.R
import com.blankj.utilcode.util.CollectionUtils
import com.tesla.framework.common.util.log.NLog
import com.tesla.framework.ui.fragment.BaseFragment

/**
 * Created by Jerry on 2021/9/23.
 */
class FilterDemoFragment:BaseFragment() {
    override fun inflateContentView(): Int {
        return R.layout.fragment_common
    }
    companion object{
        private const val TAG = "FilterDemoFragment"
    }

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)

        var list = arrayListOf<Int>(1, 2, 4, 5, 6)
        filter(list,object :IPredicate<Int>{
            override fun evaluate(item: Int): Boolean {
                return item % 2 == 0
            }
        })
        NLog.d(TAG, "list:$list")



    }

    fun <E> filter(collection: MutableCollection<E>?, predicate: IPredicate<E>?) {
        if (collection == null || predicate == null) return
        val it: MutableIterator<*> = collection.iterator()
        while (it.hasNext()) {
            if (!predicate.evaluate(it.next() as E)) {
                it.remove()
            }
        }
    }

    interface IPredicate<E> {
        fun evaluate(item: E): Boolean
    }
}