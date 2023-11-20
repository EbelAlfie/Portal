package com.share.portal.view.filemanager.wifisharing

import android.net.wifi.p2p.WifiP2pDevice
import android.net.wifi.p2p.WifiP2pManager
import android.util.Log
import android.view.LayoutInflater
import androidx.activity.OnBackPressedCallback
import androidx.recyclerview.widget.LinearLayoutManager
import com.share.portal.databinding.FragmentFileSharingBinding
import com.share.portal.view.filemanager.MainActivity
import com.share.portal.view.filemanager.WifiPerantara
import com.share.portal.view.filemanager.wifisharing.adapter.PeerAdapter
import com.share.portal.view.filemanager.wifisharing.adapter.PeerAdapter.PeerConnectionListener
import com.share.portal.view.filemanager.wifisharing.adapter.PeerAdapter.PeerItemListener
import com.share.portal.view.general.ProgenitorFragment

class FileSharingFragment: ProgenitorFragment<FragmentFileSharingBinding>() {

  //@Inject
  //lateinit var viewModel: WifiSharingViewmodel

  private val peerAdapter: PeerAdapter by lazy { PeerAdapter() }
  override fun initBinding(layoutInflater: LayoutInflater): FragmentFileSharingBinding =
    FragmentFileSharingBinding.inflate(layoutInflater)

  override fun initFragment() = setupFragment()

  private fun setupFragment() {
    fragmentComponent.inject(this)
    registerBackPress()
    setupView()
    getPeers()
  }

  private fun getPeers() {
    (requireActivity() as MainActivity).apply {
      with(provideP2pService()) {

        setPeerConnectionListener(object: PeerConnectionListener {
          override fun onConnectionSuccess(device: WifiP2pDevice) { requestConnectionInfo() }
          override fun onConnectionFailed(statusCode: Int) = showError(statusCode)
        })

        setConnectionInfoListener {
//          if (it.groupFormed && !it.isGroupOwner)
//            provideViewModel().connectWSClient(it.groupOwnerAddress)
          showToast(it.toString())
          Log.d("WIFIGROUP", it.toString())
        }

        setPeerListener {
          peerAdapter.submitPeers(it)
        }

        discoverPeers()
      }
    }

  }

  private fun registerBackPress() {
    requireActivity().apply {
      onBackPressedDispatcher.addCallback(this@FileSharingFragment,
        object: OnBackPressedCallback(true) {
          override fun handleOnBackPressed() = finish()
        }
      )
    }
  }

  private fun setupView() {
    peerAdapter.setPeerListener (object: PeerItemListener {
      override fun onPeerClicked(peer: WifiP2pDevice) =
        (requireActivity() as MainActivity).provideP2pService().requestConnection(peer)
    })
    binding.apply {
      btnRefresh.setOnClickListener {
        (requireActivity() as MainActivity).provideP2pService().discoverPeers()
      }
      rvPeers.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
      rvPeers.adapter = peerAdapter
    }
  }

  override fun onResume() {
    super.onResume()
    //getWifiPerantara()?.registerWifi()
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

  private fun getWifiPerantara(): WifiPerantara? = requireActivity() as? WifiPerantara

}