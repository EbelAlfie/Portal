package com.share.portal.view.filemanager.fileexplorer

import com.share.portal.databinding.ItemFileBinding
import com.share.portal.view.filemanager.fileexplorer.adapter.FileAdapter
import com.share.portal.view.filemanager.fileexplorer.adapter.FileAdapter.FileListener
import com.share.portal.view.filemanager.fileexplorer.model.FileExtension
import com.share.portal.view.filemanager.fileexplorer.model.FileState
import java.io.File
import javax.inject.Inject

class FileProcessor @Inject constructor() {
  private var fileState: FileState = FileState.STATE_EXPLORE

  private val fileBuffer: MutableList<String> = mutableListOf()
  private lateinit var fileAdapter: FileAdapter
  private lateinit var viewModel: FileViewModel

  fun init(fileAdapter: FileAdapter, viewModel: FileViewModel) {
    this.fileAdapter = fileAdapter
    this.viewModel = viewModel
  }

  fun setAdapterListener() {
    fileAdapter.setFileListener (object: FileListener {
      override fun onFileClicked(filePath: String, extension: FileExtension) {
        traverseFile(filePath)
      }
      override fun onPerformSelect(view: ItemFileBinding, path: String) {
        val isSelect = performSelect(path)
        fileAdapter.selectFile(view, isSelect)
      }
      override fun onFileHold(file: File) {
        fileState = FileState.STATE_SELECTION
      }
      override fun getFileState(): FileState = fileState
    })
  }

  /** file operations **/
  fun traverseFile(filePath: String) {
    viewModel.setRootPath(filePath)
    viewModel.getAllFiles()
  }

  fun performSelect(filePath: String): Boolean {
    val isSelect = fileBuffer.indexOf(filePath)
    if (isSelect == -1) fileBuffer.add(filePath)
    else fileBuffer.remove(filePath)
    return isSelect == -1
  }

  private fun clearAll() {
    fileBuffer.clear()
    fileAdapter.deselectAll()
  }

  fun onBackPressed(callback: () -> Unit) {
    if (fileState == FileState.STATE_SELECTION) {
      fileState = FileState.STATE_EXPLORE
      clearAll()
    } else
      callback.invoke()
  }

}
