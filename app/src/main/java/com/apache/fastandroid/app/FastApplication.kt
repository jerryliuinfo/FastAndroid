package com.apache.fastandroid.app

import android.app.Activity
import android.app.Application
import android.content.ComponentCallbacks2
import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Build
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import com.apache.fastandroid.Initiator
import com.apache.fastandroid.artemis.ui.app.ComApplication
import com.apache.fastandroid.jetpack.flow.api.ApiHelper
import com.apache.fastandroid.jetpack.flow.api.ApiHelperImpl
import com.apache.fastandroid.jetpack.flow.local.DatabaseBuilder
import com.apache.fastandroid.jetpack.flow.local.DatabaseHelper
import com.apache.fastandroid.jetpack.flow.local.DatabaseHelperImpl
import com.apache.fastandroid.network.api.ApiServiceFactory
import com.apache.fastandroid.util.MultidexUtils
import com.example.android.architecture.blueprints.todoapp.data.source.DefaultTasksRepository
import com.example.android.architecture.blueprints.todoapp.data.source.TasksRepository
import com.example.android.architecture.blueprints.todoapp.data.source.local.TasksLocalDataSource
import com.example.android.architecture.blueprints.todoapp.data.source.local.ToDoDatabase
import com.squareup.leakcanary.LeakCanary
import com.tesla.framework.common.util.LaunchTimer
import com.tesla.framework.component.logger.Logger
import dagger.hilt.android.HiltAndroidApp
import kotlin.system.measureTimeMillis

/**
 * Created by jerryliu on 2017/3/26.
 */
@HiltAndroidApp
class FastApplication : ComApplication(), ViewModelStoreOwner, ComponentCallbacks2 {

    private var mFactory: ViewModelProvider.Factory? = null

    private var mAppViewModelStore: ViewModelStore? = null


    val apiHelper:ApiHelper by lazy {
        ApiHelperImpl(ApiServiceFactory.flowService)
    }
    lateinit var  databaseHelper:DatabaseHelper




    companion object {
        val TAG = FastApplication::class.java.simpleName
        lateinit var instance: FastApplication
        lateinit var context: Context

        const val DEFAULT_PREFERENCES = "default_preferences"
    }
    override fun onCreate() {
        super.onCreate()
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return
        }
        context = this
        instance = this
        databaseHelper = DatabaseHelperImpl(DatabaseBuilder.getInstance(this))
        mAppViewModelStore = ViewModelStore()

        val time = measureTimeMillis {
            Initiator.init(this)
        }

        println("Application onCreate cost time: $time milles")
    }

    override fun attachBaseContext(base: Context) {
        LaunchTimer.startRecord()
//        MultiDex.install(base)

        val isMainProcess: Boolean = MultidexUtils.isMainProcess(base)
        val isVMMultidexCapable:Boolean = MultidexUtils.isVMMultidexCapable()
        Logger.d("Application attachBaseContext isMainProcess:$isMainProcess,isVMMultidexCapable:$isVMMultidexCapable")
        if (isMainProcess && !isVMMultidexCapable) {
            MultidexUtils.loadMultiDex(base)
        } else {
            MultidexUtils.preNewActivity()
        }
        //HotFixManager.loadDex(base);
        super.attachBaseContext(base)
    }


    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        //非默认值
        if (newConfig.fontScale != 1f) {
            resources
        }
        super.onConfigurationChanged(newConfig)
    }

    override fun getResources(): Resources {
        val res = super.getResources()


        //
        if (res.configuration.fontScale != 1f) { //非默认值

            //https://wx.zsxq.com/dweb2/index/topic_detail/841115285142582
            val newConfig = Configuration()
            newConfig.setToDefaults()
            //强制字体不随着系统改变而改变
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                createConfigurationContext(newConfig)
            } else {
                res.updateConfiguration(newConfig, res.displayMetrics)
            }
        }
        return res
    }


    override fun getApplicationContext(): Context {
//        return super.getApplicationContext();
        return this
    }

    override fun getViewModelStore(): ViewModelStore {
        return mAppViewModelStore!!
    }

    fun getAppViewModelProvider(activity: Activity): ViewModelProvider {
        return ViewModelProvider(
            activity.applicationContext as FastApplication,
            (activity.applicationContext as FastApplication).getAppFactory(activity)
        )
    }

    private fun getAppFactory(activity: Activity): ViewModelProvider.Factory {
        val application = checkApplication(activity)
        if (mFactory == null) {
            mFactory = ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        }
        return mFactory as ViewModelProvider.Factory
    }

    private fun checkApplication(activity: Activity): Application {
        return activity.application
            ?: throw IllegalStateException("Your activity/fragment is not yet attached to " + "Application. You can't request ViewModel before onCreate call.")
    }


    // Depends on the flavor,
    val taskRepository: TasksRepository
        get()  {
            // ServiceLocator.provideTasksRepository(this)
            val taskDao = ToDoDatabase.getInstance()
            return DefaultTasksRepository(TasksLocalDataSource(taskDao),TasksLocalDataSource(taskDao))
        }


    override fun onTrimMemory(level: Int) {
        super.onTrimMemory(level)
    }

    override fun onLowMemory() {
        super.onLowMemory()
    }


}