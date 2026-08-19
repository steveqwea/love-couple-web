package com.love.service;

import com.love.entity.Message;
import com.love.mapper.MessageMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class MessageService {

    @Resource
    private MessageMapper messageMapper;

    public Message add(Message message) {
        messageMapper.insert(message);
        return message;
    }

    public List<Message> list(String coupleCode) {
        return messageMapper.listByCoupleCode(coupleCode);
    }

    public void delete(Long id) {
        messageMapper.deleteById(id);
    }
}
