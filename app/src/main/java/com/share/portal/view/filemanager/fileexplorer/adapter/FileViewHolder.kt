package com.share.portal.view.filemanager.fileexplorer.adapter

import androidx.recyclerview.widget.RecyclerView
import com.share.portal.databinding.ItemFileBinding
import com.share.portal.view.filemanager.fileexplorer.adapter.FileAdapter.FileListener
import com.share.portal.view.filemanager.fileexplorer.model.FileData

class FileViewHolder(private val binding: ItemFileBinding) :
  RecyclerView.ViewHolder(binding.root) {

  fun bindData(data: FileData, mListener: FileListener?) {
    binding.apply {
      icIcon.setImageResource(data.extension.icon)
      tvFilename.text = data.fileName

      root.setOnClickListener {
        mListener?.onFileClicked(data.fileName, adapterPosition, data.extension)
      }

      root.setOnLongClickListener {
        mListener?.onFileHold(adapterPosition)
        true
      }
    }
  }
}