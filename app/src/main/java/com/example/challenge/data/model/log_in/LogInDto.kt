package com.example.challenge.data.model.log_in

import com.squareup.moshi.Json

data class LogInDto(
    @Json(name = "needsMfa")
    var needsMfa: Boolean? = null,
    @Json(name = "AccessToken")
    var accessToken: String? = null,
    @Json(name = "RefreshToken")
    var refreshToken: String? = null
)