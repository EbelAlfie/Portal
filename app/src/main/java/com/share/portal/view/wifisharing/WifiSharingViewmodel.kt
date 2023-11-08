package com.share.portal.view.wifisharing

import androidx.lifecycle.ViewModel
import com.share.portal.view.wifisharing.broadcastreceiver.WifiBroadcastReceiver
import javax.inject.Inject

class WifiSharingViewmodel @Inject constructor(): ViewModel() {
  private val broadCastReceiver: WifiBroadcastReceiver by lazy { WifiBroadcastReceiver() }

}