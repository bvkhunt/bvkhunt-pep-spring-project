package com.example.repository;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.entity.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {

    @Query("SELECT m FROM Message m WHERE m.posted_by = :account_id")
    List<Message> findByPosted_by(Integer account_id); 
    Optional<Message> findById(Integer id);
    
    @Modifying
    @Transactional
    @Query("DELETE FROM Message m WHERE m.message_id = :messageId")
    Integer deleteByMessageId(Integer messageId);

    @Modifying
    @Transactional
    @Query("UPDATE Message m SET m.message_text = :updatedMessage WHERE m.message_id = :messageId")
    Integer updateMessageById(String updatedMessage, Integer messageId);
}
