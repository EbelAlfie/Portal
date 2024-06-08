package com.share.portal.view.filemanager.fileexplorer.adapter

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.share.portal.R
import com.share.portal.databinding.ItemFileBinding
import com.share.portal.view.filemanager.fileexplorer.adapter.FileAdapter.FileListener
import com.share.portal.view.filemanager.fileexplorer.model.FileData

class FileViewHolder(private val binding: ItemFileBinding) :
  RecyclerView.ViewHolder(binding.root) {

  val context: Context = binding.root.context

  fun bindData(data: FileData, mListener: FileListener?) {
    binding.apply {
      icIcon.setImageResource(data.extension.icon)
      tvFilename.text = data.fileName
      root.setBackgroundColor(
        if (data.isSelected) context.getColor(R.color.blue_default)
        else context.getColor(R.color.white)
      )

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