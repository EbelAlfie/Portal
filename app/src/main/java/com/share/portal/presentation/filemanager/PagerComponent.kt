package com.share.portal.presentation.filemanager

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.share.portal.presentation.utils.PageFactory
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
        val coroutineScope = rememberCoroutineScope()
        pageFactory.forEachIndexed { index, page ->
          page.TabIcon(
            modifier = Modifier
              .padding(5.dp)
              .clickable {
                coroutineScope.launch {
                  pagerState.animateScrollToPage(index)
                }
              },
            isSelected = page.pageId.index == pagerState.currentPage
          )
        }

      }
    }
  ) { contentPadding ->
    HorizontalPager(
      modifier = Modifier
        .padding(contentPadding)
        .fillMaxSize(),
      state = pagerState
    ) {
      pageFactory[it]
        .PageContent()
    }
  }
}