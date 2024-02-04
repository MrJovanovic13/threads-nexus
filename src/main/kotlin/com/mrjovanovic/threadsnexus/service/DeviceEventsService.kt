package com.mrjovanovic.threadsnexus.service

import com.mrjovanovic.threadsnexus.model.Device
import com.mrjovanovic.threadsnexus.model.DeviceEvent
import com.mrjovanovic.threadsnexus.model.enumeration.DeviceStatus

interface DeviceEventsService {

    fun postDeviceEvent(deviceEvent: DeviceEvent)

    fun postDeviceOnlineStatusChangeEvent(device: Device, status: DeviceStatus)
}
