package me.vistark.logphonereminder.application.entities

import me.vistark.fastdroid_lib.interfaces.IEntity
import me.vistark.fastdroid_lib.utils.StringUtils.Guid
import org.w3c.dom.Entity

class DirectionEntity(
    override var Id: String = Guid(),
    var Name: String
) : IEntity<String> {
    companion object {
        const val TABLE_NAME = "direction_entity"
        const val ID = "id"
        const val NAME = "name"
        const val CREATE_TABLE_QUERY = "CREATE TABLE IF NOT EXISTS \"${TABLE_NAME}\" (" +
                "\"${ID}\" TEXT NOT NULL," +
                "\"${NAME}\" TEXT NOT NULL," +
                "PRIMARY KEY(\"${ID}\")" +
                ");"
    }
}