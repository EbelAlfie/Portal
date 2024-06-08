package com.share.portal.view.filemanager.fileexplorer.adapter

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.share.portal.databinding.ItemFileBinding
import com.share.portal.view.filemanager.fileexplorer.adapter.FileAdapter.FileListener
import com.share.portal.view.filemanager.fileexplorer.model.FileData

class FileViewHolder(private val binding: ItemFileBinding) :
  RecyclerView.ViewHolder(binding.root) {

  val context: Context = binding.root.context

  fun bindData(data: FileData, mListener: FileListener?) {
    binding.apply {
      icIcon.setImageResource(data.extension.icon)
      tvFilename.text = data.file.name
      root.isSelected = data.isSelected

      root.setOnClickListener {
        mListener?.onFileClicked(data.file.path, adapterPosition, data.extension)
      }

      root.setOnLongClickListener {
        mListener?.onFileHold(adapterPosition)
        true
      }

    }
  }
}