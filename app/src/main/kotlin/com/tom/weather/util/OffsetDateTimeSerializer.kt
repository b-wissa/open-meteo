package com.tom.weather.util

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.time.Instant
import java.time.OffsetDateTime


object OffsetDateTimeSerializer : KSerializer<OffsetDateTime> {

    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor(
            serialName = "com.tom.weather.DateTime",
            kind = PrimitiveKind.LONG
        )

    override fun deserialize(decoder: Decoder): OffsetDateTime {
        val long = decoder.decodeLong()
        return OffsetDateTime.ofInstant(Instant.ofEpochSecond(long), appZoneId)
    }

    override fun serialize(encoder: Encoder, value: OffsetDateTime) {
        val long = value.toEpochSecond()
        encoder.encodeLong(long)
    }
}
