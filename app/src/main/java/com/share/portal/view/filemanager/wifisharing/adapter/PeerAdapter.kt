package com.share.portal.view.filemanager.wifisharing.adapter

import android.net.wifi.p2p.WifiP2pDevice
import android.net.wifi.p2p.WifiP2pDeviceList
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.share.portal.databinding.ItemPeerBinding

class PeerAdapter: RecyclerView.Adapter<PeerViewHolder>() {
  private var mListener: PeerItemListener? = null
  interface PeerItemListener {
    fun onPeerClicked(peer: WifiP2pDevice)
  }

  private val diffCallback = object: DiffUtil.ItemCallback<WifiP2pDevice>() {
    override fun areItemsTheSame(oldItem: WifiP2pDevice, newItem: WifiP2pDevice): Boolean =
      oldItem === newItem

    override fun areContentsTheSame(oldItem: WifiP2pDevice, newItem: WifiP2pDevice
    ): Boolean = oldItem == newItem //TODO change

  }
  private val diffUtil = AsyncListDiffer(this, diffCallback)

  fun setPeerListener(listener: PeerItemListener) {
    mListener = listener
  }
  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PeerViewHolder =
    PeerViewHolder(ItemPeerBinding.inflate(LayoutInflater.from(parent.context), parent, false))

  override fun getItemCount(): Int = diffUtil.currentList.size

  override fun onBindViewHolder(holder: PeerViewHolder, position: Int) {
    holder.bindData(diffUtil.currentList[position])
  }

  fun submitPeers(data: WifiP2pDeviceList) {
    diffUtil.submitList(data.deviceList.toList())
  }

}