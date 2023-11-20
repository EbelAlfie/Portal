package com.share.portal.view.filemanager.wifisharing.adapter

import android.net.wifi.p2p.WifiP2pDevice
import androidx.recyclerview.widget.RecyclerView
import com.share.portal.databinding.ItemPeerBinding
import com.share.portal.view.filemanager.wifisharing.adapter.PeerAdapter.PeerItemListener

class PeerViewHolder(private val binding: ItemPeerBinding): RecyclerView.ViewHolder(binding.root) {
  fun bindData(peer: WifiP2pDevice, listener: PeerItemListener?) {
    binding.apply {
      tvDeviceName.text = peer.deviceName
      tvDeviceIp.text = peer.deviceAddress
      tvDeviceStatus.text = setStatusText(peer.status)
      root.setOnClickListener {
        listener?.onPeerClicked(peer)
      }
    }
  }

  private fun setStatusText(status: Int): String {
    return when (status) {
      WifiP2pDevice.AVAILABLE -> "Available"
      WifiP2pDevice.CONNECTED -> "Connected"
      WifiP2pDevice.INVITED -> "Invited"
      WifiP2pDevice.FAILED -> "Failed"
      else -> "None"
    }
  }
}