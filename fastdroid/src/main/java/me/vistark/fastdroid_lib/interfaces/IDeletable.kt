package me.vistark.fastdroid_lib.interfaces

interface IDeletable<T> {
    var onDelete: ((T) -> Unit)?
}