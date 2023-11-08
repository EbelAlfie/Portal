package com.share.portal.view.dinject.dmodules

import androidx.lifecycle.ViewModel
import com.share.portal.view.filemanager.MainViewModel
import com.share.portal.view.wifisharing.WifiSharingViewmodel
import dagger.Binds
import dagger.Module

@Module
interface ViewModelModule {
  @Binds
  fun provideMainViewModel(viewModel: MainViewModel): ViewModel
  @Binds
  fun provideWifiSharingViewModel(viewModel: WifiSharingViewmodel): ViewModel
}