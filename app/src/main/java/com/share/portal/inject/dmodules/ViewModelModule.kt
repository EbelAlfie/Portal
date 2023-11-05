package com.share.portal.inject.dmodules

import androidx.lifecycle.ViewModel
import com.share.portal.main.MainViewModel
import dagger.Binds
import dagger.Module

@Module
interface ViewModelModule {
  @Binds
  fun provideMainViewModel(viewModel: MainViewModel): ViewModel
}