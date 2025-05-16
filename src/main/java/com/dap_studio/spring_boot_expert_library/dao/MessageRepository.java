package com.dap_studio.spring_boot_expert_library.dao;

import com.dap_studio.spring_boot_expert_library.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long> {
}
