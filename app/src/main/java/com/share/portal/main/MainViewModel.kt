package com.share.portal.main

import androidx.lifecycle.ViewModel
import javax.inject.Inject

class MainViewModel @Inject constructor(): ViewModel() {
  fun getGreet() : String = "Hey"
}