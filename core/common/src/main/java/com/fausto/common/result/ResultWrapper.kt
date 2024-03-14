package com.fausto.common.result

sealed interface ResultWrapper<out T> {
    data class Success<T>(val data: T) : ResultWrapper<T>
    data class Error(val exception: Throwable? = null) : ResultWrapper<Nothing>
}

suspend fun <T> getResult(call: suspend () -> T): ResultWrapper<T> {
    return try {
        val result = call()
        ResultWrapper.Success(result)
    } catch (e: Throwable) {
        ResultWrapper.Error(e)
    }
}