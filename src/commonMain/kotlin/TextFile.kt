package com.common

import kotlinx.serialization.Serializable

@Serializable
data class TextFile(var id: String, var content: String)