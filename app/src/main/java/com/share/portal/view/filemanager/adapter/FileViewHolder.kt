package com.share.portal.view.filemanager.adapter

import androidx.recyclerview.widget.RecyclerView
import com.share.portal.databinding.ItemFileBinding
import com.share.portal.view.filemanager.adapter.FileAdapter.FileListener
import com.share.portal.view.filemanager.model.FileData

class FileViewHolder(private val binding: ItemFileBinding):
  RecyclerView.ViewHolder(binding.root) {

  fun bindData(data: FileData, mListener: FileListener?) {
    binding.apply {
      icIcon.setImageResource(data.extension.icon)
      tvFilename.text = data.fileName
      root.setOnClickListener { mListener?.onFileClicked(data.path) }
    }
  }

}