package com.share.portal.view.filemanager.fileexplorer

import com.share.portal.view.filemanager.fileexplorer.adapter.FileAdapter
import com.share.portal.view.filemanager.fileexplorer.adapter.FileAdapter.FileListener
import com.share.portal.view.filemanager.fileexplorer.model.FileExtension
import com.share.portal.view.filemanager.fileexplorer.model.FileOperationState
import java.io.File
import javax.inject.Inject

class FileProcessor @Inject constructor() {
  private var fileState: FileOperationState = FileOperationState.STATE_EXPLORATION

  private val fileBuffer: MutableList<String> = mutableListOf()
  private lateinit var fileAdapter: FileAdapter
  private lateinit var viewModel: FileViewModel

  fun init(fileAdapter: FileAdapter, viewModel: FileViewModel) {
    this.fileAdapter = fileAdapter
    this.viewModel = viewModel
  }

  fun setAdapterListener() {
    fileAdapter.setFileListener(object: FileListener() {
      override fun onFileClicked(filePath: String, extension: FileExtension) {
        super.onFileClicked(filePath, extension)
        when (fileState) {
          FileOperationState.STATE_EXPLORATION -> {}
          else -> {}
        }
      }

      override fun onFileHold(file: File) {
        super.onFileHold(file)
        updateFileState()
      }
    })
  }

  /** Update global state **/
  fun updateFileState() {
    fileState = when (fileState) {
      FileOperationState.STATE_EXPLORATION -> FileOperationState.STATE_SELECTION
      else -> FileOperationState.STATE_EXPLORATION
    }
  }

  /** file operations **/
  fun traverseFile(filePath: String) {
    viewModel.apply {
      setRootPath(filePath)
      getAllFiles()
    }
  }

  fun onBackPressed(callback: () -> Unit) {
    if (fileState == FileOperationState.STATE_SELECTION) {
      fileState = FileOperationState.STATE_EXPLORATION
    } else
      callback.invoke()
  }

}
