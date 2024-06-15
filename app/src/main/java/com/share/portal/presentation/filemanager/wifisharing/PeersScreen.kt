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
import com.share.portal.presentation.ui.theme.Grey
import com.share.portal.presentation.ui.theme.GreyAlpha

class PeerFinderPage : PageFactory {

  override val pageId: Page = Page.FileSharing

  @Composable
  override fun TabIcon(modifier: Modifier, isSelected: Boolean) {
    val textModifier = if (isSelected) Modifier
      .background(GreyAlpha, CircleShape)
      .padding(5.dp)
    else modifier
    Icon(
      modifier = textModifier,
      painter = painterResource(id = drawable.ic_file_sharing),
      tint = Grey,
      contentDescription = null
    )
  }

  @Composable
  override fun PageContent() {
    PeersScreen()
  }
}


@Composable
fun PeersScreen() {
}