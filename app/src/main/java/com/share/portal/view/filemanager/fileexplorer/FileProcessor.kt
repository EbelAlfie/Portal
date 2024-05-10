package com.share.portal.view.filemanager.fileexplorer

import com.share.portal.view.filemanager.fileexplorer.adapter.FileAdapter
import com.share.portal.view.filemanager.fileexplorer.model.FileOperationState
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
