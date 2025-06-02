package com.dap_studio.spring_boot_expert_library.controller;

import com.dap_studio.spring_boot_expert_library.request_models.PaymentInfoRequest;
import com.dap_studio.spring_boot_expert_library.service.PaymentService;
import com.dap_studio.spring_boot_expert_library.utils.ExtractJWT;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("https://localhost:5173")
@RestController
@RequestMapping("/api/payment/secure")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/payment-intent")
    public ResponseEntity<String> createPaymentIntent(@RequestBody PaymentInfoRequest paymentInfoRequest)
            throws StripeException {

        PaymentIntent paymentIntent = paymentService.createPaymentIntent(paymentInfoRequest);
        String paymentString = paymentIntent.toJson();

        return new ResponseEntity<>(paymentString, HttpStatus.OK);
    }

    @PutMapping("/payment-complete")
    public ResponseEntity<String> stripePaymentComplete(@RequestHeader(value= "Authorization") String token)
        throws Exception {
        String userEmail = ExtractJWT.payloadJWTExtraction(token, "\"sub\"");
        if (userEmail == null) {
            throw new Exception("User email is missing from token");
        }
        ResponseEntity<String> response = paymentService.stripePayment(userEmail);
        if (response.getStatusCode() != HttpStatus.OK) {
            throw new Exception("Stripe payment failed");
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
