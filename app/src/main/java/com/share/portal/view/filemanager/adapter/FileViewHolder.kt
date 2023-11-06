package com.share.portal.view.filemanager.adapter

import androidx.recyclerview.widget.RecyclerView
import com.share.portal.R
import com.share.portal.databinding.ItemFileBinding
import com.share.portal.domain.models.FileEntity

class FileViewHolder(private val binding: ItemFileBinding):
  RecyclerView.ViewHolder(binding.root) {
    fun bindData(data: FileEntity) {
      binding.apply {
        icIcon.setImageResource(R.drawable.baseline_folder_24)
        tvFilename.text = data.fileName
        root.setOnClickListener {

        }
      }
    }
}