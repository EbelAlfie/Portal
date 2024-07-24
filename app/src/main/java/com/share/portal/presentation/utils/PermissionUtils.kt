package com.share.portal.presentation.utils

import android.Manifest
import android.os.Build.VERSION
import android.os.Build.VERSION_CODES

object PermissionUtils {
  fun getWifiSharingPermission(): List<String> {
    return if (VERSION.SDK_INT >= VERSION_CODES.TIRAMISU) {
      listOf(Manifest.permission.NEARBY_WIFI_DEVICES) + getLocationPermission()
    } else {
      getLocationPermission()
    }

  }

  private fun getLocationPermission(): List<String> =
    listOf(
      Manifest.permission.ACCESS_FINE_LOCATION,
      Manifest.permission.ACCESS_COARSE_LOCATION
    )
}