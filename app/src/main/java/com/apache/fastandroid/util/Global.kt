package com.apache.fastandroid.util

import android.os.Handler
import android.os.Looper
import android.util.Log
import com.blankj.utilcode.util.ToastUtils
import kotlinx.coroutines.Job
import timber.log.Timber

object Global {

  var applicationListJob: Job? = null

  private val handler = Handler(Looper.getMainLooper())

  fun start() {
    handler.post {
      while (true) {
        try {
          Looper.loop()
        } catch (e: Throwable) {
          dealStackTraceException(e)
        }
      }
    }
  }

  @Throws(Throwable::class)
  private fun dealStackTraceException(e: Throwable) {
    val stack = Log.getStackTraceString(e)

    if (stack.contains("Service.startForeground()") ||
        stack.contains("com.swift.sandhook") ||
        stack.contains("updateForceDarkMode") ||
        stack.contains("MultiSelectPopupWindow.showMultiSelectPopupWindow") ||
        stack.contains("get life cycle exception")

      || stack.contains("SelectionHandleView")
      || stack.contains("Magnifier.show")
      || stack.contains("ViewRootImpl.handleDragEvent")
      || stack.contains("PinnedPopWindow.show")
      || stack.contains("FloatingToolbar.show")
      || stack.contains("Context.startForegroundService() did")
      || stack.contains("BaseInputConnection")
      || stack.contains("getTextBeforeCursor")
      || stack.contains("startDragAndDrop")
      || stack.contains("dispatchDragAndDrop")
      || stack.contains("TextClassificationAsyncTask")
      || stack.contains("startSelectionActionModeAsync")
      || stack.contains("Editor.touchePositionIsInSelection")
      || stack.contains("BlinkHandler.finishBlink")
      || stack.contains("TextClassificationHelper")
      || stack.contains("AppMeasurementService")
      || stack.contains("SuggestionSPopupwindoW")
      || stack.contains("ASynCinputstage")
      || stack.contains("HandleView.updatePosition")
      || stack.contains("onDialogDISmissed")
      || stack.contains("com.oppo.intent.action.TRANSLATE")
      || stack.contains("Accessibi1ityInteractionContro11er")
      || stack.contains("BadTokenException")
      || stack.contains("Service.startForeground()")
      || stack.contains("com.swift.sandhook")
      || stack.contains("updateForceDarkMode")
      || stack.contains("ClipboardService")
    ) {
      Timber.w(e)
    } else if (stack.contains("ClipboardService")) {
      Timber.w(e)
      ToastUtils.showShort("Cannot access to ClipboardService")
    } else if (stack.contains("de.robv.android.xposed")) {
      Timber.w(e)
      ToastUtils.showShort("Encounter Xposed module crash")
    } else {
      throw e
    }
  }
}
