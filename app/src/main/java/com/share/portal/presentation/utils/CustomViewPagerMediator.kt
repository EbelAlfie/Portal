package com.share.portal.presentation.utils

import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2

@Deprecated("Research lebih lanjut mengenai listener")
class CustomViewPagerMediator<V: ViewGroup, T: Enum<T>> (
  private val viewPager2: ViewPager2,
  private val pseudoTab: V,
  private val identifier: Class<T>,
  private val setView: (Int) -> View
  ) {

  private var currentPosition: Int = 0

  private val pageChangeListener = object: ViewPager2.OnPageChangeCallback() {
    override fun onPageSelected(position: Int) {
      super.onPageSelected(position)
      pseudoTab.getChildAt(position).isSelected = true
    }
    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
      super.onPageScrolled(position, positionOffset, positionOffsetPixels)
      pseudoTab.getChildAt(currentPosition).isSelected = (position == currentPosition)
      currentPosition = position
    }
  }

  init {
    initAllTabs()
  }

  private fun mediateViewPager() {
    viewPager2.apply {
      registerOnPageChangeCallback(pageChangeListener)
    }
  }

  private fun initAllTabs() {
    identifier.enumConstants?.forEach {enum ->
      val tabItem = setView.invoke(enum.ordinal)
      tabItem.setOnClickListener {
        viewPager2.currentItem = enum.ordinal
      }
      pseudoTab.addView(tabItem, enum.ordinal)
    }
    mediateViewPager()
  }

}