package com.share.portal.view.general

import androidx.activity.result.contract.ActivityResultContracts
import androidx.viewbinding.ViewBinding

abstract class PermissionActivity<V: ViewBinding>: ProgenitorActivity<V>() {
  private var mListener: PermissionListener? = null

  private val permissionLauncher = registerForActivityResult(
    ActivityResultContracts.RequestMultiplePermissions()
  ) { result ->
    result.onEachIndexed { index, entry ->
      when (entry.value) {
        false -> mListener?.onDenied(entry.key)
        else -> if (index == result.size) mListener?.onGranted()
      }
    }
  }

  fun checkPermissions() = permissionLauncher.launch(getPermissions().toTypedArray())

  fun setPermissionListener(listener: PermissionListener) {
    mListener = listener
  }

  interface PermissionListener {
    fun onGranted()
    fun onDenied(permission: String)
  }

  abstract fun getPermissions(): List<String>
}