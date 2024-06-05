package com.share.portal.presentation.filemanager

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.share.portal.R
import com.share.portal.R.string

@Preview
@Composable
fun PortalBlueHeader(
  modifier: Modifier = Modifier,
  trailingContent: @Composable RowScope.() -> Unit = {}
) {
  Row(
    modifier = modifier
      .fillMaxWidth()
  ) {
    AppLabel()
    trailingContent()
  }
}

@Composable
fun AppLabel(modifier: Modifier = Modifier) {
  Row(modifier) {
    Image(
      modifier = Modifier,
      painter = painterResource(id = R.mipmap.ic_launcher),
      contentDescription = null
    )
    Text(
      text = stringResource(id = string.app_name)
    )
  }
}