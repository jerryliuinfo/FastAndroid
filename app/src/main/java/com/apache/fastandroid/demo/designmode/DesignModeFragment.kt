package com.apache.fastandroid.demo.designmode

import android.os.Bundle
import android.view.LayoutInflater
import com.apache.fastandroid.databinding.FragmentDesignModeBinding
import com.apache.fastandroid.demo.designmode.absfactory.ConcreteAnimalFactory
import com.apache.fastandroid.demo.designmode.base.AnimalType
import com.apache.fastandroid.demo.designmode.factory.AnimalFactory
import com.tesla.framework.ui.fragment.BaseBindingFragment

/**
 * Created by Jerry on 2021/3/1.
 */
class DesignModeFragment:BaseBindingFragment<FragmentDesignModeBinding>(FragmentDesignModeBinding::inflate) {
//    override fun initDatas(): ArrayList<ViewItemBean> {
//        return arrayListOf(
//            ViewItemBean("观察者模式","观察者模式",ObserverModeFragment::class.java)
//            ,ViewItemBean("LifeCycleOwner","防系统ComponentActivity监听声明周期",ObserverModeFragment::class.java)
//            ,ViewItemBean("代理模式","代理模式",ProxyModeDemoFragment::class.java)
//            ,ViewItemBean("Builder模式","Builder模式",BuilderModeDemoFragment::class.java)
//            ,ViewItemBean("Delegate模式","Delegate模式",DelegateDemoFragment::class.java)
//            ,ViewItemBean("GetService","根据传入的参数类型决定返回参数类型对象",ParamTypeDemoFragment::class.java)
//            ,ViewItemBean("Filter模式","Filter模式",FilterDemoFragment::class.java)
//            ,ViewItemBean("Chain模式","Chain模式",ChainModeDemoFragment::class.java)
//            ,ViewItemBean("Wrapper模式","Wrapper模式", WrapperDesignMode::class.java)
//            ,ViewItemBean("IdleHandlerDemo","IdleHandlerDemo", WrapperDesignMode::class.java)
//
//        )
//    }
//
//    override fun layoutInit(inflater: LayoutInflater?, savedInstanceSate: Bundle?) {
//        super.layoutInit(inflater, savedInstanceSate)
//    }

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)


        mBinding.btnFactory.setOnClickListener {
            val dog = AnimalFactory.createAnimal(AnimalType.DOG)
            dog?.makeSound() // Output: Woof!

            val cat = AnimalFactory.createAnimal(AnimalType.CAT)
            cat?.makeSound() // Output: Meow!
        }

        mBinding.btnAbstractFactory.setOnClickListener {
            val dog = ConcreteAnimalFactory().createDog()
            val cat = ConcreteAnimalFactory().createCat()
            dog.makeSound()
            cat.makeSound()


        }

        mBinding.btnPrototype.setOnClickListener {

        }
    }
}