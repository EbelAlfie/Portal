package com.share.portal.domain.models

data class DataWrapper<T: Any>(
  val onSuccess: (T) -> Any?,
  val onFailed: (Throwable) -> Any?
)