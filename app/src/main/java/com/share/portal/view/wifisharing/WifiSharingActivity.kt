package com.share.portal.view.wifisharing

import android.view.LayoutInflater
import com.share.portal.databinding.ActivityWifiSharingBinding
import com.share.portal.view.general.ProgenitorActivity

class WifiSharingActivity: ProgenitorActivity<ActivityWifiSharingBinding>(){
  override fun initBinding(layoutInflater: LayoutInflater): ActivityWifiSharingBinding =
    ActivityWifiSharingBinding.inflate(layoutInflater)

  override fun onCreated() {

  }

}