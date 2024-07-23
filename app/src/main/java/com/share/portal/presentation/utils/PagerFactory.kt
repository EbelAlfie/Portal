package com.share.portal.presentation.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.share.portal.presentation.filemanager.Page

interface PageFactory {

  val pageId: Page

  @Composable
  fun TabIcon(modifier: Modifier, isSelected: Boolean)

  @Composable
  fun PageContent()

}