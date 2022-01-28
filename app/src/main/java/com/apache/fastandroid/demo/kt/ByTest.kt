package com.apache.fastandroid.demo.kt

import com.tesla.framework.component.logger.Logger

/**
 * Created by Jerry on 2022/1/27.
 */
class ByTest {

    interface Animal{
        fun bark()
    }

    class Cat:Animal{
        override fun bark() {
            Logger.d("Wang")
        }
    }

    class Zoo(animal: Animal):Animal by animal

    class ZooBy(animal: Animal):Animal by animal{
        override fun bark() {
            Logger.d("Wang by delegate")
        }
    }
}