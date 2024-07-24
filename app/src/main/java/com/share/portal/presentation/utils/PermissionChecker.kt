package com.share.portal.presentation.utils

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts.RequestMultiplePermissions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf

data class PermissionState(
  val permissions: List<String> = mutableStateListOf(),
  val onPermissionGranted: () -> Unit = {},
  val onPermissionDenied: () -> Unit = {}
)

@Composable
fun PermissionChecker(
  permissionState: PermissionState
) {
  if (permissionState.permissions.isEmpty()) return
  val permissionLauncher = rememberLauncherForActivityResult(
    contract = RequestMultiplePermissions()
  ) {
    val revokedPermission = it.map { permissions -> !permissions.value }
    if (revokedPermission.isEmpty()) permissionState.onPermissionGranted.invoke()
    else permissionState.onPermissionDenied.invoke()
  }

  LaunchedEffect(permissionState.permissions) {
    permissionLauncher.launch(permissionState.permissions.toTypedArray())
  }
}