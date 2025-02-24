package com.coder.toolbox.sdk.convertors

import com.coder.toolbox.util.Arch
import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson

/**
 * Serializer/deserializer for converting [Arch] objects.
 */
class ArchConverter {
    @ToJson fun toJson(src: Arch?): String = src?.toString() ?: ""

    @FromJson fun fromJson(src: String): Arch? = Arch.from(src)
}
