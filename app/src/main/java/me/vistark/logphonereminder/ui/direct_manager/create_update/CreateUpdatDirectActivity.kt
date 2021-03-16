package me.vistark.logphonereminder.ui.direct_manager.create_update

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.common_create_update_input_text.*
import me.vistark.fastdroid_lib.ui.activities.FastdroidActivity
import me.vistark.fastdroid_lib.utils.FastToasty.ToastError
import me.vistark.fastdroid_lib.utils.FastToasty.ToastSuccess
import me.vistark.fastdroid_lib.utils.StringUtils
import me.vistark.fastdroid_lib.utils.ViewExtension.onTap
import me.vistark.fastdroid_lib.utils.ViewExtension.onTextChanged
import me.vistark.logphonereminder.R
import me.vistark.logphonereminder.application.AppStorage
import me.vistark.logphonereminder.application.entities.AreaEntity
import me.vistark.logphonereminder.application.entities.DirectionEntity

class CreateUpdatDirectActivity :
    FastdroidActivity(R.layout.activity_create_updat_direct, isHaveActionBar = true) {
    var entity: DirectionEntity = DirectionEntity("", "")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initFirstParams()
        initPreText()
        initBlockProcess()
        initConfirmButtonEvent()

        initActionBar()
    }

    private fun initActionBar() {
        if (entity.Id.isEmpty()) {
            supportActionBar?.title = "Thêm hướng mới"
        } else {
            supportActionBar?.title = "Sửa hướng"
        }

        // Hiển thị nút trở vềF
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    // Khi nhấn nút trở về
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    private fun initConfirmButtonEvent() {
        val isEdit = entity.Id.isNotEmpty()
        ceConfirmButton.onTap {
            if (AppStorage.directions.any { x ->
                    x.Name.equals(
                        entity.Name,
                        true
                    ) && !x.Id.equals(entity.Id, true)
                }) {
                ToastError("Tên hướng đã tồn tại trước đó")
                return@onTap
            }
            if (isEdit) {
                updateData()
            } else {
                createData()
            }
            setResult(RESULT_OK)
            finish()
        }
    }

    private fun createData() {
        entity.Id = StringUtils.Guid()
        AppStorage.directions = AppStorage.directions.plus(entity)
        ToastSuccess("Thêm [${entity.Name}] vào danh sách hướng thành công")
    }

    private fun updateData() {
        AppStorage.directions =
            AppStorage.directions.filter { x -> !x.Id.equals(entity.Id) }.plus(entity)
                .toTypedArray()
        ToastSuccess("Cập nhật tên hướng [${entity.Name}] thành công")

    }

    private fun initBlockProcess() {
        ceEdittext.onTextChanged {
            entity.Name = it
            ceConfirmButton.isEnabled = it.isNotEmpty()
        }
    }

    private fun initFirstParams() {
        val gottedId = intent.getStringExtra("ID")
        if (!gottedId.isNullOrEmpty()) {
            entity =
                AppStorage.directions.firstOrNull { x -> x.Id.equals(gottedId, true) }
                    ?: DirectionEntity(
                        "",
                        ""
                    )
        }
    }

    private fun initPreText() {
        ceLabel.text = "Tên hướng"
        ceEdittext.hint = "Tên hướng"
        if (entity.Name.isNotEmpty())
            ceEdittext.setText(entity.Name)
        if (entity.Id.isEmpty()) {
            ceConfirmButton.text = "Thêm mới"
        } else {
            ceConfirmButton.text = "Cập nhật"
        }
    }
}