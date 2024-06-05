package com.share.portal.presentation.filemanager.fileexplorer

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.share.portal.R
import com.share.portal.domain.models.ParentFile
import com.share.portal.presentation.filemanager.Page
import com.share.portal.presentation.filemanager.PageFactory
import com.share.portal.presentation.ui.theme.GreyAlpha

class FileExplorerPage : PageFactory {

  override val pageId: Page = Page.FileExplorer

  @Composable
  override fun TabIcon(isSelected: Boolean) {
    val modifier = if (isSelected) Modifier
      .background(GreyAlpha, CircleShape)
      .padding(5.dp)
    else Modifier
    Icon(
      modifier = modifier,
      painter = painterResource(id = R.drawable.ic_folder),
      contentDescription = null
    )
  }

  @Composable
  override fun PageContent() {
    FileExploreScreen()
  }
}

@Composable
fun FileExploreScreen(
  fileViewModel: FileViewModel
) {
  BackHandler {

  }
  Column(
    Modifier.fillMaxSize()
  ) {
    ParentFilePath()
    FileScreen()
  }
}

@Composable
fun ParentFilePath() {
  TODO("Not yet implemented")
}

@Composable
fun FileScreen() {

}

