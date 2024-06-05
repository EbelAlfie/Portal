package com.share.portal.view.utils

import android.Manifest
import android.os.Build.VERSION
import android.os.Build.VERSION_CODES

object PermissionUtils {

  fun getStoragePermission() =
    if (VERSION.SDK_INT < VERSION_CODES.TIRAMISU)
      listOf(
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.READ_EXTERNAL_STORAGE,
      )
    else listOf()

  fun getWifiSharingPermission(): List<String> {
    return if (VERSION.SDK_INT >= VERSION_CODES.TIRAMISU)
      listOf(Manifest.permission.NEARBY_WIFI_DEVICES)
    else {
      getLocationPermission()
    }
  }

  private fun getLocationPermission(): List<String> =
    listOf(
      Manifest.permission.ACCESS_FINE_LOCATION,
      Manifest.permission.ACCESS_COARSE_LOCATION
    )
}