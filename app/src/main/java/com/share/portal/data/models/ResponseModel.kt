package com.share.portal.data.models

data class ResponseModel<T: Any>(
  val data: T? = null,
  val error: Throwable? = null
)
