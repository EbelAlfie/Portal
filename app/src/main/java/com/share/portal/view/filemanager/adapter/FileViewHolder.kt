package com.share.portal.view.filemanager.adapter

import androidx.recyclerview.widget.RecyclerView
import com.share.portal.databinding.ItemFileBinding
import com.share.portal.view.filemanager.adapter.FileAdapter.FileListener
import com.share.portal.view.filemanager.model.FileData
import com.share.portal.view.filemanager.model.FileExtension

class FileViewHolder(private val binding: ItemFileBinding):
  RecyclerView.ViewHolder(binding.root) {

  fun bindData(data: FileData, mListener: FileListener?) {
    binding.apply {
      if (!data.isParent) icIcon.setImageResource(data.extension.icon)
      tvFilename.text = data.fileName
      root.setOnClickListener {
        if (data.extension == FileExtension.FOLDER)
          mListener?.onFileClicked(data.path)
      }
    }
  }

}