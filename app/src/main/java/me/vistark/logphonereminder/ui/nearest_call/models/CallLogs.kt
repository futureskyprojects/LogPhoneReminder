package me.vistark.logphonereminder.ui.nearest_call.models

import java.util.*

class CallLogs(
    val phoneNumber: String,
    val callType: Int,
    val callDate: Date,
    val callDuration: String
)