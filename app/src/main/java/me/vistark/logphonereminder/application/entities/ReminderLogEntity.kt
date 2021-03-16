package me.vistark.logphonereminder.application.entities

import me.vistark.fastdroid_lib.interfaces.IEntity
import me.vistark.fastdroid_lib.utils.StringUtils
import java.util.*

class ReminderLogEntity(
    override var Id: String = StringUtils.Guid(),
    var Direction: String = "",
    var Area: String = "",
    var PhoneNumber: String = "",
    var Note: String = "",
    var RemindAt: Date = Date(),
    var IsReminded: Boolean = false,
    var RemindSound: String = ""
) : IEntity<String> {
    companion object {
        const val TABLE_NAME = "reminder_log_entity"
        const val ID = "id"
        const val DIRECTION = "direction"
        const val AREA = "area"
        const val PHONE_NUMBER = "phone_number"
        const val NOTE = "note"
        const val REMIND_AT = "remind_at"
        const val IS_REMINDED = "is_reminded"
        const val REMIND_SOUND = "remind_sound"
        const val CREATE_TABLE_QUERY = "CREATE TABLE IF NOT EXISTS \"${TABLE_NAME}\" (" +
                "\"${ID}\" TEXT NOT NULL," +
                "\"${DIRECTION}\" TEXT NOT NULL," +
                "\"${AREA}\" TEXT NOT NULL," +
                "\"${PHONE_NUMBER}\" TEXT NOT NULL," +
                "\"${NOTE}\" TEXT," +
                "\"${REMIND_AT}\" TEXT NOT NULL," +
                "\"${IS_REMINDED}\" TEXT," +
                "\"${REMIND_SOUND}\" TEXT," +
                "PRIMARY KEY(\"${ID}\")" +
                ");"
    }
}