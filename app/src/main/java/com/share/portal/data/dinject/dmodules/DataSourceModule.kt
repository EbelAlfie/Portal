package com.share.portal.data.dinject.dmodules

import com.share.portal.data.datasource.OfflineDataSource
import com.share.portal.data.datasource.OfflineDataSourceImpl
import com.share.portal.data.datasource.OnlineDataSource
import com.share.portal.data.datasource.OnlineDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module(includes = [WSModule::class])
@InstallIn(ViewModelComponent::class)
interface DataSourceModule {
  @Binds
  fun provideOfflineModule(dataSource: OfflineDataSourceImpl): OfflineDataSource

  @Binds
  fun provideOnlineModule(dataSource: OnlineDataSourceImpl): OnlineDataSource
}