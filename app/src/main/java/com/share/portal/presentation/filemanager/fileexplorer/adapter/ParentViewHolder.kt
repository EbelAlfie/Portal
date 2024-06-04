package com.share.portal.presentation.filemanager.fileexplorer.adapter

import androidx.recyclerview.widget.RecyclerView
import com.share.portal.databinding.ItemParentFileBinding
import com.share.portal.view.filemanager.fileexplorer.model.ParentData

class ParentViewHolder(private val binding: ItemParentFileBinding)
  : RecyclerView.ViewHolder(binding.root) {
    fun bind(parent: ParentData, listener: ((String) -> Unit)?) {
      binding.apply {
        tvParentName.text = parent.name
        root.setOnClickListener {
          listener?.invoke(parent.path)
        }
      }
    }
}