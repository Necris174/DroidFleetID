package com.example.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class AddressLayerDto (
    @SerializedName("address")
    @Expose
    val address: AddressDto? = null,
    @SerializedName("addresstype")
    @Expose
    val addresstype: String? = null,
    @SerializedName("boundingbox")
    @Expose
    val boundingbox: List<String>? = null,
    @SerializedName("category")
    @Expose
    val category: String? = null,
    @SerializedName("display_name")
    @Expose
    val displayName: String? = null,
    @SerializedName("importance")
    @Expose
    val importance: Double? = null,
    @SerializedName("lat")
    @Expose
    val lat: String? = null,
    @SerializedName("licence")
    @Expose
    val licence: String? = null,
    @SerializedName("lon")
    @Expose
    val lon: String? = null,
    @SerializedName("name")
    @Expose
    val name: Any? = null,
    @SerializedName("osm_id")
    @Expose
    val osmId: Int? = null,
    @SerializedName("osm_type")
    @Expose
    val osmType: String? = null,
    @SerializedName("place_id")
    @Expose
    val placeId: Int? = null,
    @SerializedName("place_rank")
    @Expose
    val placeRank: Int? = null,
    @SerializedName("type")
    @Expose
    val type: String? = null,
)
