package com.share.portal.inject

import com.share.portal.inject.dmodules.ViewModelModule
import com.share.portal.main.MainActivity
import dagger.Component

@Component(
  modules = [ViewModelModule::class]
)
interface ApplicationComponent {

  fun inject(page: MainActivity)
}