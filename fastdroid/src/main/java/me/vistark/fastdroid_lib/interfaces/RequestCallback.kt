package me.vistark.fastdroid_lib.interfaces

interface RequestCallback<T> {
    fun onResponse(response: T)
    fun onFailure(throwable: Throwable? = null)
}