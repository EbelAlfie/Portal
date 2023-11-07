package com.share.portal.view.filemanager

import android.Manifest
import android.view.LayoutInflater
import androidx.activity.OnBackPressedCallback
import androidx.recyclerview.widget.LinearLayoutManager
import com.share.portal.databinding.ActivityMainBinding
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
    super.onCreated()
    applicationComponent.inject(this)
    registerBackPress()
    setupViews()
    loadData()
  }

  private fun registerBackPress() {
    onBackPressedDispatcher.addCallback(this, object: OnBackPressedCallback(true) {
      override fun handleOnBackPressed() = onBackButtonPressed()
    })
  }

  private fun onBackButtonPressed() {

  }

  private fun setupViews() {
    fileAdapter.setFileListener ( object: FileListener {
      override fun onFileClicked(filePath: String) {
        viewModel.setRootPath(filePath)
        loadData()
        showToast(filePath)
      }
    })

    binding.rvFiles.run {
      layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
      adapter = fileAdapter
    }
  }

  private fun loadData() {
    fileAdapter.update(FileData.store(viewModel.getAllFiles()))
  }
}