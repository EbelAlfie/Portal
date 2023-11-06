package com.share.portal.data.dinject

import com.share.portal.data.datasource.OfflineDataSource
import com.share.portal.data.repository.FileRepository
import dagger.Binds
import dagger.Module

@Module(
  includes = [OfflineDataSource::class]
)
interface RepositoryModule {
  @Binds
  fun provideFileRepository(repo: FileRepository): FileRepository
}