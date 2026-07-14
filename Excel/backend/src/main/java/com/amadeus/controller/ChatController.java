package com.amadeus.controller;

import com.amadeus.service.ChatService;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/chat")
@Validated
public class ChatController {

    @Autowired
    private ChatService chatService;

    @PostMapping(value = "/process", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity process(@RequestPart MultipartFile file,
                                  @RequestPart @NotBlank(message ="prompt不能为空") String prompt) throws IOException {
        return ResponseEntity.ok(chatService.process(file,prompt));
    }


}
