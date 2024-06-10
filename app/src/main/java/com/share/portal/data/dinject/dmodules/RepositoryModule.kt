package com.share.portal.data.dinject.dmodules

import com.share.portal.data.FileRepositoryImpl
import com.share.portal.data.repository.FileRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module(
  includes = [DataSourceModule::class]
)
@InstallIn(ViewModelComponent::class)
interface RepositoryModule {
  @Binds
  fun provideFileRepository(repo: FileRepositoryImpl): FileRepository
}