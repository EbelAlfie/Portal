package com.share.portal.view.filemanager.wifisharing

import android.content.Context
import android.view.LayoutInflater
import com.share.portal.databinding.FragmentFileSharingBinding
import com.share.portal.view.filemanager.WifiPerantara
import com.share.portal.view.general.ProgenitorFragment

class FileSharingFragment: ProgenitorFragment<FragmentFileSharingBinding>() {

  override fun initBinding(layoutInflater: LayoutInflater): FragmentFileSharingBinding =
    FragmentFileSharingBinding.inflate(layoutInflater)

  override fun initFragment() = setupFragment()


  private fun setupFragment() {
    fragmentComponent.inject(this)
    setupView()
  }

  private fun setupView() {
    binding.apply {

    }
  }

  override fun onResume() {
    super.onResume()
    getWifiPerantara()?.registerWifi()
  }

  override fun onPause() {
    super.onPause()
    getWifiPerantara()?.unregisterWifi()
  }

  private fun getWifiPerantara(): WifiPerantara? =
    requireActivity() as? WifiPerantara

  private fun exitActivity() {
    requireActivity().finish()
  }

  companion object {
    fun navigate(from: Context) {

    }
  }

}