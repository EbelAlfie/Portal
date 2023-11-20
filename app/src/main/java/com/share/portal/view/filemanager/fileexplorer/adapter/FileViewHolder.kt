package com.share.portal.view.filemanager.fileexplorer.adapter

import androidx.recyclerview.widget.RecyclerView
import com.share.portal.R
import com.share.portal.databinding.ItemFileBinding
import com.share.portal.view.filemanager.fileexplorer.adapter.FileAdapter.FileListener
import com.share.portal.view.filemanager.fileexplorer.model.FileData
import com.share.portal.view.filemanager.fileexplorer.model.FileExtension

class FileViewHolder(private val binding: ItemFileBinding):
  RecyclerView.ViewHolder(binding.root) {

  fun bindData(data: FileData, mListener: FileListener?) {
    binding.apply {
      icIcon.setImageResource(data.extension.icon)
      tvFilename.text = data.file.name
      root.setOnClickListener {
        if (data.extension == FileExtension.FOLDER)
          mListener?.onFileClicked(null, data.file.path)
      }
      root.setOnLongClickListener {
        mListener?.onFileHold(binding, data.file); true
      }
    }
  }

  fun ItemFileBinding.setSelected() {
    root.backgroundTintList = root.context.getColorStateList(R.color.blue_default)
  }

  fun ItemFileBinding.setUnselected() {
    root.backgroundTintList = root.context.getColorStateList(R.color.white)
  }

}