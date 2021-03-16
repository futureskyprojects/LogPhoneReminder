package me.vistark.logphonereminder.application.repositories

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import com.google.gson.Gson
import me.vistark.fastdroid_lib.sqlite_db.FastdroidRepository
import me.vistark.fastdroid_lib.utils.ConvertUtils.toInt
import me.vistark.fastdroid_lib.utils.DateTimeUtils.Companion.format
import me.vistark.fastdroid_lib.utils.DateTimeUtils.Companion.toDate
import me.vistark.logphonereminder.application.db.DbContext
import me.vistark.logphonereminder.application.entities.AreaEntity
import me.vistark.logphonereminder.application.entities.ReminderLogEntity
import java.lang.Exception
import java.util.*

class ReminderLogRepository(context: Context) :
    FastdroidRepository<DbContext, ReminderLogEntity, String>(
        DbContext(context),
        ReminderLogEntity.TABLE_NAME
    ) {
    override fun SetValues(entity: ReminderLogEntity): ContentValues {
        val contentValues = ContentValues()
        contentValues.put(ReminderLogEntity.ID, entity.Id)
        contentValues.put(ReminderLogEntity.DIRECTION, entity.Direction)
        contentValues.put(ReminderLogEntity.AREA, entity.Area)
        contentValues.put(ReminderLogEntity.PHONE_NUMBER, entity.PhoneNumber)
        contentValues.put(ReminderLogEntity.NOTE, entity.Note)
        contentValues.put(
            ReminderLogEntity.REMIND_AT,
            entity.RemindAt.format("HH:mm:ss dd/MM/yyyy")
        )
        contentValues.put(ReminderLogEntity.IS_REMINDED, entity.IsReminded.toString())
        contentValues.put(ReminderLogEntity.REMIND_SOUND, entity.RemindSound)
        return contentValues
    }

    override fun GetValues(cursor: Cursor): ReminderLogEntity {
        val entity = ReminderLogEntity()
        entity.Id = cursor.getString(0)
        entity.Direction = cursor.getString(1)
        entity.Area = cursor.getString(2)
        entity.PhoneNumber = cursor.getString(3)
        entity.Note = cursor.getString(4)
        entity.RemindAt = cursor.getString(5).toDate("HH:mm:ss dd/MM/yyyy") ?: Date()
        entity.IsReminded = cursor.getString(6).toBoolean()
        entity.RemindSound = cursor.getString(7)
        return entity
    }
}