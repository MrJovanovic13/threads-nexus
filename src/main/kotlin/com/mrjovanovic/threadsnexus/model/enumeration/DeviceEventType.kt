package com.mrjovanovic.threadsnexus.model.enumeration

enum class DeviceEventType(val title: String, val severity: Severity) {
    DEVICE_ONLINE("DEVICE ONLINE", Severity.LOW),
    DEVICE_OFFLINE("DEVICE OFFLINE", Severity.MEDIUM)
}
