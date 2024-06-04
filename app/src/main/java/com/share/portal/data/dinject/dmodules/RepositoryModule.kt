package com.share.portal.data.dinject.dmodules

import com.share.portal.data.FileRepositoryImpl
import com.share.portal.data.repository.FileRepository
import dagger.Binds
import dagger.Module

@Module(
  includes = [DataSourceModule::class]
)
interface RepositoryModule {
  @Binds
  fun provideFileRepository(repo: FileRepositoryImpl): FileRepository
}