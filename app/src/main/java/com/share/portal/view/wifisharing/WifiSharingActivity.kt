package com.share.portal.view.wifisharing

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import com.share.portal.R
import com.share.portal.databinding.ActivityWifiSharingBinding
import com.share.portal.view.filemanager.MainActivity
import com.share.portal.view.general.PermissionActivity
import com.share.portal.view.general.ProgenitorActivity
import com.share.portal.view.utils.BottomSheetPopUp
import com.share.portal.view.utils.PermissionUtils
import java.lang.System.exit

class WifiSharingActivity: PermissionActivity<ActivityWifiSharingBinding>(){
  override fun initBinding(layoutInflater: LayoutInflater): ActivityWifiSharingBinding =
    ActivityWifiSharingBinding.inflate(layoutInflater)

  override fun getPermissions(): List<String> =
    PermissionUtils.getWifiSharingPermission()

  override fun onCreated() {
    //bottomsheet, register sebagai service, scan devices
    setPermissionListener(object: PermissionListener {
      override fun onGranted() {
        setupActivity()
      }
      override fun onDenied(permission: String) {
        BottomSheetPopUp.newDialog (
          fragmentManager = supportFragmentManager,
          image = R.drawable.ic_document,
          title = getString(R.string.warning_wifi_sharing_title),
          content = getString(R.string.warning_wifi_sharing_content),
          onDismiss = ::exitActivity
        )
      }
    })
    checkPermissions()
  }

  private fun setupActivity() {
    showToast("YEYY")
  }

  private fun exitActivity() {
    finish()
    overridePendingTransition(R.anim.stay, R.anim.slide_down)
  }

  companion object {
    fun navigate(from: Context): Intent {
      return Intent(from, WifiSharingActivity::class.java)
    }

  }

}