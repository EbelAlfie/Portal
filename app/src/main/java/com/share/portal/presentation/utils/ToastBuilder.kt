package com.share.portal.presentation.utils

import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.TextView
import android.widget.Toast
import com.share.portal.databinding.ToastViewBinding
import javax.inject.Inject

class ToastBuilder @Inject constructor (private val context: Context) {

  private var toast: Toast? = null

  private var toastView: TextView = ToastViewBinding.inflate(LayoutInflater.from(context)).root

  fun show(message: CharSequence?, gravity: Int = Gravity.CENTER) {
    if (toast != null) toast?.cancel()
    toast = Toast.makeText(context, message, Toast.LENGTH_SHORT)
    toastView.text = message
    toast?.view = toastView
    toast?.setGravity(gravity, 0, 0)
    toast?.show()
  }
}