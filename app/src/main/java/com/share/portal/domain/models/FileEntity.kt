package com.share.portal.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class FileEntity(
  val fileName: String,
  val extension: String
): Parcelable
