package com.share.portal.view.filemanager

import android.content.IntentFilter

interface WifiPerantara {
  val intentFilter: IntentFilter
  fun registerWifi()
  fun unregisterWifi()
}