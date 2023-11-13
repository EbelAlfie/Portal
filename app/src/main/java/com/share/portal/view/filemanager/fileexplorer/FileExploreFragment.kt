package com.share.portal.view.filemanager.fileexplorer

import android.view.LayoutInflater
import androidx.activity.OnBackPressedCallback
import androidx.recyclerview.widget.LinearLayoutManager
import com.share.portal.databinding.FragmentFileExplorerBinding
import com.share.portal.databinding.ItemFileBinding
import com.share.portal.domain.models.FileTreeEntity
import com.share.portal.view.filemanager.MainActivity
import com.share.portal.view.filemanager.fileexplorer.adapter.FileAdapter
import com.share.portal.view.filemanager.fileexplorer.adapter.FileAdapter.FileListener
import com.share.portal.view.filemanager.fileexplorer.adapter.ParentAdapter
import com.share.portal.view.general.ProgenitorFragment
import javax.inject.Inject

class FileExploreFragment: ProgenitorFragment<FragmentFileExplorerBinding>() {

  @Inject
  lateinit var viewModel: FileViewModel

  private val fileAdapter: FileAdapter by lazy { FileAdapter() }
  private val parentAdapter: ParentAdapter by lazy { ParentAdapter() }

  override fun initBinding(layoutInflater: LayoutInflater): FragmentFileExplorerBinding =
    FragmentFileExplorerBinding.inflate(layoutInflater)

  override fun initFragment() {
    fragmentComponent.inject(this)
    registerObservers()
    registerBackPress()
    setupView()
  }

  private fun registerBackPress() { //TODO optimise
    (requireActivity() as MainActivity)
      .onBackPressedDispatcher.addCallback(this, object: OnBackPressedCallback(true) {
      override fun handleOnBackPressed() = onBackButtonPressed()
    })
  }

  private fun onBackButtonPressed() {
    val currentRoot = parentAdapter.getCurrentNode().substringBeforeLast("/")
    if (currentRoot.isNotBlank()) traverseFile(currentRoot)
    else requireActivity().finish()
  }

  private fun registerObservers() {
    viewModel.fileData().observe(this) {
      loadData(it)
    }
    viewModel.errorFile().observe(this) {
      showErrorDialog(it)
    }
  }

  private fun setupView() {

    fileAdapter.setFileListener (object: FileListener {
      override fun onFileClicked(filePath: String) = traverseFile(filePath)
      override fun onFileHold(view: ItemFileBinding) {}
    })
    parentAdapter.setListener(::traverseFile)

    binding.run {
      rvFiles.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
      rvFiles.adapter = fileAdapter

      rvParent.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
      rvParent.adapter = parentAdapter
    }
  }

  private fun getFile() = viewModel.getAllFiles()

  private fun traverseFile(filePath: String) {
    viewModel.setRootPath(filePath)
    getFile()
    showToast(filePath)
  }

  private fun loadData(data: FileTreeEntity) {
    parentAdapter.update(data.current)
    fileAdapter.update(data)
  }

  private fun showErrorDialog(throwable: Throwable) {
    showToast(throwable.message)
  }
}