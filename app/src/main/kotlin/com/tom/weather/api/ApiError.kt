package com.tom.weather.api

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ApiError(
    @SerialName("reason") val reason: String,
) : Throwable(message = reason)
