package com.caio.bliss.data.model

data class ErrorResponse(
    val error_description: String,
    val causes: Map<String, String> = emptyMap(),
    val message: String
)
