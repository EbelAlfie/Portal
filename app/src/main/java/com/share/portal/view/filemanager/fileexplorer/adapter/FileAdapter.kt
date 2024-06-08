package com.share.portal.view.filemanager.fileexplorer.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.share.portal.databinding.ItemFileBinding
import com.share.portal.domain.models.FileTreeEntity
import com.share.portal.view.filemanager.fileexplorer.model.FileData
import com.share.portal.view.filemanager.fileexplorer.model.FileExtension

class FileAdapter: RecyclerView.Adapter<FileViewHolder>() {
  private var mListener: FileListener? = null

  private val diffCallback = object: DiffUtil.ItemCallback<FileData>() {
    override fun areItemsTheSame(oldItem: FileData, newItem: FileData) =
      oldItem.hashCode() == newItem.hashCode()
    override fun areContentsTheSame(oldItem: FileData, newItem: FileData) =
      oldItem.fileName == newItem.fileName
  }

  private val diffUtil = AsyncListDiffer(this, diffCallback)

  fun setFileListener (listener: FileListener) {
    mListener = listener
  }

  fun updateList(items: List<FileData>) = diffUtil.submitList(items)

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FileViewHolder =
    FileViewHolder(ItemFileBinding.inflate(LayoutInflater.from(parent.context), parent, false))

  override fun getItemCount(): Int = diffUtil.currentList.size

  override fun onBindViewHolder(holder: FileViewHolder, position: Int) =
    holder.bindData(diffUtil.currentList[position], mListener)

  fun notifySelectedFile(selectedIndices: MutableList<Int>) {
    selectedIndices.forEach { selectedIndex ->
      diffUtil.currentList[selectedIndex] = diffUtil.currentList[selectedIndex].copy(
        isSelected = true
      )
      notifyItemChanged(selectedIndex)
    }
  }

  abstract class FileListener {
    open fun onFileClicked(filePath: String, filePosition: Int, extension: FileExtension) {
      if (extension == FileExtension.FOLDER) return
    }
    open fun onFileHold(filePosition: Int) {}
  }

}