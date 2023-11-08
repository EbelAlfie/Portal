package com.share.portal.view.general

import android.Manifest.permission
import android.content.pm.PackageManager
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.viewbinding.ViewBinding
import com.share.portal.R
import com.share.portal.view.utils.BottomSheetPopUp

abstract class PermissionActivity<V: ViewBinding>: ProgenitorActivity<V>() {
  private var mListener: PermissionListener? = null

  private val permissionLauncher = registerForActivityResult(
    ActivityResultContracts.RequestMultiplePermissions()
  ) { result ->
    result.onEachIndexed { index, permission ->
      when (permission.value) {
        false -> handleDeniedPermission(permission.key)
        true -> if (index == result.size - 1) mListener?.onGranted()
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

  fun showPermissionDeniedDialog(permission: String) {
    BottomSheetPopUp.newDialog(
      supportFragmentManager,
      R.drawable.ic_folder,
      getString(R.string.warning_general_title),
      getString(R.string.warning_general_content),
      onDismiss = { finish() }
    )
  }

  interface PermissionListener {
    fun onGranted()
    fun onDenied(permission: String)
    fun onDeniedPermanently(permission: String)
  }

  abstract fun getPermissions(): List<String>
}