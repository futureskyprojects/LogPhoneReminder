package me.vistark.logphonereminder.application.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import me.vistark.fastdroid_lib.sqlite_db.FastdroidDatabaseContext
import me.vistark.logphonereminder.application.entities.AreaEntity
import me.vistark.logphonereminder.application.entities.DirectionEntity
import me.vistark.logphonereminder.application.entities.ReminderLogEntity

class DbContext(context: Context) : FastdroidDatabaseContext(context, "LogPhoneReminder.db", 1) {
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(AreaEntity.CREATE_TABLE_QUERY)
        db?.execSQL(DirectionEntity.CREATE_TABLE_QUERY)
        db?.execSQL(ReminderLogEntity.CREATE_TABLE_QUERY)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        // Xóa các bảng cũ
        db?.execSQL("DROP TABLE IF EXISTS ${AreaEntity.TABLE_NAME}")
        db?.execSQL("DROP TABLE IF EXISTS ${DirectionEntity.TABLE_NAME}")
        db?.execSQL("DROP TABLE IF EXISTS ${ReminderLogEntity.TABLE_NAME}")

        // Tọa lại bảng mới
        onCreate(db)
    }
}