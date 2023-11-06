package com.share.portal.domain.dinject

import com.share.portal.domain.FileUseCaseImpl
import com.share.portal.domain.usecase.FileUseCase
import dagger.Binds
import dagger.Module

@Module
interface UseCaseModule {
  @Binds
  fun provideFileUseCase(useCase: FileUseCase): FileUseCaseImpl
}