package com.share.portal.view.general

import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.viewbinding.ViewBinding

abstract class PermissionActivity<V: ViewBinding>: ProgenitorActivity<V>() {
  private var mListener: PermissionListener? = null
  private val permissionLauncher = registerForActivityResult(
    ActivityResultContracts.RequestMultiplePermissions()
  ) { permissions ->
    permissions.onEachIndexed { index, permission ->
      when (permission.value) {
        false -> handleDeniedPermission(permission.key)
        true -> if (index == permissions.size - 1) mListener?.onGranted()
      }
    }
  }

  private fun handleDeniedPermission(permission: String) {
    if (ActivityCompat.shouldShowRequestPermissionRationale(this, permission))
      mListener?.onDenied(permission)
    else mListener?.onDeniedPermanently(permission)
  }

  protected fun setPermissionListener(listener: PermissionListener) {
    mListener = listener
  }

  protected fun checkPermissions() {
    permissionLauncher.launch(getPermissions().toTypedArray())
  }

  interface PermissionListener {
    fun onGranted()
    fun onDenied(permission: String)
    fun onDeniedPermanently(permission: String)
  }

  abstract fun getPermissions(): List<String>
}