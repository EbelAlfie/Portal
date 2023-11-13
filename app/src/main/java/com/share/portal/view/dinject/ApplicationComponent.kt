package com.share.portal.view.dinject

import com.share.portal.domain.dinject.DomainComponent
import com.share.portal.view.dinject.dmodules.ViewModelModule
import com.share.portal.view.filemanager.MainActivity
import dagger.Component

@Component(
  modules = [ViewModelModule::class],
  dependencies = [
    DomainComponent::class,
  ]
)
interface ApplicationComponent {
  @Component.Factory
  interface Factory {
    fun create(domainComponent: DomainComponent): ApplicationComponent
  }
  fun inject(page: MainActivity)
}