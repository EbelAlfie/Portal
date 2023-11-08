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

class WifiSharingActivity: PermissionActivity<ActivityWifiSharingBinding>(){
  override fun initBinding(layoutInflater: LayoutInflater): ActivityWifiSharingBinding =
    ActivityWifiSharingBinding.inflate(layoutInflater)

  override fun getPermissions(): List<String> =
    PermissionUtils.getWifiSharingPermission()

  override fun onCreated() {
    //bottomsheet, register sebagai service, scan devices
    BottomSheetPopUp.newDialog(
      fragmentManager = supportFragmentManager,
      image = R.drawable.ic_document,
      title = getString(),
      content = getString()
    )
  }

  companion object {
    fun navigate(from: Context): Intent {
      return Intent(from, WifiSharingActivity::class.java)
    }

  }

}