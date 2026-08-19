package com.love.controller;

import com.love.entity.Message;
import com.love.service.MessageService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/api/message")
public class MessageController {

    @Resource
    private MessageService messageService;

    @GetMapping("/list")
    public List<Message> list(@RequestParam String coupleCode) {
        return messageService.list(coupleCode);
    }

    @PostMapping("/add")
    public Message add(@RequestBody Message message) {
        return messageService.add(message);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        messageService.delete(id);
    }
}
