package com.example.domain.entity

data class Tails(
var accountId: String? = null,
var imei: String? = null,
var status: String? = null,
var descr: String? = null,
var datumDto: List<Datum>?  = null,
var sensors: List<Sensor>? = null)

