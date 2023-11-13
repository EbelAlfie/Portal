package com.share.portal.view.filemanager.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.share.portal.view.filemanager.fileexplorer.FileExploreFragment
import com.share.portal.view.filemanager.wifisharing.FileSharingFragment

class ViewPagerAdapter(
  fragmentManager: FragmentManager,
  lifecycle: Lifecycle
): FragmentStateAdapter(fragmentManager, lifecycle) {

  override fun getItemCount(): Int = PageEnum.values().size

  override fun createFragment(position: Int): Fragment =
    when(position) {
      PageEnum.FILE_EXPLORER.ordinal -> FileExploreFragment()
      PageEnum.FILE_SHARING.ordinal -> FileSharingFragment()
      else -> FileExploreFragment()
    }

}