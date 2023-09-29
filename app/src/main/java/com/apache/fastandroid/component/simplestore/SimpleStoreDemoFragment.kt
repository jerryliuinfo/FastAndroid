package com.apache.fastandroid.component.simplestore

import android.os.Bundle
import android.view.LayoutInflater
import androidx.lifecycle.lifecycleScope
import com.apache.fastandroid.databinding.ActivitySimpleStoreBinding
import com.tesla.framework.component.simplestore.BlockStore
import com.tesla.framework.component.simplestore.DatastorePreference
import com.tesla.framework.component.simplestore.SharedPreference
import com.tesla.framework.component.simplestore.SimpleStore
import com.tesla.framework.ui.fragment.BaseBindingFragment
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

/**
 * Created by Jerry on 2023/9/26.
 */
class SimpleStoreDemoFragment:BaseBindingFragment<ActivitySimpleStoreBinding>(ActivitySimpleStoreBinding::inflate) {

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {

        super.layoutInit(inflater, savedInstanceState)

        val store = SimpleStore.Builder()
            .context(context = requireContext())
            .storeName("AnyName")
            .encryption(encrypted = false)
            .build()

        mBinding.storeByDataStore.setOnClickListener {
            lifecycleScope.launch {
                store.getType<DatastorePreference>().saveStringToStore(
                    "name",
                    mBinding.editTextTextPersonName.text.toString()
                )
            }

        }
        mBinding.storeBySp.setOnClickListener {
            lifecycleScope.launch {
                store.getType<SharedPreference>().saveStringToStore(
                    "yes",
                    mBinding.editTextTextPersonName2.text.toString()
                )
            }

        }
       /* mBinding.storeByBlockStore.setOnClickListener {
            lifecycleScope.launch {
                store.getType<BlockStore>().saveStringToStore(
                    "no",
                    mBinding.editTextTextPersonName3.text.toString()
                )
            }

        }*/

        lifecycleScope.launchWhenCreated {

            mBinding.dataOneValue.text = store.getType<DatastorePreference>()
                .getStringFromStore("name").first()

            mBinding.dataTwoValue.text = store.getType<SharedPreference>()
                .getStringFromStore("yes")?.first().toString()

            mBinding.dataThreeValue.text = store.getType<DatastorePreference>()
                .getStringFromStore("no").first()

        }
    }
}