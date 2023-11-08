package com.share.portal.view.filemanager.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.share.portal.databinding.ItemFileBinding
import com.share.portal.view.filemanager.model.FileData

class FileAdapter: RecyclerView.Adapter<FileViewHolder>() {
  private var mListener: FileListener? = null

  private val diffCallback = object: DiffUtil.ItemCallback<FileData>() {
    override fun areItemsTheSame(oldItem: FileData, newItem: FileData) =
      oldItem.fileName == newItem.fileName
    override fun areContentsTheSame(oldItem: FileData, newItem: FileData) =
      oldItem == newItem
  }

  fun setFileListener (listener: FileListener) {
    mListener = listener
  }

  private val diffUtil = AsyncListDiffer(this, diffCallback)

  fun update(items: List<FileData>) = diffUtil.submitList(items)

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FileViewHolder =
    FileViewHolder(ItemFileBinding.inflate(LayoutInflater.from(parent.context), parent, false))

  override fun getItemCount(): Int =
    diffUtil.currentList.size

  override fun onBindViewHolder(holder: FileViewHolder, position: Int) =
    holder.bindData(diffUtil.currentList[position], mListener)

  fun getParent(): FileData = diffUtil.currentList[0]

  interface FileListener {
    fun onFileClicked(filePath: String)
  }
}