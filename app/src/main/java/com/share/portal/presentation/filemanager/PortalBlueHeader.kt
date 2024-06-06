package com.share.portal.presentation.filemanager

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.share.portal.R
import com.share.portal.R.string
import com.share.portal.presentation.ui.theme.BlueDefault

@Preview
@Composable
fun PortalBlueHeader(
  modifier: Modifier = Modifier,
  trailingContent: @Composable RowScope.() -> Unit = {}
) {
  Row(
    modifier = modifier
      .background(BlueDefault)
      .padding(8.dp)
      .fillMaxWidth(),
    verticalAlignment = Alignment.CenterVertically
  ) {
    AppLabel(Modifier.weight(1f))
    trailingContent()
  }
}

@Composable
fun AppLabel(modifier: Modifier = Modifier) {
  Row(modifier) {
    Image(
      modifier = Modifier,
      painter = painterResource(id = R.drawable.ic_folder),
      contentDescription = null
    )
    Text(
      text = stringResource(id = string.portal_label)
    )
  }
}