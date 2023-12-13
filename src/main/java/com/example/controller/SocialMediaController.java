package com.example.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.service.AccountService;
import com.example.service.MessageService;

/**
 * TODO: You will need to write your own endpoints and handlers for your
 * controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use
 * the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations.
 * You should
 * refer to prior mini-project labs and lecture materials for guidance on how a
 * controller may be built.
 */
@RestController
public class SocialMediaController {
    private AccountService accountService;
    private MessageService messageService;

    SocialMediaController(AccountService accountService, MessageService messageService) {
        this.accountService = accountService;
        this.messageService = messageService;
    }

    @PostMapping("/register")
    public ResponseEntity<Account> registerUser(@RequestBody Account account) {
        if (accountService.isValidAccount(account)) {
            if (accountService.isExistingAccount(account.getUsername())) {
                return ResponseEntity.status(409).build();
            }
            Account registeredAccount = accountService.registerAccount(account);
            return ResponseEntity.status(200).body(registeredAccount);
        }
        return ResponseEntity.status(400).build();
    }

    @PostMapping("/login")
    public ResponseEntity<Account> loginUser(@RequestBody Account account) {
        Account fetchedAccount =  accountService.getAccount(account);
        if (fetchedAccount != null) {
            return ResponseEntity.status(200).body(fetchedAccount);
        }
        return ResponseEntity.status(401).build();
    }

    @PostMapping("/messages")
    public ResponseEntity<Message> createMessage(@RequestBody Message message) {
        if(messageService.isValidMessage(message.getMessage_text()) && accountService.isExstingAccountById(message.getPosted_by())){
            Message insertedMessage = messageService.createMessage(message);
            if(insertedMessage != null){
                return ResponseEntity.status(200).body(insertedMessage);
            }
        }
        return ResponseEntity.status(400).build();
    }

    @GetMapping("/messages")
    public ResponseEntity<List<Message>> getAllMessages() {
        return ResponseEntity.status(200).body(messageService.getAllMessages());
    }

    @GetMapping("/messages/{message_id}")
    public ResponseEntity<Message> getMessageById(@PathVariable("message_id") Integer messageId) {
        Message message = messageService.getMessageById(messageId);
        if(message != null){
            return ResponseEntity.status(200).body(message);
        }
        return ResponseEntity.status(200).build();
    }

    @DeleteMapping("/messages/{message_id}")
    public ResponseEntity<Integer> deleteById(@PathVariable("message_id") Integer messageId) {
        Integer deletedRows = messageService.deleteMessage(messageId);
        if(deletedRows > 0){
            return ResponseEntity.status(200).body(1);
        }
        return ResponseEntity.status(200).build();
    }

    @PatchMapping("/messages/{message_id}")
    public ResponseEntity<Integer> updateMessage(@PathVariable("message_id") Integer messageId, @RequestBody(required = false) Message message) {     
        Integer updatedMessage = messageService.updateMessage(message.getMessage_text(), messageId);
        if(updatedMessage > 0){
            return ResponseEntity.status(200).body(1);
        }
        return ResponseEntity.status(400).build();
    }

    @GetMapping("/accounts/{account_id}/messages")
    public ResponseEntity<List<Message>> updateMessage(@PathVariable("account_id") Integer accountId) {
        
        return ResponseEntity.status(200).body(messageService.getMessagesByUser(accountId));
    }
}