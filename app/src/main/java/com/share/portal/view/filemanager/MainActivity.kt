package com.share.portal.view.filemanager

import android.Manifest
import android.view.LayoutInflater
import androidx.activity.OnBackPressedCallback
import androidx.recyclerview.widget.LinearLayoutManager
import com.share.portal.R
import com.share.portal.databinding.ActivityMainBinding
import com.share.portal.databinding.ItemFileBinding
import com.share.portal.domain.models.FileTreeEntity
import com.share.portal.view.filemanager.adapter.FileAdapter
import com.share.portal.view.filemanager.adapter.FileAdapter.FileListener
import com.share.portal.view.filemanager.adapter.ParentAdapter
import com.share.portal.view.general.PermissionActivity
import com.share.portal.view.utils.BottomSheetPopUp
import com.share.portal.view.wifisharing.WifiSharingActivity
import javax.inject.Inject

class MainActivity : PermissionActivity<ActivityMainBinding>() {

  @Inject
  lateinit var viewModel: MainViewModel

  private val fileAdapter: FileAdapter by lazy { FileAdapter() }
  private val parentAdapter: ParentAdapter by lazy { ParentAdapter() }

  /** Permission exclusives **/
  override fun getPermissions(): List<String> =
    listOf(
      Manifest.permission.WRITE_EXTERNAL_STORAGE,
      Manifest.permission.READ_EXTERNAL_STORAGE
    )

  override fun onPermissionGranted() = setupActivity()

  override fun onPermissionDenied(permission: String) =
    showPermissionDeniedDialog(permission)

  override fun onPermissionDeniedPermanently(permission: String) =
    showPermissionDeniedDialog(permission)

  private fun showPermissionDeniedDialog(permission: String) {
    BottomSheetPopUp.newDialog(
      supportFragmentManager,
      this,
      R.drawable.ic_folder,
      getString(R.string.warning_general_title),
      getString(R.string.warning_general_content),
      onDismiss = ::finish
    )
  }

  /** Activity exclusives **/
  override fun initBinding(layoutInflater: LayoutInflater): ActivityMainBinding =
    ActivityMainBinding.inflate(layoutInflater)

  override fun onCreated() {
    checkPermissions()
  }

  private fun setupActivity() {
    applicationComponent.inject(this)
    registerBackPress()
    setupViews()
    registerObservers()
  }

  private fun registerObservers() {
    viewModel.fileData().observe(this) {
      loadData(it)
    }
    viewModel.errorFile().observe(this) {
      showErrorDialog(it)
    }
  }

  private fun registerBackPress() {
    onBackPressedDispatcher.addCallback(this, object: OnBackPressedCallback(true) {
      override fun handleOnBackPressed() = onBackButtonPressed()
    })
  }

  private fun setupToolbar() {
    binding.toolbar.apply {
      icFile.setOnClickListener {
        startActivity(WifiSharingActivity.navigate(this@MainActivity))
      }
      tvTitle.text = getString(R.string.portal_label)
    }
  }

  private fun onBackButtonPressed() {
    val currentRoot = parentAdapter.getCurrentNode().substringBeforeLast("/")
    if (currentRoot.isNotBlank()) traverseFile(currentRoot)
    else finish()
  }

  private fun setupViews() {
    setupToolbar()
    fileAdapter.setFileListener (object: FileListener {
      override fun onFileClicked(filePath: String) = traverseFile(filePath)
      override fun onFileHold(view: ItemFileBinding) {}
    })
    parentAdapter.setListener(::traverseFile)

    binding.run {
      rvFiles.layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
      rvFiles.adapter = fileAdapter

      rvParent.layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false)
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