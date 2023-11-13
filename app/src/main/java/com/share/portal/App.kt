package com.share.portal

import android.app.Application
import com.share.portal.data.dinject.DaggerDataComponent
import com.share.portal.data.dinject.DataComponent
import com.share.portal.domain.dinject.DaggerDomainComponent
import com.share.portal.domain.dinject.DomainComponent
import com.share.portal.view.dinject.ApplicationComponent
import com.share.portal.view.dinject.DaggerApplicationComponent
import com.share.portal.view.dinject.DaggerFragmentComponent
import com.share.portal.view.dinject.FragmentComponent

class App: Application() {
  fun getAppComponent(): ApplicationComponent =
    DaggerApplicationComponent.factory().create(getDomainComponent())

  fun getFragmentComponent(): FragmentComponent =
    DaggerFragmentComponent.factory().create(getDomainComponent())
  private fun getDomainComponent(): DomainComponent =
    DaggerDomainComponent.factory().create(getDataComponent())

  private fun getDataComponent(): DataComponent =
    DaggerDataComponent.factory().create()
}