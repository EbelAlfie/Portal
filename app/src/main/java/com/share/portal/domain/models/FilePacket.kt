package com.share.portal.domain.models

import java.io.File

data class FilePacket(
  var target: String,
  var file: File
)