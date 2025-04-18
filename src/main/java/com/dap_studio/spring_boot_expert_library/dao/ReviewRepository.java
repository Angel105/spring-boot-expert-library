package com.dap_studio.spring_boot_expert_library.dao;

import com.dap_studio.spring_boot_expert_library.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}
