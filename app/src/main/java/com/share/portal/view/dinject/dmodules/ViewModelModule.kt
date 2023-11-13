package com.share.portal.view.dinject.dmodules

import androidx.lifecycle.ViewModel
import com.share.portal.view.filemanager.MainViewModel
import com.share.portal.view.filemanager.fileexplorer.FileViewModel
import com.share.portal.view.filemanager.wifisharing.WifiSharingViewmodel
import dagger.Binds
import dagger.Module

@Module
interface ViewModelModule {
  @Binds
  fun provideMainViewModel(viewModel: MainViewModel): ViewModel
  @Binds
  fun provideFileViewModel(viewModel: FileViewModel): ViewModel
  @Binds
  fun provideWifiSharingViewModel(viewModel: WifiSharingViewmodel): ViewModel
}