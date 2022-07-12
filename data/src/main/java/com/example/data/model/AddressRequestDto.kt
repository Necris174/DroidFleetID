package com.example.data.model

import com.example.domain.entity.AddressPoint
import com.google.gson.annotations.SerializedName

class AddressRequestDto(
    @SerializedName("points")
    val points: List<AddressPoint>,
    @SerializedName("zoom,omitempty")
    val zoom: Int,
    @SerializedName("accept-language")
    val acceptLanguage: String
)