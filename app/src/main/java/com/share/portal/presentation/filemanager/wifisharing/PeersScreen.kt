package com.share.portal.presentation.filemanager.wifisharing

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.share.portal.R.drawable
import com.share.portal.presentation.filemanager.Page
import com.share.portal.presentation.ui.theme.Grey
import com.share.portal.presentation.ui.theme.GreyAlpha
import com.share.portal.presentation.utils.PageFactory

class PeerFinderPage(
  private val viewModel: WifiSharingViewmodel
) : PageFactory {

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
    PeersScreen(viewModel)
  }
}


@Composable
fun PeersScreen(viewModel: WifiSharingViewmodel) {
  val uiState by viewModel.uiState.collectAsState()
  when (uiState) {
    is PeerUiState.Loading -> {}
    is PeerUiState.Loaded -> {}
    is PeerUiState.EmptyPeer ->
      EmptyPeerScreen()
  }
}