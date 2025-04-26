package com.dap_studio.spring_boot_expert_library.request_models;

import lombok.Data;

import java.util.Optional;

@Data
public class ReviewRequest {

    private double rating;
    private Long bookId;
    private Optional<String> reviewDescription;
}
