package me.vistark.logphonereminder.application.constants

import android.Manifest
import me.vistark.fastdroid_lib.core.models.RequirePermission

object AppRequires {
    val permissions: ArrayList<RequirePermission>
        get() {
            val temp: ArrayList<RequirePermission> = ArrayList()
            temp.add(
                RequirePermission(
                    Manifest.permission.INTERNET,
                    "Truy cập mạng",
                    "Cho phép ứng dụng truy cập vào mạng để đồng bộ thông tin"
                )
            )
            temp.add(
                RequirePermission(
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    "Đọc bộ nhớ",
                    "Cho phép ứng dụng có khả năng truy cập vào các dữ liệu đã được lưu"
                )
            )
            temp.add(
                RequirePermission(
                    Manifest.permission.READ_CALL_LOG,
                    "Đọc lịch sử cuộc gọi",
                    "Cho phép ứng dụng đọc lịch sử các cuộc gọi đến và đi trên thiết bị"
                )
            )
            temp.add(
                RequirePermission(
                    Manifest.permission.READ_CONTACTS,
                    "Đọc danh bạ",
                    "Cho phép ứng dụng đọc danh sách danh bạ của thiết bị"
                )
            )
            temp.add(
                RequirePermission(
                    Manifest.permission.WAKE_LOCK,
                    "Đánh thức màn hình khóa",
                    "Cho phép ứng dụng đánh thức thiết bị ngay cả khi màn hình đang tắt"
                )
            )

            return temp
        }

    val directions = arrayListOf(
        "Đông",
        "Tây",
        "Nam",
        "Bắc",
        "Đông - Nam",
        "Đông - Bắc",
        "Tây - Nam",
        "Tây - Bắc"
    )
    val areas = arrayListOf(
        "Quận Ba Đình - Hà Nội",
        "Quận Bắc Từ Liêm - Hà Nội",
        "Quận Cầu Giấy - Hà Nội",
        "Quận Đống Đa - Hà Nội",
        "Quận Hà Đông - Hà Nội",
        "Quận Hai Bà Trưng - Hà Nội",
        "Quận Hoàn Kiếm - Hà Nội",
        "Quận Hoàng Mai - Hà Nội",
        "Quận Long Biên - Hà Nội",
        "Quận Nam Từ Liêm - Hà Nội",
        "Quận Tây Hồ - Hà Nội",
        "Quận Thanh Xuân - Hà Nội",
        "Thị xã Sơn Tây - Hà Nội",
        "Huyện Ba Vì - Hà Nội",
        "Huyện Chương Mỹ - Hà Nội",
        "Huyện Đan Phượng - Hà Nội",
        "Huyện Đông Anh - Hà Nội",
        "Huyện Gia Lâm - Hà Nội",
        "Huyện Hoài Đức - Hà Nội",
        "Huyện Mê Linh - Hà Nội",
        "Huyện Mỹ Đức - Hà Nội",
        "Huyện Phú Xuyên - Hà Nội",
        "Huyện Phúc Thọ - Hà Nội",
        "Huyện Quốc Oai - Hà Nội",
        "Huyện Sóc Sơn - Hà Nội",
        "Huyện Thạch Thất - Hà Nội",
        "Huyện Thanh Oai - Hà Nội",
        "Huyện Thanh Trì - Hà Nội",
        "Huyện Thường Tín - Hà Nội",
        "Huyện Ứng Hòa - Hà Nội"
    )
}