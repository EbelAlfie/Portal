package com.share.portal.utils

import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.Toast
import com.share.portal.databinding.ToastViewBinding
import javax.inject.Inject

class ToastBuilder @Inject constructor (private val context: Context) {

  private var toast: Toast? = null

  fun show(message: CharSequence?) {
    if (toast != null) toast?.cancel()
    toast = Toast.makeText(context, message, Toast.LENGTH_SHORT)
    toast?.view = ToastViewBinding.inflate(LayoutInflater.from(context)).root.apply {
      text = message
    }
    toast?.setGravity(Gravity.CENTER, 0, 0)
    toast?.show()
  }
}