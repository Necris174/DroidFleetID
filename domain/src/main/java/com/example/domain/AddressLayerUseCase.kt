package com.example.domain

import com.example.domain.entity.Address
import com.example.domain.entity.AddressLayer
import javax.inject.Inject

class AddressLayerUseCase @Inject constructor(
    private val dfRepository: DFRepository
) {


    suspend operator fun invoke (accessToken: String, addressLayer: AddressLayer): List<Address> {
       return dfRepository.getAddressLayer(accessToken ,addressLayer)
    }
}