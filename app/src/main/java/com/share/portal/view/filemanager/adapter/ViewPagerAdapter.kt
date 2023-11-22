package com.share.portal.view.filemanager.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.share.portal.view.filemanager.fileexplorer.FileExploreFragment
import com.share.portal.view.filemanager.wifisharing.FileSharingFragment
import com.share.portal.view.general.ProgenitorFragment

class ViewPagerAdapter(
  fragmentManager: FragmentManager,
  lifecycle: Lifecycle
): FragmentStateAdapter(fragmentManager, lifecycle) {

  private val fragmentList = arrayListOf(
    FileExploreFragment(),
    FileSharingFragment()
  )

  override fun getItemCount(): Int = fragmentList.size

  override fun createFragment(position: Int): Fragment = fragmentList[position]

  fun getFragmentAt(position: Int) = fragmentList[position]

}