package com.share.portal.data.dinject

import com.share.portal.data.datasource.OfflineDataSource
import dagger.Binds
import dagger.Module

@Module
interface DataSourceModule {
  @Binds
  fun provideOfflineModule(dataSource: OfflineDataSource): OfflineDataSource
}