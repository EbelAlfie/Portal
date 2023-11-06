package com.share.portal.data.dinject

import com.share.portal.data.dinject.dmodules.DataSourceModule
import com.share.portal.data.dinject.dmodules.RepositoryModule
import dagger.Component

@Component(
  modules = [
    DataSourceModule::class,
    RepositoryModule::class
  ]
)
interface DataComponent {

  @Component.Factory
  interface Factory {
    fun create (): DataComponent
  }
}