package com.share.portal.inject

import com.share.portal.domain.dinject.DomainComponent
import com.share.portal.inject.dmodules.ViewModelModule
import com.share.portal.main.MainActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
  modules = [ViewModelModule::class],
  dependencies = [
    DomainComponent::class
  ]
)
interface ApplicationComponent {
  @Component.Factory
  interface Factory {
    fun create(domainComponent: DomainComponent): ApplicationComponent
  }
  fun inject(page: MainActivity)
}