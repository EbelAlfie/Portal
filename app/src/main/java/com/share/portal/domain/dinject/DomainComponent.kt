package com.share.portal.domain.dinject

import com.share.portal.data.dinject.DataComponent
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
  modules = [
    UseCaseModule::class
  ],
  dependencies = [
    DataComponent::class
  ]
)
interface DomainComponent {}