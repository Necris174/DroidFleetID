package com.example.data.model

import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose

data class AddressDto (
    @SerializedName("city")
    @Expose
   val city: String? = null,
    @SerializedName("city_district")
    @Expose
    val cityDistrict: String? = null,
    @SerializedName("country")
    @Expose
    val country: String? = null,
    @SerializedName("country_code")
    @Expose
    val countryCode: String? = null,
    @SerializedName("county")
    @Expose
    val county: String? = null,
    @SerializedName("house_number")
    @Expose
    val houseNumber :String? = null,
    @SerializedName("postcode")
    @Expose
    val postcode: String? = null,
    @SerializedName("region")
    @Expose
    val region: String? = null,
    @SerializedName("road")
    @Expose
    val road :String? = null,
    @SerializedName("state")
    @Expose
    val state: String? = null,
    @SerializedName("suburb")
    @Expose
    val suburb: String? = null,
)