package com.share.portal.view.filemanager.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.share.portal.databinding.ItemParentFileBinding
import com.share.portal.view.filemanager.model.ParentData

class ParentAdapter: RecyclerView.Adapter<ParentViewHolder>() {
  private var mListener: ((String) -> Unit)? = null

  private val diffCallback = object: DiffUtil.ItemCallback<ParentData>() {
    override fun areItemsTheSame(oldItem: ParentData, newItem: ParentData): Boolean =
      oldItem.path == newItem.path
    override fun areContentsTheSame(oldItem: ParentData, newItem: ParentData): Boolean =
      oldItem.path == newItem.path
  }

  private val diffUtil = AsyncListDiffer(this, diffCallback)

  fun setListener(listener: (String) -> Unit) {
    mListener = listener
  }

  fun updateList(newList: List<ParentData>) {
    diffUtil.submitList(newList)
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParentViewHolder =
    ParentViewHolder(ItemParentFileBinding.inflate(
      LayoutInflater.from(parent.context), parent, false
    ))

  override fun getItemCount(): Int = diffUtil.currentList.size

  override fun onBindViewHolder(holder: ParentViewHolder, position: Int) {
    holder.bind(diffUtil.currentList[position], mListener)
  }
}