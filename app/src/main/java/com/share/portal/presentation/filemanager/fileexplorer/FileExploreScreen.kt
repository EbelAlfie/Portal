package com.share.portal.presentation.filemanager.fileexplorer

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.share.portal.R
import com.share.portal.presentation.filemanager.Page
import com.share.portal.presentation.filemanager.PageFactory
import com.share.portal.presentation.filemanager.fileexplorer.FileUiState.FileExplore
import com.share.portal.presentation.filemanager.fileexplorer.model.FileData
import com.share.portal.presentation.filemanager.fileexplorer.model.ParentData
import com.share.portal.presentation.ui.theme.Grey
import com.share.portal.presentation.ui.theme.GreyAlpha
import dagger.hilt.EntryPoint

class FileExplorerPage(
  private val viewModel: FileViewModel
) : PageFactory {

  override val pageId: Page = Page.FileExplorer

  @Composable
  override fun TabIcon(modifier: Modifier, isSelected: Boolean) {
    val textModifier = if (isSelected) Modifier
      .background(GreyAlpha, CircleShape)
      .padding(5.dp)
    else modifier
    Icon(
      modifier = textModifier,
      painter = painterResource(id = R.drawable.ic_folder),
      tint = Grey,
      contentDescription = null
    )
  }

  @Composable
  override fun PageContent() {
    FileExploreScreen(viewModel)
  }
}

@Composable
fun FileExploreScreen(
  fileViewModel: FileViewModel
) {
  BackHandler (fileViewModel.canGoBack(), fileViewModel::goBack)
  Column(
    Modifier.fillMaxSize()
  ) {
    val uiState by fileViewModel.fileUiState.collectAsState()
    when (uiState) {
      is FileUiState.Loading -> {}
      is FileUiState.FileExplore -> FileExploreContent(
        uiState = uiState as FileExplore
      )
      else -> {}
    }
  }
}

@Composable
fun FileExploreContent(
  uiState: FileUiState.FileExplore,
) {
  val newFile = uiState.allFiles.last()
  ParentFileContent(ParentData.toParentDataList(newFile.current))
  FileScreen(FileData.store(newFile), { file ->})
}

@Composable
private fun ParentFileContent(rootFile: List<ParentData>) {
  LazyRow {
    items(rootFile) {
      ParentFile(it)
    }
  }
}

@Composable
private fun FileScreen(
  files: List<FileData>,
  onFileClicked: (FileData) -> Unit
  ) {
  LazyColumn {
    items(files) {
      FileItem(it)
    }
  }
}