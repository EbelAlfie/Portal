package com.share.portal.presentation.filemanager.wifisharing

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.stringResource
import com.share.portal.R
import com.share.portal.presentation.ui.theme.DefaultText

@Composable
fun EmptyPeerScreen() {
  Column(
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    DefaultText(text = stringResource(id = R.string.empty_peer_message))
  }
}