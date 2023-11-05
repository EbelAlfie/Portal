package com.share.portal.utils

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent.getService
import android.content.Context
import android.os.Binder
import android.os.Handler
import android.os.RemoteException
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.resources.Compatibility
import androidx.core.app.NotificationChannelCompat
import androidx.core.app.NotificationManagerCompat

class ToastBuilder(private val context: Context) {

  private val mToken: Binder? = null
  private val mHandler: Handler? = null
  private var toastView: TextView? = null

  fun show() {
    if (toastView == null) return
    val service = Notification.Builder(context)
      .setAutoCancel(true)
  }

  fun setView(textView: TextView) {
    toastView = textView
  }


  private fun getService() {
  }
  interface ToastCallback {
    fun onShow()
    fun onHide()
  }

  companion object {
    const val TAG: String = "Toast"

  }
}