package com.share.portal.domain.dinject

import com.share.portal.data.dinject.DataComponent
import com.share.portal.domain.dinject.dmodules.UseCaseModule
import dagger.Component

@Component(
  modules = [UseCaseModule::class],
  dependencies = [DataComponent::class]
)
interface DomainComponent {
  @Component.Factory
  interface Factory {
    fun create(dataComponent: DataComponent): DomainComponent
  }
}