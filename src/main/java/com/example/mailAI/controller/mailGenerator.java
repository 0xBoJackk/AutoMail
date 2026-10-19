package com.example.mailAI.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/mail")
public class mailGenerator {

     public ResponseEntity<String> genMail (@RequestBody EmailReq mailreq){

         return ResponseEntity.ok("");

    }
}
