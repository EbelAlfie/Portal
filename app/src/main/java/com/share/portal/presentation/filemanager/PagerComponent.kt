package com.share.portal.presentation.filemanager

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.share.portal.presentation.filemanager.Page.FileExplorer
import com.share.portal.presentation.filemanager.Page.FileSharing
import com.share.portal.presentation.filemanager.fileexplorer.FileExploreScreen
import com.share.portal.presentation.filemanager.wifisharing.PeersScreen

enum class Page(val index: Int) {
  FileExplorer(0),
  FileSharing(1);
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PagerScreen() {
  val pagerState = rememberPagerState { Page.entries.size }
  Scaffold(
    modifier = Modifier,
    topBar = {
      PortalBlueHeader()
    }
  ) { contentPadding ->
    HorizontalPager(
      modifier = Modifier.padding(contentPadding),
      state = pagerState
    ) {

    }
  }
}


@Composable
fun PagerContent(page: Page) {
  when (page) {
    FileExplorer -> FileExploreScreen()
    FileSharing -> PeersScreen()
  }
}

@Composable
fun TabIcons() {}

interface PageFactory {

  val pageId: Page

  @Composable
  fun TabIcon(isSelected: Boolean)

  @Composable
  fun PagerContent()

}