package com.share.portal

import android.app.Application
import com.share.portal.data.dinject.DaggerDataComponent
import com.share.portal.data.dinject.DataComponent
import com.share.portal.domain.dinject.DaggerDomainComponent
import com.share.portal.domain.dinject.DomainComponent
import com.share.portal.inject.ApplicationComponent
import com.share.portal.inject.DaggerApplicationComponent

class App: Application() {
  fun getAppComponent(): ApplicationComponent =
    DaggerApplicationComponent.factory().create(getDomainComponent())

  private fun getDomainComponent(): DomainComponent =
    DaggerDomainComponent.factory().create(getDataComponent())

  private fun getDataComponent(): DataComponent =
    DaggerDataComponent.factory().create()
}