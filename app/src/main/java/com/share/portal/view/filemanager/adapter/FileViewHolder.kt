package com.share.portal.view.filemanager.adapter

import androidx.recyclerview.widget.RecyclerView
import com.share.portal.R
import com.share.portal.databinding.ItemFileBinding
import com.share.portal.domain.models.FileEntity
import com.share.portal.view.filemanager.adapter.FileAdapter.FileListener

class FileViewHolder(private val binding: ItemFileBinding):
  RecyclerView.ViewHolder(binding.root) {
    fun bindData(data: FileEntity, mListener: FileListener?) {
      binding.apply {
        icIcon.setImageResource(R.drawable.baseline_folder_24)
        tvFilename.text = data.fileName
        root.setOnClickListener {
          mListener?.onFileClicked(data.fileName)
        }
      }
    }
}