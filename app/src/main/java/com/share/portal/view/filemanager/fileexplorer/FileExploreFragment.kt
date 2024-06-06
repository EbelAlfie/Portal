package com.share.portal.view.filemanager.fileexplorer

import android.view.LayoutInflater
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.share.portal.databinding.FragmentFileExplorerBinding
import com.share.portal.domain.models.FileTreeEntity
import com.share.portal.view.filemanager.fileexplorer.adapter.FileAdapter
import com.share.portal.view.filemanager.fileexplorer.adapter.ParentAdapter
import com.share.portal.view.general.ProgenitorFragment
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
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
      is FileUiState.Loaded ->
        loadData(uiState.files)
      is FileUiState.Error ->
        showErrorDialog(uiState.cause)
    }
  }

  private fun setupView() {
    binding.run {
      rvFiles.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
      rvFiles.adapter = fileAdapter

      rvParent.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
      rvParent.adapter = parentAdapter
    }
  }

  private fun loadData(data: FileTreeEntity) {
    parentAdapter.update(data.current)
    fileAdapter.updateList(data)
  }

  private fun showErrorDialog(throwable: Throwable?) {
    showToast(throwable?.message)
  }
}