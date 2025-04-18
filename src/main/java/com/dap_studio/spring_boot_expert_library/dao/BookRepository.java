package com.dap_studio.spring_boot_expert_library.dao;

import com.dap_studio.spring_boot_expert_library.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}
