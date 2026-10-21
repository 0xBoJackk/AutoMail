package com.example.mailAI.controller;

import com.example.mailAI.data.EmailReq;
import com.example.mailAI.service.MailService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/mail")
class MailGenerator {

    private final MailService emailService;

    // Constructor injection
    public MailGenerator(MailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping("/generate")
    public ResponseEntity<String> genMail(
            @RequestBody EmailReq emailReq) {

        return ResponseEntity.ok(
                emailService.genMailReply(emailReq).toString()
        );
    }
}