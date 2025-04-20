package com.dap_studio.spring_boot_expert_library.dao;

import com.dap_studio.spring_boot_expert_library.entity.Checkout;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CheckoutRepository extends JpaRepository<Checkout, Long> {

    Checkout findByUserEmailAndBookId(String userEmail, Long bookId);
}
