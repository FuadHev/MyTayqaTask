package com.fuadhev.mytayqatask.common.utils

sealed class Resource<out T> {
    data class Success<out T : Any>(val data: T?) : Resource<T>()
    data class Error(val exception: String) : Resource<Nothing>()
    data object Loading : Resource<Nothing>()
}