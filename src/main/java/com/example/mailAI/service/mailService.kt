
package com.example.mailAI.service

import com.example.mailAI.data.EmailReq
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import tools.jackson.databind.JsonNode
import tools.jackson.databind.ObjectMapper

@Service
class MailService(
     val webClientBuilder: WebClient.Builder
) {

    @Value("\${Url}")
    private lateinit var apiUrl: String

    @Value("\${key}")
    private lateinit var apiKey: String

    fun genMailReply(emailReq: EmailReq): String {
        val prompt = buildPrompt(emailReq)

        val craftReq: Map<String, Any> = mapOf(
            "contents" to arrayOf(
                mapOf(
                    "parts" to arrayOf(
                        mapOf("text" to prompt)
                    )
                )
            )
        )

        val response = webClientBuilder.build()
            .post()
            .uri(apiUrl)
            .header("Content-Type", "application/json")
            .header("X-goog-api-key", apiKey)
            .bodyValue(craftReq)
            .retrieve()
            .bodyToMono(String::class.java)
            .block()

        return extractResponseContent(response)
    }

    private fun buildPrompt(emailReq: EmailReq): String {
        val builder = StringBuilder()
        builder.append("Generate a professional mail reply. ")

        if (!emailReq.tone.isNullOrBlank()) {
            builder.append("Tone: ")
                .append(emailReq.tone)
                .append(". ")
        }

        builder.append(emailReq.content)
        return builder.toString()
    }

    private fun extractResponseContent(response: String?): String {
        require(!response.isNullOrBlank()) {
            "Empty response received from Gemini API"
        }

        val mapper = ObjectMapper()
        val rootNode: JsonNode = mapper.readTree(response)

        return rootNode.path("candidates")
            .path(0)
            .path("content")
            .path("parts")
            .path(0)
            .path("text")
            .asText()
    }
}
