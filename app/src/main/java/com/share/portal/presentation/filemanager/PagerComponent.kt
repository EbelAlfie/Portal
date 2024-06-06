package com.share.portal.presentation.filemanager

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

enum class Page(val index: Int) {
  FileExplorer(0),
  FileSharing(1);
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PagerScreen(
  pageFactory: List<PageFactory>
) {
  val pagerState = rememberPagerState { Page.entries.size }
  Scaffold(
    modifier = Modifier,
    topBar = {
      PortalBlueHeader {
        TabRow(selectedTabIndex = pagerState.currentPage) {
          pageFactory.forEach {
            it.TabIcon(
              modifier = Modifier.padding(5.dp),
              isSelected = it.pageId.index == pagerState.currentPage
            )
          }
        }
      }
    }
  ) { contentPadding ->
    val coroutineScope = rememberCoroutineScope()
    Tab(
      modifier = Modifier
        .padding(contentPadding)
        .fillMaxSize(),
      selected = pagerState.currentPage == index,
      onClick = {
        coroutineScope.launch {
          pagerState.animateScrollToPage(index)
        }
      }
    ) {
      HorizontalPager(
        modifier = Modifier
          .padding(contentPadding)
          .fillMaxSize(),
        state = pagerState
      ) {
        pageFactory.forEach {
          it.PageContent()
        }
      }
    }
  }

//  BottomSheetContainer(data = ) {
//    BottomSheetErrorContent(sheetData = it) {
//      it.onDismissRequest?.invoke()
//    }
//  }
}


//@Composable
//fun PageContent(page: Page) {
//  when (page) {
//    FileExplorer -> FileExploreScreen()
//    FileSharing -> PeersScreen()
//  }
//}

@Composable
fun TabIcons() {}

interface PageFactory {

  val pageId: Page

  @Composable
  fun TabIcon(modifier: Modifier, isSelected: Boolean)

  @Composable
  fun PageContent()

}