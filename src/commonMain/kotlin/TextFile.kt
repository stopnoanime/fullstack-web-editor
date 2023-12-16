package com.common

import kotlinx.serialization.Serializable

@Serializable
data class TextFile(val id: String, var content: String)