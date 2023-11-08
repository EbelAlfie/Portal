package com.share.portal.view.filemanager

import android.Manifest
import android.view.LayoutInflater
import androidx.activity.OnBackPressedCallback
import androidx.recyclerview.widget.LinearLayoutManager
import com.share.portal.databinding.ActivityMainBinding
import com.share.portal.domain.models.FileTreeEntity
import com.share.portal.view.filemanager.adapter.FileAdapter
import com.share.portal.view.filemanager.adapter.FileAdapter.FileListener
import com.share.portal.view.filemanager.model.FileData
import com.share.portal.view.general.PermissionActivity
import javax.inject.Inject

class MainActivity : PermissionActivity<ActivityMainBinding>() {

  @Inject
  lateinit var viewModel: MainViewModel

  private val fileAdapter: FileAdapter by lazy { FileAdapter() }

  override fun getPermissions(): List<String> =
    listOf(
      Manifest.permission.WRITE_EXTERNAL_STORAGE,
      Manifest.permission.READ_EXTERNAL_STORAGE
    )

  override fun initBinding(layoutInflater: LayoutInflater): ActivityMainBinding =
    ActivityMainBinding.inflate(layoutInflater)

  override fun onCreated() {
    setPermissionListener(object: PermissionListener {
      override fun onGranted() {
        setupActivity()
      }
      override fun onDenied(permission: String) {
        showToast("Permission $permission must be enabled!")
        finish()
      }
    })
    checkPermissions()
  }

  private fun setupActivity() {
    applicationComponent.inject(this)
    registerBackPress()
    setupViews()
    getFile()
  }

  private fun registerBackPress() {
    onBackPressedDispatcher.addCallback(this, object: OnBackPressedCallback(true) {
      override fun handleOnBackPressed() = onBackButtonPressed()
    })
  }

  private fun onBackButtonPressed() {
    viewModel.setRootPath(fileAdapter.getParent().path)
    getFile()
  }

  private fun setupViews() {
    setupToolbar()
    fileAdapter.setFileListener ( object: FileListener {
      override fun onFileClicked(filePath: String) {
        viewModel.setRootPath(filePath)
        getFile()
        showToast(filePath)
      }
    })

    binding.rvFiles.run {
      layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
      adapter = fileAdapter
    }
  }

  private fun setupToolbar() {
    binding.toolbar.apply {
      icBack.setOnClickListener { onBackPressedDispatcher.onBackPressed() }
      icFile.setOnClickListener {  }
    }
  }

  private fun getFile() =
    viewModel.getAllFiles(::loadData, ::showErrorDialog)

  private fun loadData(data: FileTreeEntity) {
    fileAdapter.update(FileData.store(data))
  }

  private fun showErrorDialog(throwable: Throwable) {
    showToast(throwable.message, )
  }
}