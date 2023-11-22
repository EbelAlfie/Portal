package com.share.portal.view.general

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.share.portal.App
import com.share.portal.view.dinject.FragmentComponent
import com.share.portal.view.utils.ToastBuilder

abstract class ProgenitorFragment<V: ViewBinding>: Fragment() {
  lateinit var binding: V

  private val toast: ToastBuilder by lazy {
    ToastBuilder(requireContext())
  }

  val fragmentComponent: FragmentComponent by lazy {
    (requireActivity().application as App).getFragmentComponent()
  }

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    binding = initBinding(layoutInflater)
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    initFragment()
  }

  fun showToast(msg: String?) {
   toast.show(msg)
  }

  abstract fun initFragment()

  abstract fun initBinding(layoutInflater: LayoutInflater): V

  abstract fun onBackPressed()
}