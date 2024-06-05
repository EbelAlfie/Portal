package com.share.portal.presentation.filemanager.fileexplorer

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.share.portal.R
import com.share.portal.domain.models.ParentFile
import com.share.portal.presentation.filemanager.Page
import com.share.portal.presentation.filemanager.PageFactory
import com.share.portal.presentation.filemanager.fileexplorer.model.FileData
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
    //FileExploreScreen()
  }
}

@Composable
fun FileExploreScreen(
  fileViewModel: FileViewModel
) {
  BackHandler (fileViewModel.canGoBack, fileViewModel::goBack)
  Column(
    Modifier.fillMaxSize()
  ) {
    ParentFileContent()
    FileScreen()
  }
}

@Composable
fun ParentFileContent() {
  LazyRow {

  }
}

@Composable
fun FileScreen() {

}

@Composable
fun ParentFile(root: ParentFile) {
  Row(
    modifier = Modifier.background(Color.White),
    verticalAlignment = Alignment.CenterVertically
  ) {
    Icon(
      painter = painterResource(id = R.drawable.ic_arrow_right),
      contentDescription = null
    )
    Text(text = "dataaaaaaaaaaaaa")
  }
}

@Composable
fun FileItem(file: FileData) {
  Row {
    Icon(
      painter = painterResource(id = file.extension.icon),
      contentDescription = null
    )
    Text(text = file.file.name)
    Icon(
      modifier = Modifier.border(1.dp, Color.Black, RectangleShape),
      painter = painterResource(id = R.drawable.ic_arrow_right),
      contentDescription = null
    )
  }
}