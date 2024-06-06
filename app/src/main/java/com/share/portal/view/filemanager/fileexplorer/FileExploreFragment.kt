package com.share.portal.view.filemanager.fileexplorer

import android.view.LayoutInflater
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.share.portal.databinding.FragmentFileExplorerBinding
import com.share.portal.domain.models.FileTreeEntity
import com.share.portal.view.filemanager.fileexplorer.adapter.FileAdapter
import com.share.portal.view.filemanager.fileexplorer.adapter.ParentAdapter
import com.share.portal.view.filemanager.fileexplorer.model.FileExtension
import com.share.portal.view.general.ProgenitorFragment
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

class FileExploreFragment: ProgenitorFragment<FragmentFileExplorerBinding>() {

  @Inject
  lateinit var viewModel: FileViewModel

//  @Inject
//  lateinit var fileProcessor: FileProcessor

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
      viewModel.fileUiState.collectLatest { updateUiState(it) }
    }
  }

  fun updateUiState(uiState: FileUiState) {
    when (uiState) {
      is FileUiState.Loading ->
        {}
      is FileUiState.FileExplore ->
        loadData(uiState.allFiles.last())
      is FileUiState.Error ->
        showErrorDialog(uiState.cause)
      is FileUiState.FileSelect ->
        {}
    }
  }

  private fun setupView() {
    binding.run {
      setupRootAdapter()
      rvParent.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
      rvParent.adapter = parentAdapter

      setupFileAdapter()
      rvFiles.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
      rvFiles.adapter = fileAdapter
    }
  }

  private fun setupRootAdapter() {
    parentAdapter
  }

  private fun setupFileAdapter() {
    fileAdapter.setFileListener(
      object: FileAdapter.FileListener() {
        override fun onFileClicked(filePath: String, extension: FileExtension) {
          super.onFileClicked(filePath, extension)
          viewModel.onFileClicked(filePath)
        }

        override fun onFileHold(file: File) {
          super.onFileHold(file)
          viewModel.onLongHold(file)
        }
      }
    )
  }

  private fun loadData(data: FileTreeEntity) {
    parentAdapter.update(data.current)
    fileAdapter.updateList(data)
  }

  private fun showErrorDialog(throwable: Throwable?) {
    showToast(throwable?.message)
  }
}