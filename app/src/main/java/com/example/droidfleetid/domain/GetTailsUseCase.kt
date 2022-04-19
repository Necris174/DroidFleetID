package com.example.droidfleetid.domain

import com.example.droidfleetid.data.DeviceRequestItem
import com.example.droidfleetid.data.TailsDto
import com.example.droidfleetid.domain.entity.DeviceEntity
import com.google.gson.JsonObject
import org.json.JSONArray
import org.json.JSONObject

class GetTailsUseCase(
    private val dfRepository: DFRepository) {

    suspend operator fun invoke(deviceList: List<DeviceRequestItem>, accessToken: String): List<TailsDto> {
        return dfRepository.getTails(deviceList, accessToken)
    }

}