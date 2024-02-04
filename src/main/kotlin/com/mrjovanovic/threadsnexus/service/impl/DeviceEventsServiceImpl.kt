package com.mrjovanovic.threadsnexus.service.impl

import com.mrjovanovic.threadsnexus.model.Device
import com.mrjovanovic.threadsnexus.model.DeviceEvent
import com.mrjovanovic.threadsnexus.model.enumeration.DeviceStatus
import com.mrjovanovic.threadsnexus.model.enumeration.Severity
import com.mrjovanovic.threadsnexus.repository.DeviceEventRepository
import com.mrjovanovic.threadsnexus.service.DeviceEventsService
import org.springframework.stereotype.Service
import java.time.Instant

@Service
class DeviceEventsServiceImpl(private val deviceEventRepository: DeviceEventRepository) : DeviceEventsService {

    override fun postDeviceOnlineStatusChangeEvent(device: Device, status: DeviceStatus) {
        val deviceEvent = DeviceEvent(null, "DEVICE OFFLINE", null, Instant.now(), Severity.MEDIUM, device)

        postDeviceEvent(deviceEvent)
    }

    override fun postDeviceEvent(deviceEvent: DeviceEvent) {
        deviceEventRepository.save(deviceEvent).subscribe()
    }
}
