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
import com.share.portal.view.filemanager.fileexplorer.model.FileOperationState
import java.io.File

class FileAdapter: RecyclerView.Adapter<FileViewHolder>() {
  private val selectedFile: MutableList<ItemFileBinding> = mutableListOf()

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

  fun updateList(items: FileTreeEntity) = diffUtil.submitList(FileData.store(items))

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FileViewHolder =
    FileViewHolder(ItemFileBinding.inflate(LayoutInflater.from(parent.context), parent, false))

  override fun getItemCount(): Int = diffUtil.currentList.size

  override fun onBindViewHolder(holder: FileViewHolder, position: Int) =
    holder.bindData(diffUtil.currentList[position], mListener)

  interface FileListener {
    fun onFileClicked(filePath: String, extension: FileExtension)
    fun onFileHold(file: File)
    fun onPerformSelect(view: ItemFileBinding, path: String)
    fun getFileState(): FileOperationState
  }

  fun selectFile(view: ItemFileBinding, isSelect: Boolean) {
    view.container.isSelected = isSelect
    if (isSelect) selectedFile.add(view)
    else selectedFile.remove(view)
  }

  fun deselectAll() {
    selectedFile.removeAll {
      it.container.isSelected = false
      true
    }
  }
}