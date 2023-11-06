package com.share.portal.data.dinject

import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
  modules = [
    DataSourceModule::class,
    RepositoryModule::class
  ]
)
interface DataComponent {}