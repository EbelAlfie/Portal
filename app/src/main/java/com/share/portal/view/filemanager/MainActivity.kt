package com.share.portal.view.filemanager

import android.Manifest
import android.view.LayoutInflater
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

  private val adapter: FileAdapter by lazy { FileAdapter(this) }

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
    setupViews()
    loadData()
  }

  private fun setupViews() {
    adapter.setFileListener ( object: FileListener {
      override fun onFileClicked(filePath: String) {
        viewModel.setRootPath(filePath)
        loadData()
        showToast(filePath)
      }
    })

    binding.run {
      rvFiles.layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
      rvFiles.adapter = adapter
    }
  }

  private fun loadData() {
    adapter.setItems(FileData.store(viewModel.getAllFiles()))
  }
}