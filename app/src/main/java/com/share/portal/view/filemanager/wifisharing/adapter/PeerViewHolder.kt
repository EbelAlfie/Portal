package com.share.portal.view.filemanager.wifisharing.adapter

import android.net.wifi.p2p.WifiP2pDevice
import androidx.recyclerview.widget.RecyclerView
import com.share.portal.databinding.ItemPeerBinding

class PeerViewHolder(private val binding: ItemPeerBinding): RecyclerView.ViewHolder(binding.root) {
  fun bindData(data: WifiP2pDevice) {
    binding.apply {
      tvDeviceName.text = data.deviceName
      tvDeviceIp.text = data.deviceAddress
    }
  }
}