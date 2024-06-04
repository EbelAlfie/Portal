package com.share.portal.view.filemanager.wifisharing

import android.net.wifi.p2p.WifiP2pDevice
import android.net.wifi.p2p.WifiP2pManager
import android.util.Log
import android.view.LayoutInflater
import androidx.recyclerview.widget.LinearLayoutManager
import com.share.portal.databinding.FragmentFileSharingBinding
import com.share.portal.view.filemanager.WifiPerantara
import com.share.portal.view.filemanager.wifisharing.adapter.PeerAdapter
import com.share.portal.view.filemanager.wifisharing.adapter.PeerAdapter.PeerConnectionListener
import com.share.portal.view.filemanager.wifisharing.adapter.PeerAdapter.PeerItemListener
import com.share.portal.view.general.ProgenitorFragment

class FileSharingFragment : ProgenitorFragment<FragmentFileSharingBinding>() {

//  @Inject
//  lateinit var viewModel: WifiSharingViewmodel

  private val peerAdapter: PeerAdapter by lazy { PeerAdapter() }
  override fun initBinding(layoutInflater: LayoutInflater): FragmentFileSharingBinding =
    FragmentFileSharingBinding.inflate(layoutInflater)

  override fun initFragment() = setupFragment()

  override fun onBackPressed() {
    requireActivity().finish()
  }

  private fun setupFragment() {
    fragmentComponent.inject(this)
    setupView()
    getPeers()
  }

  private fun setupView() {
    setupPeerAdapter()
    binding.apply {
      btnRefresh.setOnClickListener {
        getWifiPerantara().getP2pService().initiatePeerDiscovery()
      }
      rvPeers.layoutManager =
        LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
      rvPeers.adapter = peerAdapter
    }
  }

  private fun setupPeerAdapter() {
    peerAdapter.setItemListener(object : PeerItemListener {
      override fun onPeerClicked(peer: WifiP2pDevice) =
        getWifiPerantara().getP2pService().requestConnection(peer)
    })
  }

  private fun getPeers() {
    getWifiPerantara().getP2pService().apply {
      setPeerConnectionListener(object : PeerConnectionListener {
        override fun onConnectionSuccess(device: WifiP2pDevice) {
          Log.d("CONNECtiOn", device.toString())
        }
        override fun onConnectionFailed(statusCode: Int) = showError(statusCode)
      })

      setConnectionInfoListener {
        showToast(it.toString())
        Log.d("WIFIGROUP", it.toString())
      }

      setPeerListener { peerAdapter.submitPeers(it) }

      initiatePeerDiscovery()
    }
  }

  override fun onResume() {
    super.onResume()
    getWifiPerantara().registerWifi()
  }

  override fun onPause() {
    super.onPause()
    //getWifiPerantara()?.unregisterWifi()
  }

  private fun showError(reason: Int) {
    when (reason) {
      WifiP2pManager.ERROR -> showToast("Error")
      WifiP2pManager.P2P_UNSUPPORTED -> showToast("Unsupport P2P")
      WifiP2pManager.BUSY -> showToast("Busy")
      else -> showToast("Error")
    }
  }

  private fun getWifiPerantara(): WifiPerantara = requireActivity() as WifiPerantara

}