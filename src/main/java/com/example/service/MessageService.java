package com.example.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Message;
import com.example.repository.AccountRepository;
import com.example.repository.MessageRepository;

@Service
public class MessageService {
    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private AccountRepository accountRepository;

    public List<Message> getAllMessages() {
        List<Message> messages = new ArrayList<>();

        messages = messageRepository.findAll();
        return messages;
    }

    public List<Message> getAllMessagesByUser(Integer account_id) {
        List<Message> messages = messageRepository.findByPosted_by(account_id);
        return messages;
    }

    public Message createMessage(Message message) {
        if (isValidMessage(message.getMessage_text())
                && !accountRepository.findById(message.getPosted_by()).isEmpty()) {
            return messageRepository.save(message);
        }
        return null;
    }

    public Integer updateMessage(String updatedMessage, Integer id) {
        if (isValidMessage(updatedMessage)) {
            if (messageRepository.existsById(id)) {
                return messageRepository.updateMessageById(updatedMessage, id);
            }
        }
        return 0;
    }

    public Message getMessageById(Integer id){
        Optional<Message> message = messageRepository.findById(id);
        if(message.isPresent()){
            return message.get();
        }

        return null;

    }
    public List<Message> getMessagesByUser(Integer accountId){
        List<Message> messages = new ArrayList<>();

        messages = messageRepository.findByPosted_by(accountId);

        return messages;
    }

    public Integer deleteMessage(Integer id) {
        return messageRepository.deleteByMessageId(id);
    }

    public Message getMessage(Integer id) {
        Optional<Message> opMessage = messageRepository.findById(id);
        if(opMessage.isPresent()){
            return opMessage.get();
        }
        return null;
    }

    public Boolean isValidMessage(String message) {
        return message != null && message.length() < 256 && !message.isEmpty();
    }
}