package com.dap_studio.spring_boot_expert_library.controller;

import com.dap_studio.spring_boot_expert_library.request_models.ReviewRequest;
import com.dap_studio.spring_boot_expert_library.service.ReviewService;
import com.dap_studio.spring_boot_expert_library.utils.ExtractJWT;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("http://localhost:5173")
@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping("/secure")
    public void postReview(@RequestHeader(value="Authorization") String token,
                           @RequestBody ReviewRequest reviewRequest) throws Exception {

        String userEmail = ExtractJWT.payloadJWTExtraction(token, "\"sub\"");
        if (userEmail == null) {
            throw new Exception("User email is missing from token");
        }
        reviewService.postReview(userEmail, reviewRequest);
    }
}
