package com.share.portal.view.dinject.dmodules

import androidx.lifecycle.ViewModel
import com.share.portal.view.filemanager.MainViewModel
import dagger.Binds
import dagger.Module

@Module
interface ViewModelModule {
  @Binds
  fun provideMainViewModel(viewModel: MainViewModel): ViewModel
}