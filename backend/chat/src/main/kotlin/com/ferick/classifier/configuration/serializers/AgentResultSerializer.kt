package com.ferick.classifier.configuration.serializers

import com.fasterxml.jackson.databind.ObjectMapper
import com.ferick.classifier.model.dto.AgentResult
import org.bsc.langgraph4j.serializer.Serializer
import java.io.ObjectInput
import java.io.ObjectOutput

class AgentResultSerializer(
    private val objectMapper: ObjectMapper
) : Serializer<AgentResult> {

    override fun write(obj: AgentResult, output: ObjectOutput) {
        val jsonBytes = objectMapper.writeValueAsBytes(obj)
        output.writeInt(jsonBytes.size)
        output.write(jsonBytes)
    }

    override fun read(input: ObjectInput): AgentResult {
        val size = input.readInt()
        val bytes = ByteArray(size)
        input.readFully(bytes)
        return objectMapper.readValue(bytes, AgentResult::class.java)
    }
}
