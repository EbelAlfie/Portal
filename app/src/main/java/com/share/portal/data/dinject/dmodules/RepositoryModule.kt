package com.share.portal.data.dinject.dmodules

import com.share.portal.data.FileRepositoryImpl
import com.share.portal.data.repository.FileRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent

@Module(
  includes = [DataSourceModule::class]
)
@InstallIn(SingletonComponent::class)
interface RepositoryModule {
  @Binds
  fun provideFileRepository(repo: FileRepositoryImpl): FileRepository
}