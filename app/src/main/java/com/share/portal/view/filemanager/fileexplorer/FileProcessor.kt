package com.share.portal.view.filemanager.fileexplorer

import com.share.portal.databinding.ItemFileBinding
import com.share.portal.view.filemanager.fileexplorer.adapter.FileAdapter
import com.share.portal.view.filemanager.fileexplorer.adapter.FileAdapter.FileListener
import com.share.portal.view.filemanager.fileexplorer.model.FileExtension
import com.share.portal.view.filemanager.fileexplorer.model.FileState
import javax.inject.Inject

@Deprecated("Pindah ke viewmodel")
class FileProcessor @Inject constructor() {
  private var fileState: FileState = FileState.Exploration

  private val fileBuffer: MutableList<String> = mutableListOf()
  private lateinit var fileAdapter: FileAdapter
  private lateinit var viewModel: FileViewModel

  fun init(fileAdapter: FileAdapter, viewModel: FileViewModel) {
    this.fileAdapter = fileAdapter
    this.viewModel = viewModel
  }

  fun setAdapterListener() {
    fileAdapter.setFileListener(object: FileListener() {
      override fun onFileClicked(fileItem: ItemFileBinding, filePosition: Int, extension: FileExtension) {
        super.onFileClicked(fileItem, filePosition, extension)
        when (fileState) {
          FileState.Exploration -> {}
          else -> {}
        }
      }

      override fun onFileHold(fileItem: ItemFileBinding, filePosition: Int) {
        super.onFileHold(fileItem, filePosition)
        updateFileState()
      }
    })
  }

  /** Update global state **/
  fun updateFileState() {
    fileState = when (fileState) {
      FileState.Exploration -> FileState.Selection
      else -> FileState.Exploration
    }
  }

  /** file operations **/
  fun traverseFile(filePath: String) {
    viewModel.apply {
      getAllChildrenFiles(filePath)
    }
  }

  fun onBackPressed(callback: () -> Unit) {
    if (fileState == FileState.Selection) {
      fileState = FileState.Exploration
    } else
      callback.invoke()
  }

}
