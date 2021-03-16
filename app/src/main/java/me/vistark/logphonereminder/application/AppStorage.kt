package me.vistark.logphonereminder.application

import me.vistark.fastdroid_lib.utils.storage.AppStorageManager
import me.vistark.logphonereminder.application.entities.AreaEntity
import me.vistark.logphonereminder.application.entities.DirectionEntity

object AppStorage {
    var areas: Array<AreaEntity>
        get() = AppStorageManager.get("__APP_AREAS__") ?: emptyArray()
        set(v) {
            AppStorageManager.update("__APP_AREAS__", v)
        }

    var directions: Array<DirectionEntity>
        get() = AppStorageManager.get("__APP_DIRECTION__") ?: emptyArray()
        set(v) {
            AppStorageManager.update("__APP_DIRECTION__", v)
        }
}