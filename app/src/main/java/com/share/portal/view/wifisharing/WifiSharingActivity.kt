package com.share.portal.view.wifisharing

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import androidx.lifecycle.ViewModel
import com.share.portal.R
import com.share.portal.databinding.ActivityWifiSharingBinding
import com.share.portal.view.general.PermissionActivity
import com.share.portal.view.utils.BottomSheetPopUp
import com.share.portal.view.utils.PermissionUtils
import javax.inject.Inject

class WifiSharingActivity: PermissionActivity<ActivityWifiSharingBinding>(){

  @Inject
  lateinit var viewModel: WifiSharingViewmodel

  override fun initBinding(layoutInflater: LayoutInflater): ActivityWifiSharingBinding =
    ActivityWifiSharingBinding.inflate(layoutInflater)

  override fun getPermissions(): List<String> =
    PermissionUtils.getWifiSharingPermission()

  override fun onCreated() {
    setPermissionListener(object: PermissionListener {
      override fun onGranted() =
        setupActivity()
      override fun onDenied(permission: String) =
        showPermissionDeniedDialog(permission)
      override fun onDeniedPermanently(permission: String) =
        showPermissionDeniedDialog(permission)
    })
    checkPermissions()
  }

  private fun setupActivity() {
    applicationComponent.inject(this)
    registerWifi()
    setupView()
  }

  private fun registerWifi() {

  }

  private fun setupView() {
    binding.apply {

    }
  }

  private fun exitActivity() {
    finish()
    overridePendingTransition(R.anim.stay, R.anim.slide_down)
  }

  private fun showPermissionDeniedDialog(permission: String) {
    BottomSheetPopUp.newDialog(
      supportFragmentManager,
      this,
      R.drawable.ic_folder,
      getString(R.string.warning_general_title),
      getString(R.string.warning_general_content),
      onDismiss = ::exitActivity
    )
  }

  companion object {
    fun navigate(from: Context): Intent {
      return Intent(from, WifiSharingActivity::class.java)
    }
  }

}