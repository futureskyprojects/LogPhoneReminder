package me.vistark.fastdroid_lib.interfaces

interface ILongClickable<T> {
    var onLongClick: ((T) -> Unit)?
}