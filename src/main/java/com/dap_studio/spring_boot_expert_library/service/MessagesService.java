package com.dap_studio.spring_boot_expert_library.service;

import com.dap_studio.spring_boot_expert_library.dao.MessageRepository;
import com.dap_studio.spring_boot_expert_library.entity.Message;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class MessagesService {

    private final MessageRepository messagesRepository;

    public MessagesService(MessageRepository messagesRepository) {
        this.messagesRepository = messagesRepository;
    }

    public void postMessage(Message messageRequest, String userEmail) {
        Message message = new Message(messageRequest.getTitle(), messageRequest.getQuestion());
        message.setUserEmail(userEmail);
        messagesRepository.save(message);
    }
}
