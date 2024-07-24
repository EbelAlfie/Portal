package com.share.portal.presentation.filemanager.wifisharing

import android.net.wifi.p2p.WifiP2pDevice
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.share.portal.R.drawable
import com.share.portal.presentation.filemanager.Page
import com.share.portal.presentation.filemanager.wifisharing.PeerUiState.Loaded
import com.share.portal.presentation.ui.theme.DefaultText
import com.share.portal.presentation.ui.theme.Grey
import com.share.portal.presentation.ui.theme.GreyAlpha
import com.share.portal.presentation.utils.PageFactory
import com.share.portal.presentation.utils.PermissionUtils

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
}


@Composable
fun PeersScreen(
  viewModel: WifiSharingViewmodel,
  discoverPeers: () -> Unit
) {
  PermissionChecker(
    permissions = PermissionUtils.getWifiSharingPermission(),
    onPermissionGranted = discoverPeers,
    onPermissionDenied = {}
  )
  discoverPeers.invoke()

  val uiState by viewModel.uiState.collectAsState()
  when (uiState) {
    is PeerUiState.Loading -> {}
    is PeerUiState.Loaded ->
      PeerList((uiState as Loaded).peers)

    is PeerUiState.EmptyPeer ->
      EmptyPeerScreen()
  }
}

@Composable
fun PeerList(
  peers: List<WifiP2pDevice>,
  onRequestPeerConnection: (WifiP2pDevice) -> Unit = {}
) {
  LazyColumn {
    items(peers) {
      PeerItem(it, onRequestPeerConnection)
    }
  }
}

@Composable
fun PeerItem(
  peer: WifiP2pDevice,
  onClick: (WifiP2pDevice) -> Unit = {}
) {
  Row(
    modifier = Modifier
      .clickable { onClick.invoke(peer) }
      .fillMaxWidth()
      .padding(20.dp),
    verticalAlignment = Alignment.CenterVertically
  ) {
    DefaultText(text = peer.deviceName)
    DefaultText(text = peer.deviceAddress)
    DefaultText(text = peer.status.toString())
  }
}

@Composable
fun PermissionChecker(
  permissions: List<String> = listOf(),
  onPermissionGranted: () -> Unit = {},
  onPermissionDenied: () -> Unit = {}
) {
  val permissionLauncher = rememberLauncherForActivityResult(
    contract = ActivityResultContracts.RequestMultiplePermissions()
  ) {
    val revokedPermission = it.map { permissions -> !permissions.value }
    if (revokedPermission.isEmpty()) onPermissionGranted.invoke()
    else onPermissionDenied.invoke()
  }

  LaunchedEffect(permissions) {
    permissionLauncher.launch(permissions.toTypedArray())
  }
}