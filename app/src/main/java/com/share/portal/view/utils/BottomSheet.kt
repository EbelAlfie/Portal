package com.share.portal.view.utils

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.fragment.app.FragmentManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.share.portal.databinding.BottomsheetWarningBinding

class BottomSheetPopUp: BottomSheetDialogFragment() {

  private var title: String = ""
  private var content: CharSequence = ""
  private var img: Int = 0

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    return BottomsheetWarningBinding.inflate(inflater, container, false).root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    loadArguments()
    setupView(view)
  }

  private fun loadArguments() {
    arguments?.let {
      img = it.getInt(DIALOG_IMG, 0)
      title = it.getString(DIALOG_TITLE, "")
      content = it.getCharSequence(DIALOG_DESC, "")
    }
  }

  private fun setupView(view: View) {
    BottomsheetWarningBinding.bind(view).run {
      ivLogo.setImageResource(img)
      tvTitle.text = title
      tvContent.text = content
    }
  }

  override fun onDismiss(dialog: DialogInterface) {
    super.onDismiss(dialog)
  }

  companion object {
    private const val DIALOG_TAG = "POPUP"
    private const val DIALOG_IMG = "DIALOG_IMG"
    private const val DIALOG_TITLE = "DIALOG_TITLE"
    private const val DIALOG_DESC = "DIALOG_DESC"

    fun newDialog(
      fragmentManager: FragmentManager,
      @DrawableRes image: Int,
      title: String,
      content: CharSequence,
    ) {
      val dialog = BottomSheetPopUp()
      val bundle = Bundle().apply {
        putInt(DIALOG_IMG, image)
        putString(DIALOG_TITLE, title)
        putCharSequence(DIALOG_DESC, content)
      }
      dialog.arguments = bundle

      fragmentManager.findFragmentByTag(DIALOG_TAG)?.let {
        (it as BottomSheetDialogFragment).dismiss()
      }

      dialog.show(fragmentManager, DIALOG_TAG)
    }
  }
}