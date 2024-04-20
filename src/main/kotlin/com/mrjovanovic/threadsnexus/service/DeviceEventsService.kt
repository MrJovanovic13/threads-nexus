package com.mrjovanovic.threadsnexus.service

import com.mrjovanovic.threadsnexus.model.Device
import com.mrjovanovic.threadsnexus.model.DeviceEvent

interface DeviceEventsService {

    fun postDeviceEvent(deviceEvent: DeviceEvent)

    fun postCurrentDeviceStatusEvent(device: Device)
}
