package com.apache.fastandroid.demo.databinding.practice

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.databinding.ObservableInt
import com.apache.fastandroid.databinding.FragmentDatabindingObservableFieldBinding
import com.apache.fastandroid.demo.databinding.data.ObservableFieldProfile
import com.tesla.framework.ui.fragment.BaseDBFragment


class DatabindingObservableFiledFragment :
    BaseDBFragment<FragmentDatabindingObservableFieldBinding>(FragmentDatabindingObservableFieldBinding::inflate) {
    companion object {
        private const val TAG = "DatabindingObservableFiledFragment"
    }
    private val observableFieldProfile = ObservableFieldProfile("Ada", "Lovelace", ObservableInt(0))




    override fun initViewModel() {


    }


    override fun layoutInit(inflater: LayoutInflater?, savedInstanceSate: Bundle?) {
        super.layoutInit(inflater, savedInstanceSate)

        mBinding.apply {
            user = observableFieldProfile
            framgent = this@DatabindingObservableFiledFragment
        }

       /* viewBinding.likeButton.setOnClickListener {
            observableFieldProfile.likes.set(observableFieldProfile.likes.get() + 1)
        }*/
    }

    /**
     * This method is triggered by the `android:onclick` attribute in the layout. It puts business
     * logic in the activity, which is not ideal. See {@link ViewModelActivity} for a better
     * solution.
     */
    fun onLike(view: View) {
        observableFieldProfile.likes.set(observableFieldProfile.likes.get() + 1)
    }


}