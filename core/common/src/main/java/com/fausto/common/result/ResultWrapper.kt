package com.fausto.common.result

sealed interface ResultWrapper<out T> {
    data class Success<T>(val data: T) : ResultWrapper<T>
    data class Error(val exception: Throwable? = null) : ResultWrapper<Nothing>
}