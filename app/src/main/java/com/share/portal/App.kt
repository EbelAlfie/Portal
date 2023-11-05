package com.share.portal

import android.app.Application
import com.share.portal.inject.ApplicationComponent
import com.share.portal.inject.DaggerApplicationComponent

class App: Application() {
  fun getAppComponent(): ApplicationComponent =
    DaggerApplicationComponent.create()
}