package com.share.portal.main.adapter

import androidx.recyclerview.widget.RecyclerView
import com.share.portal.databinding.ItemFileBinding

class FileViewHolder(private val binding: ItemFileBinding):
  RecyclerView.ViewHolder(binding.root) {
    fun bindData(data: String) {
      binding.apply {
        icIcon.setImageResource()
        tvFilename.text = data
      }
    }
}