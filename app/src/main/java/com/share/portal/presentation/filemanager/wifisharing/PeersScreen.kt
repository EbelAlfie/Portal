package com.share.portal.presentation.filemanager.wifisharing

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.share.portal.R.drawable
import com.share.portal.presentation.filemanager.Page
import com.share.portal.presentation.filemanager.PageFactory
import com.share.portal.presentation.ui.theme.GreyAlpha

class PeerFinderPage : PageFactory {

  override val pageId: Page = Page.FileSharing

  @Composable
  override fun TabIcon(isSelected: Boolean) {
    val modifier = if (isSelected) Modifier
      .background(GreyAlpha, CircleShape)
      .padding(5.dp)
    else Modifier
    Icon(
      modifier = modifier,
      painter = painterResource(id = drawable.ic_file_sharing),
      contentDescription = null
    )
  }

  @Composable
  override fun PagerContent() {
    PeersScreen()
  }
}


@Composable
fun PeersScreen() {
}