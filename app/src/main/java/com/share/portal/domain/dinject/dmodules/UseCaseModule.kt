package com.share.portal.domain.dinject.dmodules

import com.share.portal.domain.FileUseCaseImpl
import com.share.portal.domain.usecase.FileUseCase
import dagger.Binds
import dagger.Module

@Module
interface UseCaseModule {
  @Binds
  fun provideFileUseCase(useCase: FileUseCaseImpl): FileUseCase
}