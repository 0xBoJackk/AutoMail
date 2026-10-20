package com.example.mailAI

import com.example.mailAI.controller.EmailReq
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient

@Service
class MailService {

    private lateinit var webClient: WebClient

    @Value("\${Url}")
    private lateinit var apiUrl: String

    @Value("\${key}")
    private lateinit var apiKey: String

    fun genMailReply(emailReq: EmailReq): String {

        // Build prompt
        val prompt = buildPrompt(emailReq)

        // Craft a request
        val craftReq: Map<String, Any> = mapOf(
            "contents" to arrayOf(
                mapOf(
                    "parts" to arrayOf(
                        mapOf("text" to prompt)
                    )
                )
            )
        )

        //extract response and return
        return extractResponseContent(response)


    }

    // Next: send craftReq to the AI API

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

    // do req and get response
    var response  = webClient.post()
        .uri(apiUrl+apiKey)
        .header("content-type","application/json")
        .retrieve()
        .bodyToMono(String::class.java)
        .block()


    //extract and return the response

    fun extractResponseContent(response: String?): String {
        return ""
    }




}