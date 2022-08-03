package com.example.domain

import com.google.gson.JsonObject
import javax.inject.Inject


class GetTrackUseCase @Inject constructor(private val dfRepository: DFRepository) {

    suspend operator fun invoke(authorization: String, device_IMEI: String, account_id: String, date_from: String, date_to: String, shift_vars: String): JsonObject {
        return dfRepository.getTrack(authorization, device_IMEI, account_id, date_from, date_to, shift_vars)
    }
}