package com.share.portal.view.utils

import android.content.Context
import android.content.DialogInterface
import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.fragment.app.FragmentManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.share.portal.R
import com.share.portal.databinding.BottomsheetWarningBinding


class BottomSheetPopUp(context: Context): BottomSheetDialogFragment() {
  private var title: String = context.getString(R.string.warning_general_title)
  private var content: CharSequence = context.getString(R.string.warning_general_content)
  private var img: Int = R.drawable.general_error
  private var onDismiss: (() -> Unit)? = null

  private lateinit var binding: BottomsheetWarningBinding

  fun addOnDismissListener(callback: () -> Unit) {
    onDismiss = callback
  }

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    binding = BottomsheetWarningBinding.inflate(inflater, container, false)
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    loadArguments()
    setupView()
  }

  private fun loadArguments() {
    arguments?.let {
      img = it.getInt(DIALOG_IMG, 0)
      title = it.getString(DIALOG_TITLE, "")
      content = it.getCharSequence(DIALOG_DESC, "")
    }
  }

  private fun setupView() {
    binding.run {
      with(root.parent as View) {
        layoutParams.apply {
          height = Resources.getSystem().displayMetrics.heightPixels
          width = ViewGroup.LayoutParams.MATCH_PARENT
        }
        configureBehavior(this)
      }

      ivLogo.setImageResource(img)
      tvTitle.text = title
      tvContent.text = content
    }
  }

  private fun configureBehavior(view: View) {
    BottomSheetBehavior.from(view).run {
      state = BottomSheetBehavior.STATE_EXPANDED
    }
  }

  override fun onDismiss(dialog: DialogInterface) {
    super.onDismiss(dialog)
    onDismiss?.invoke()
  }

  companion object {
    private const val DIALOG_TAG = "POPUP"
    private const val DIALOG_IMG = "DIALOG_IMG"
    private const val DIALOG_TITLE = "DIALOG_TITLE"
    private const val DIALOG_DESC = "DIALOG_DESC"

    fun newDialog(
      fragmentManager: FragmentManager,
      context: Context,
      @DrawableRes image: Int = R.drawable.general_error,
      title: String?,
      content: CharSequence?,
      onDismiss: (() -> Unit)? = null
    ) {

      val bundle = Bundle().apply {
        putInt(DIALOG_IMG, image)
        putString(DIALOG_TITLE, title)
        putCharSequence(DIALOG_DESC, content)
      }

      BottomSheetPopUp(context).also {
        it.enterTransition = R.anim.slide_up
        it.arguments = bundle
        it.addOnDismissListener { onDismiss?.invoke() }

        fragmentManager.findFragmentByTag(DIALOG_TAG)?.let {fragment ->
          (fragment as BottomSheetDialogFragment).dismiss()
        }

        it.show(fragmentManager, DIALOG_TAG)
      }
    }

  }
}