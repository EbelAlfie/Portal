package com.share.portal.view.filemanager

import android.Manifest
import android.content.IntentFilter
import android.net.wifi.p2p.WifiP2pManager
import android.view.LayoutInflater
import android.widget.ImageView
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.view.setPadding
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.Tab
import com.google.android.material.tabs.TabLayoutMediator
import com.share.portal.R
import com.share.portal.databinding.ActivityMainBinding
import com.share.portal.view.filemanager.adapter.PageEnum
import com.share.portal.view.filemanager.adapter.PageEnum.FILE_EXPLORER
import com.share.portal.view.filemanager.adapter.PageEnum.FILE_SHARING
import com.share.portal.view.filemanager.adapter.ViewPagerAdapter
import com.share.portal.view.filemanager.wifisharing.broadcastreceiver.WifiBroadcastReceiver
import com.share.portal.view.general.PermissionActivity
import com.share.portal.view.utils.BottomSheetPopUp
import com.share.portal.view.utils.PermissionUtils
import javax.inject.Inject

class MainActivity : PermissionActivity<ActivityMainBinding>(), WifiPerantara {

  @Inject
  lateinit var viewModel: MainViewModel

  override val intentFilter: IntentFilter
    get() = IntentFilter().apply {
    addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION)
    addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION)
    addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION)
    addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION)
  }

  private val adapter: ViewPagerAdapter by lazy {
    ViewPagerAdapter(supportFragmentManager, lifecycle)
  }

  private val wifiP2PManager: WifiP2pManager by lazy(LazyThreadSafetyMode.NONE) {
    getSystemService(WIFI_P2P_SERVICE) as WifiP2pManager
  }

  private val wifiBroadcastReceiver: WifiBroadcastReceiver by lazy {
    WifiBroadcastReceiver(
      wifiP2PManager,
      wifiP2PManager.initialize(this, mainLooper, null)
    )
  }

  /** Permission exclusives **/
  override fun getPermissions(): List<String> =
    listOf(
      Manifest.permission.WRITE_EXTERNAL_STORAGE,
      Manifest.permission.READ_EXTERNAL_STORAGE,
    ) + PermissionUtils.getWifiSharingPermission()

  override fun onPermissionGranted() = setupActivity()

  override fun onPermissionDenied(permission: String) =
    showPermissionDeniedDialog(permission)

  override fun onPermissionDeniedPermanently(permission: String) =
    showPermissionDeniedDialog(permission)

  private fun showPermissionDeniedDialog(permission: String) {
    BottomSheetPopUp.newDialog(
      fragmentManager = supportFragmentManager,
      context = this,
      title = getString(R.string.warning_general_title),
      content = getString(R.string.warning_general_content),
      onDismiss = ::finish
    )
  }

  /** Activity exclusives **/
  override fun initBinding(layoutInflater: LayoutInflater): ActivityMainBinding =
    ActivityMainBinding.inflate(layoutInflater)

  override fun onCreated() {
    checkPermissions()
  }

  private fun setupActivity() {
    applicationComponent.inject(this)
    registerBackPress()
    configureWifiService()
    setupViews()
  }

  private fun configureWifiService() {
    wifiBroadcastReceiver
  }

  private fun registerBackPress() {
    onBackPressedDispatcher.addCallback(this, object: OnBackPressedCallback(true) {
      override fun handleOnBackPressed() = onBackButtonPressed()
    })
  }

  private fun onBackButtonPressed() {}

  private fun setupToolbar() {
    binding.toolbar.apply {
      tvTitle.text = getString(R.string.portal_label)
    }
  }

  private fun setupViews() {
    setupToolbar()
    binding.run {
      vpContainer.adapter = adapter
      configureTab()
      TabLayoutMediator(toolbar.pseudoTab, vpContainer) {_, _ -> }
      vpContainer.registerOnPageChangeCallback(object: OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
          super.onPageSelected(position)
          binding.toolbar.pseudoTab.getTabAt(position)?.select()
        }
      })
    }
  }

  private fun configureTab() {
    binding.toolbar.pseudoTab.apply {
      PageEnum.values().forEach { enum ->
        val tab = newTab()

        tab.customView = ImageView(this@MainActivity).apply {
          setImageResource(
            when (enum) {
              FILE_EXPLORER -> R.drawable.ic_folder
              FILE_SHARING -> R.drawable.ic_file_sharing
            }
          )
          setPadding(20)
          imageTintList = getColorStateList(R.color.white)
          background = AppCompatResources
            .getDrawable(this@MainActivity, R.drawable.bg_toolbar_icon)
        }
        addTab(tab)
      }

      addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener {
        override fun onTabSelected(tab: Tab) {
          binding.vpContainer.currentItem = tab.position
        }
        override fun onTabUnselected(tab: Tab) {}
        override fun onTabReselected(tab: Tab) {}
      })
    }
  }

  override fun registerWifi() {
    registerReceiver(
      wifiBroadcastReceiver, intentFilter,
    )
  }

  override fun unregisterWifi() {
    unregisterReceiver(wifiBroadcastReceiver)
  }

  fun provideP2pService(): WifiBroadcastReceiver {
    return wifiBroadcastReceiver
  }
}