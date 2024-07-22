package com.share.portal.presentation.utils

import android.content.res.Resources
import com.share.portal.domain.models.FileTreeEntity
import com.share.portal.presentation.filemanager.fileexplorer.model.FileData


fun calculate() {
  val phoneWindow = Resources.getSystem().displayMetrics
}

fun List<FileTreeEntity>.getChildFiles(): List<FileData> {
  return this.lastOrNull()?.child ?: listOf()
}