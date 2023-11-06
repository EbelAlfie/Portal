package com.share.portal.view.general

import androidx.activity.result.contract.ActivityResultContracts
import androidx.viewbinding.ViewBinding

abstract class PermissionActivity<V: ViewBinding>: ProgenitorActivity<V>() {
  private val mListener = object: PermissionListener {
    override fun onDenied(permission: String) {
      finish()
    }

    override fun onDeniedPermanently() {
      finish()
    }
  }

  private val permissionLauncher = registerForActivityResult(
    ActivityResultContracts.RequestMultiplePermissions()
  ) { result ->
    result.forEach {
      when (it.value) {
        false -> finish()
        else -> {}
      }
    }
  }

  override fun onCreated() {
    checkPermissions()
  }

  private fun checkPermissions() = permissionLauncher.launch(getPermissions().toTypedArray())

  interface PermissionListener {
    fun onDenied(permission: String)
    fun onDeniedPermanently()
  }

  abstract fun getPermissions(): List<String>
}