package com.share.portal.view.dinject

import com.share.portal.domain.dinject.DomainComponent
import com.share.portal.view.dinject.dmodules.ViewModelModule
import com.share.portal.view.filemanager.fileexplorer.FileExploreFragment
import com.share.portal.view.filemanager.wifisharing.FileSharingFragment
import dagger.Component

@Component(
  modules = [ViewModelModule::class],
  dependencies = [DomainComponent::class]
)
interface FragmentComponent {
  @Component.Factory
  interface Factory {
    fun create(domainComponent: DomainComponent): FragmentComponent
  }

  fun inject(fragment: FileExploreFragment)

  fun inject(fragment: FileSharingFragment)
}