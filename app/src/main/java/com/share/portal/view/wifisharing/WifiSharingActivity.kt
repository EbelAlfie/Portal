package com.share.portal.view.wifisharing

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.wifi.p2p.WifiP2pManager
import android.view.LayoutInflater
import com.share.portal.R
import com.share.portal.databinding.ActivityWifiSharingBinding
import com.share.portal.view.general.PermissionActivity
import com.share.portal.view.utils.BottomSheetPopUp
import com.share.portal.view.utils.PermissionUtils
import com.share.portal.view.wifisharing.broadcastreceiver.WifiBroadcastReceiver

class WifiSharingActivity: PermissionActivity<ActivityWifiSharingBinding>(){

  val intentFilter = IntentFilter().apply {
    addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION)
    addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION)
    addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION)
    addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION)
  }

  private val wifiP2PManager: WifiP2pManager by lazy(LazyThreadSafetyMode.NONE) {
    getSystemService(Context.WIFI_P2P_SERVICE) as WifiP2pManager
  }

  private val wifiBroadcastReceiver: WifiBroadcastReceiver by lazy {
    WifiBroadcastReceiver(
      wifiP2PManager,
      wifiP2PManager.initialize(this, mainLooper, null)
    )
  }

  override fun initBinding(layoutInflater: LayoutInflater): ActivityWifiSharingBinding =
    ActivityWifiSharingBinding.inflate(layoutInflater)

  /** Permission exclusives **/
  override fun getPermissions(): List<String> =
    PermissionUtils.getWifiSharingPermission()

  override fun onPermissionGranted() =
    setupActivity()
  override fun onPermissionDenied(permission: String) =
    showPermissionDeniedDialog(permission)
  override fun onPermissionDeniedPermanently(permission: String) =
    showPermissionDeniedDialog(permission)

  override fun onCreated() = checkPermissions()

  private fun setupActivity() {
    applicationComponent.inject(this)
    registerWifi()
    setupView()
  }

  private fun setupView() {
    binding.apply {

    }
  }

  override fun onResume() {
    super.onResume()
    registerWifi()
  }

  override fun onPause() {
    super.onPause()
    unregisterWifi()
  }

  private fun registerWifi() {
    registerReceiver(
      wifiBroadcastReceiver,
      intentFilter
    )
  }
  private fun unregisterWifi() {
    unregisterReceiver(wifiBroadcastReceiver)
  }

  private fun exitActivity() {
    finish()
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