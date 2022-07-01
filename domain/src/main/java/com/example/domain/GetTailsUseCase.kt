package com.example.domain


import com.example.domain.entity.DeviceRequest
import com.example.domain.entity.Tails
import javax.inject.Inject

class GetTailsUseCase @Inject constructor(
    private val dfRepository: DFRepository) {

    suspend operator fun invoke(deviceList: List<DeviceRequest>, accessToken: String): List<Tails> {
        return dfRepository.getTails(deviceList, accessToken)
    }

}