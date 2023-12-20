package com.share.portal.view.filemanager.fileexplorer.adapter

import androidx.recyclerview.widget.RecyclerView
import com.share.portal.databinding.ItemFileBinding
import com.share.portal.view.filemanager.fileexplorer.adapter.FileAdapter.FileListener
import com.share.portal.view.filemanager.fileexplorer.model.FileData
import com.share.portal.view.filemanager.fileexplorer.model.FileState

class FileViewHolder(private val binding: ItemFileBinding) :
  RecyclerView.ViewHolder(binding.root) {

  fun bindData(data: FileData, mListener: FileListener?) {
    binding.apply {
      icIcon.setImageResource(data.extension.icon)
      tvFilename.text = data.file.name

      root.setOnLongClickListener {
        mListener?.onFileHold(data.file)
        root.performClick()
        true
      }

      root.setOnClickListener {
        when (mListener?.getFileState()) {
          FileState.STATE_EXPLORE -> mListener.onFileClicked(data.file.path, data.extension)
          else -> mListener?.onPerformSelect(binding, data.file.path)
        }
      }

    }
  }
}