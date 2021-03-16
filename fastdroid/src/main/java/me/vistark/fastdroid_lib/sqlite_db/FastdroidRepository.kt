package me.vistark.fastdroid_lib.sqlite_db

import android.content.ContentValues
import android.database.Cursor
import me.vistark.fastdroid_lib.interfaces.IEntity
import java.lang.Exception

open class FastdroidRepository<TDbContext : FastdroidDatabaseContext, TEntity : IEntity<TKey>, TKey>(
    val dbContext: TDbContext,
    val tabelName: String
) {
    protected val instance: TDbContext = dbContext

    open fun SetValues(entity: TEntity): ContentValues {
        throw Exception("Vui lòng khởi tạo hàm map dữ liệu");
    }

    open fun GetValues(cursor: Cursor): TEntity {
        throw Exception("Vui lòng khởi tạo hàm lấy dữ liệu");
    }

    fun Filter(filter: () -> Boolean): ArrayList<TEntity> {
        return GetAll().filter { filter.invoke() } as ArrayList<TEntity>
    }

    fun Any(whereClause: String, selectArgs: Array<String>): Boolean {
        val query = "SELECT COUNT(*) FROM $tabelName WHERE $whereClause;"
        val cursor = instance.readableDatabase.rawQuery(query, selectArgs)
        // Nếu không có bản ghi
        if (!cursor.moveToFirst()) {
            cursor.close()
            instance.readableDatabase.close()
            return false
        }

        var res = false

        // Còn có thì tiến hành duyệt
        do {
            try {
                // Gán dữ liệu vào đối tượng
                res = cursor.getInt(0) > 0
            } catch (e: Exception) {
                // Sự đời khó lường trước
                e.printStackTrace()
            }

        } while (cursor.moveToNext())

        // Đóng con trỏ
        cursor.close()

        // Đóng trình đọc
        instance.readableDatabase.close()

        // Trả về dữ liệu
        return res
    }

    fun Count(): Long {
        val query = "SELECT COUNT(*) FROM $tabelName;"
        val cursor = instance.readableDatabase.rawQuery(query, null)
        // Nếu không có bản ghi
        if (!cursor.moveToFirst()) {
            cursor.close()
            instance.readableDatabase.close()
            return 0
        }

        var res = 0L

        // Còn có thì tiến hành duyệt
        do {
            try {
                // Gán dữ liệu vào đối tượng
                res = cursor.getLong(0)
            } catch (e: Exception) {
                // Sự đời khó lường trước
                e.printStackTrace()
            }

        } while (cursor.moveToNext())

        // Đóng con trỏ
        cursor.close()

        // Đóng trình đọc
        instance.readableDatabase.close()

        // Trả về dữ liệu
        return res
    }

    fun GetAll(): ArrayList<TEntity> {
        // Khai báo biến chứa danh sách
        val entities = ArrayList<TEntity>()
        // Lấy con trỏ
        val cursor = instance.readableDatabase.query(
            true,
            tabelName,
            null,
            null,
            null,
            null,
            null,
            "id DESC",
            null
        )

        // Nếu không có bản ghi
        if (!cursor.moveToFirst()) {
            cursor.close()
            instance.readableDatabase.close()
            return entities
        }

        // Còn có thì tiến hành duyệt
        do {
            try {
                // Gán dữ liệu vào đối tượng
                val entity = GetValues(cursor)
                // Theeo vào danh sách lưu trữ
                entities.add(entity)
            } catch (e: Exception) {
                // Sự đời khó lường trước
                e.printStackTrace()
            }

        } while (cursor.moveToNext())

        // Đóng con trỏ
        cursor.close()

        // Đóng trình đọc
        instance.readableDatabase.close()

        // Trả về dữ liệu
        return entities
    }

    fun Get(id: TKey): TEntity? {
        return GetAll().firstOrNull { x -> x.Id == id }
    }

    fun Create(entity: TEntity): Long {
        var contentValues = SetValues(entity)
        // Ghi vào db
        val res = instance.writableDatabase.insert(tabelName, null, contentValues)
        // Đóng CSDL lại
        instance.writableDatabase.close()
        // Trả về kết quả
        return res
    }

    fun Update(id: TKey, entity: TEntity): Int {
        val contentValues = SetValues(entity)
        // Ghi vào db
        val res = instance.writableDatabase.update(
            tabelName, contentValues, "id=?",
            arrayOf(id.toString())
        )

        // Đóng CSDL lại
        instance.writableDatabase.close()

        // Trả về kết quả
        return res
    }

    fun Remove(id: TKey): Int {
        val res = instance.writableDatabase.delete(
            tabelName,
            "id=?",
            arrayOf(id.toString())
        )
        instance.writableDatabase.close()
        return res
    }
}