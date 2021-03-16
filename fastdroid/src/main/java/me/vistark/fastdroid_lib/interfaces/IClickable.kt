package me.vistark.fastdroid_lib.interfaces

interface IClickable<T> {
    var onClick: ((T) -> Unit)?
}