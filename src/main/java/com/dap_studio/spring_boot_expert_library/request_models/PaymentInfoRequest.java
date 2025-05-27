package com.dap_studio.spring_boot_expert_library.request_models;

import lombok.Data;

@Data
public class PaymentInfoRequest {

    private int amount;

    private String currency;

    private String receiptEmail;
}
