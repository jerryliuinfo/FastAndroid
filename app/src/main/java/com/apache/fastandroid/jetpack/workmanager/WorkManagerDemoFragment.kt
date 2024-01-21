package com.apache.fastandroid.jetpack.workmanager

import android.os.Bundle
import android.view.LayoutInflater
import androidx.work.BackoffPolicy
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequest
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkRequest
import androidx.work.workDataOf
import com.apache.fastandroid.databinding.FragmentJetpackWorkManagerBinding
import com.tesla.framework.ui.fragment.BaseBindingFragment
import java.util.concurrent.TimeUnit

/**
 * Created by Jerry on 2023/12/26.
 * https://developer.android.com/guide/background?hl=zh-cn
 */
class WorkManagerDemoFragment:BaseBindingFragment<FragmentJetpackWorkManagerBinding>(FragmentJetpackWorkManagerBinding::inflate) {

    companion object{
        const val WORK_MANAGER_TAG = "WorkManager"
        private const val TASK_TAG = "upload"
        private const val TASK_CLEANUP_TAG = "cleanUp"

    }

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)


        mBinding.btnOnetimeWork.setOnClickListener {
            oneTimeWorkUsage()
        }

        mBinding.btnPeriodicWork.setOnClickListener {
            periodicWorkUsage()
        }

        mBinding.btnFlexibleInterval.setOnClickListener {
            flexibleIntervalUsage()
        }

        mBinding.btnConstraint.setOnClickListener {
            constraintUsage()
        }

        mBinding.btnInitialDelay.setOnClickListener {
            delayExecuteUsage()
        }
        mBinding.btnRetryLiner.setOnClickListener {
            retryWithLinerUsage()
        }

        mBinding.btnRetryExponential.setOnClickListener {
            retryWithExponentialUsage()
        }

        mBinding.btnJobMarkTag.setOnClickListener {
            tagUsage()
        }
        mBinding.btnCancelJobByTag.setOnClickListener {
            cancelTaskByTag()
        }

        mBinding.btnTaskWithParameter.setOnClickListener {
            taskWithParameter()
        }
    }

    private fun taskWithParameter() {
        val task = OneTimeWorkRequestBuilder<UploadWorkerWithParamter>().setInputData(
            workDataOf(UploadWorkerWithParamter.PARAM_IMAGE_URL to "www.baidu.com")
        ).build()
        val workInfoById = WorkManager.getInstance(requireContext()).enqueue(task)
    }

    private fun cancelTaskByTag() {
        val workManager = WorkManager.getInstance(requireContext())
        workManager.cancelAllWorkByTag(TASK_CLEANUP_TAG)
    }

    /**
     * 每个工作请求都有一个唯一标识符，该标识符可用于在以后标识该工作，以便取消工作或观察其进度。
     *
     * 如果有一组在逻辑上相关的工作，对这些工作项进行标记可能也会很有帮助。通过标记，您一起处理一组工作请求。
     *
     * 例如，WorkManager.cancelAllWorkByTag(String) 会取消带有特定标记的所有工作请求，WorkManager.getWorkInfosByTag(String) 会返回一个 WorkInfo 对象列表，该列表可用于确定当前工作状态。
     *
     *
     */
    private fun tagUsage() {
        val workRequest = OneTimeWorkRequestBuilder<TagWork>().addTag(TASK_CLEANUP_TAG).build()
        WorkManager.getInstance(requireContext()).enqueue(workRequest)
    }


    /**
     *
     * 重试时长序列将接近10、 20、40、80 秒，以此类推。
     */
    private fun retryWithExponentialUsage() {
        val request = OneTimeWorkRequestBuilder<RetryWork>()
            .setBackoffCriteria(
                BackoffPolicy.EXPONENTIAL,
                OneTimeWorkRequest.MIN_BACKOFF_MILLIS,
                TimeUnit.MILLISECONDS)
            .build()
        WorkManager
            .getInstance(requireContext())
            .enqueue(request)
    }


    /**
     * 重试和退避政策
     * 在本示例中，最短退避延迟时间设置为允许的最小值，即 10 秒。由于政策为 LINEAR，每次尝试重试时，重试间隔都会增加约 10 秒。
     * 例如，第一次运行以 Result.retry() 结束并在 10 秒后重试；然后，如果工作在后续尝试后继续返回 Result.retry()，
     * 那么接下来会在 20 秒、30 秒、40 秒后重试，以此类推。
     */
    private fun retryWithLinerUsage() {
        val request = OneTimeWorkRequestBuilder<RetryWork>()
            .setBackoffCriteria(
                BackoffPolicy.LINEAR,
                OneTimeWorkRequest.MIN_BACKOFF_MILLIS,
                TimeUnit.MILLISECONDS)
            .build()
        WorkManager
            .getInstance(requireContext())
            .enqueue(request)
    }


    /**
     * 如果不希望工作立即运行，可以将工作指定为在经过一段最短初始延迟时间后再启动。
     * 注意：执行工作器的确切时间还取决于 WorkRequest 中使用的约束和系统优化方式。WorkManager 经过设计，能够在满足这些约束的情况下提供可能的最佳行为。
     */
    private fun delayExecuteUsage() {
        com.tesla.framework.component.logger.Logger.d("call initalDelayUsage")
        val request = OneTimeWorkRequestBuilder<UploadWorker>()
            .setInitialDelay(5, TimeUnit.SECONDS)
            .build()
        WorkManager
            .getInstance(requireContext())
            .enqueue(request)
    }


    /**
     * 如果指定了多个约束，工作将仅在满足所有约束时才会运行。
     *
     * 如果在工作运行时不再满足某个约束，WorkManager 将停止工作器。系统将在满足所有约束后重试工作。
     */
    private fun constraintUsage() {
        val constraints = Constraints.Builder()
            //y约束网络类型为 wifi
            .setRequiredNetworkType(NetworkType.UNMETERED)
            //充电时运行
            .setRequiresCharging(true)
            //低电量不运行
            .setRequiresBatteryNotLow(true)
            //如果设置为 true，则要求用户的设备必须处于空闲状态，才能运行工作。在运行批量操作时，此约束会非常有用；
            // 若是不用此约束，批量操作可能会降低用户设备上正在积极运行的其他应用的性能
            .setRequiresDeviceIdle(true)
            //当用户设备上的存储空间不足时，工作不会运行
            .setRequiresStorageNotLow(true)
            .build()

        val request: WorkRequest =
            OneTimeWorkRequestBuilder<UploadWorker>()
                .setConstraints(constraints)
                .build()
        WorkManager
            .getInstance(requireContext())
            .enqueue(request)
    }

    private fun flexibleIntervalUsage() {
        //重复间隔必须大于或等于 PeriodicWorkRequest.MIN_PERIODIC_INTERVAL_MILLIS (15 分钟)，
        // 而灵活间隔必须大于或等于 PeriodicWorkRequest.MIN_PERIODIC_FLEX_MILLIS (5 分钟)
        val request = PeriodicWorkRequestBuilder<UploadWorker>(
            1, TimeUnit.HOURS, // repeatInterval (the period cycle)
            15, TimeUnit.MINUTES) // flexInterval
            .build()
        WorkManager
            .getInstance(requireContext())
            .enqueue(request)
    }

    private fun periodicWorkUsage() {
        //   注意 :可以定义的最短重复间隔是 15 分钟  PeriodicWorkRequest.MIN_PERIODIC_INTERVAL_MILLIS,如果传入的事件少于 15分钟，则也会按 15 分钟生效
        val request =
            PeriodicWorkRequestBuilder<UploadWorker>(1, TimeUnit.HOURS)
                // Additional configuration
                .build()
        WorkManager
            .getInstance(requireContext())
            .enqueue(request)
    }

    /**
     * 一次性工作
     */
    private fun oneTimeWorkUsage() {
        val uploadWorkRequest: WorkRequest =
            OneTimeWorkRequestBuilder<UploadWorker>()
                .addTag(TASK_TAG)
                .build()
        WorkManager
            .getInstance(requireContext())
            .enqueue(uploadWorkRequest)
    }
}