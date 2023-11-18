package com.share.portal.view.filemanager.fileexplorer.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.share.portal.databinding.ItemFileBinding
import com.share.portal.domain.models.FileTreeEntity
import com.share.portal.view.filemanager.fileexplorer.model.FileData
import com.share.portal.view.filemanager.fileexplorer.model.FileState
import com.share.portal.view.filemanager.fileexplorer.model.FileState.STATE_EXPLORE
import java.io.File

class FileAdapter: RecyclerView.Adapter<FileViewHolder>() {
  private var fileState: FileState = FileState.STATE_EXPLORE
  private var mListener: FileListener? = null

  private val diffCallback = object: DiffUtil.ItemCallback<FileData>() {
    override fun areItemsTheSame(oldItem: FileData, newItem: FileData) =
      oldItem.hashCode() == newItem.hashCode()
    override fun areContentsTheSame(oldItem: FileData, newItem: FileData) =
      oldItem.file.name == newItem.file.name
  }

  private val diffUtil = AsyncListDiffer(this, diffCallback)

  fun setFileListener (listener: FileListener) {
    mListener = listener
  }

  fun setState(state: FileState) {
    fileState = state
  }

  fun update(items: FileTreeEntity) = diffUtil.submitList(FileData.store(items))

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FileViewHolder =
    FileViewHolder(ItemFileBinding.inflate(LayoutInflater.from(parent.context), parent, false))

  override fun getItemCount(): Int =
    diffUtil.currentList.size

  override fun onBindViewHolder(holder: FileViewHolder, position: Int) =
    holder.bindData(diffUtil.currentList[position], mListener)

  fun getState(): FileState = fileState

  interface FileListener {
    fun onFileClicked(filePath: String)
    fun onFileHold(view: ItemFileBinding, file: File)
  }
}