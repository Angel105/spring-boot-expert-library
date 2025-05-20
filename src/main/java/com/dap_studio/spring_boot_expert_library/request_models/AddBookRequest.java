package com.dap_studio.spring_boot_expert_library.request_models;

import lombok.Data;

@Data
public class AddBookRequest {

    private String title;

    private String author;

    private String description;

    private int copies;

    private String category;

    private String img;
}
