package com.share.portal.view.general

import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.viewbinding.ViewBinding

abstract class PermissionActivity<V: ViewBinding>: ProgenitorActivity<V>() {
  private val permissionLauncher = registerForActivityResult(
    ActivityResultContracts.RequestMultiplePermissions()
  ) { permissions ->
    permissions.onEachIndexed { index, permission ->
      when (permission.value) {
        false ->  {
          handleDeniedPermission(permission.key)
          return@registerForActivityResult
        }
        true -> if (index == permissions.size - 1) onPermissionGranted()
      }
    }
    onPermissionGranted()
  }

  private fun handleDeniedPermission(permission: String) {
    if (ActivityCompat.shouldShowRequestPermissionRationale(this, permission))
      onPermissionDenied(permission)
    else onPermissionDeniedPermanently(permission)
  }

  protected fun checkPermissions() {
    permissionLauncher.launch(getPermissions().toTypedArray())
  }

  abstract fun getPermissions(): List<String>

  abstract fun onPermissionGranted()
  abstract fun onPermissionDenied(permission: String)
  abstract fun onPermissionDeniedPermanently(permission: String)
}