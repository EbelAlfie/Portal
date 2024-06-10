package com.share.portal.domain.dinject

import com.share.portal.domain.FileUseCaseImpl
import com.share.portal.domain.usecase.FileUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
interface UseCaseModule {
  @Binds
  fun provideFileUseCase(useCase: FileUseCaseImpl): FileUseCase
}