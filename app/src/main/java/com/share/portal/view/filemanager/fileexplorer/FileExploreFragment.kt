package com.share.portal.view.filemanager.fileexplorer

import android.view.LayoutInflater
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.share.portal.databinding.FragmentFileExplorerBinding
import com.share.portal.databinding.ItemFileBinding
import com.share.portal.domain.models.FileTreeEntity
import com.share.portal.view.filemanager.fileexplorer.adapter.FileAdapter
import com.share.portal.view.filemanager.fileexplorer.adapter.ParentAdapter
import com.share.portal.view.filemanager.fileexplorer.model.FileData
import com.share.portal.view.filemanager.fileexplorer.model.FileExtension
import com.share.portal.view.general.ProgenitorFragment
import kotlinx.coroutines.launch
import javax.inject.Inject

class FileExploreFragment : ProgenitorFragment<FragmentFileExplorerBinding>() {

  @Inject
  lateinit var viewModel: FileViewModel

  private val fileAdapter: FileAdapter by lazy { FileAdapter() }
  private val parentAdapter: ParentAdapter by lazy { ParentAdapter() }

  override fun initBinding(layoutInflater: LayoutInflater) =
    FragmentFileExplorerBinding.inflate(layoutInflater)

  override fun initFragment() {
    fragmentComponent.inject(this)
    registerObservers()
    setupView()
  }

  override fun onBackPressed() {
    if (viewModel.canGoBack()) viewModel.goBack()
    else requireActivity().finish()
  }

  private fun registerObservers() {
    lifecycleScope.launch {
      viewModel.fileUiState.collect { updateUiState(it) }
    }
  }

  private fun updateUiState(uiState: FileUiState) {
    when (uiState) {
      is FileUiState.Loading -> {} //Display loading screen
      is FileUiState.Loaded ->
        loadData(uiState.allFiles.last())

      is FileUiState.Error ->
        showErrorDialog(uiState.cause)
    }
  }

  private fun setupView() {
    binding.run {
      rvParent.layoutManager =
        LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
      rvParent.adapter = parentAdapter

      setupFileAdapter()
      rvFiles.layoutManager =
        LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
      rvFiles.adapter = fileAdapter
    }
  }

  private fun setupFileAdapter() {
    fileAdapter.setFileListener(
      object : FileAdapter.FileListener() {
        override fun onFileClicked(filePath: ItemFileBinding, filePosition: Int, extension: FileExtension) {
          super.onFileClicked(filePath, filePosition, extension)
          (viewModel.fileUiState.value as? FileUiState.Loaded)?.let {
            when (it.operationMode) {
              is OperationMode.FileExplore -> viewModel.onFileClicked(filePosition)
              is OperationMode.FileSelect -> {
                viewModel.selectFile(filePosition)
                filePath.root.apply {
                  isSelected = !isSelected
                }
              }
            }
          }
        }

        override fun onFileHold(fileItem: ItemFileBinding, filePosition: Int) {
          super.onFileHold(fileItem, filePosition)
          (viewModel.fileUiState.value as? FileUiState.Loaded)?.let {
            when (it.operationMode) {
              is OperationMode.FileExplore -> viewModel.switchOperationMode(filePosition)
              is OperationMode.FileSelect -> {}
            }
              viewModel.selectFile(filePosition)
            fileItem.root.apply {
              isSelected = !isSelected
            }
          }
        }
      }
    )
  }

  private fun loadData(data: FileTreeEntity) {
    parentAdapter.update(data.current)
    fileAdapter.updateList(FileData.store(data))
  }

  private fun showErrorDialog(throwable: Throwable?) {
    showToast(throwable?.message)
  }
}