package com.realestate.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class InquiryRequest {
    @NotBlank
    private String message;

    @NotBlank
    @Email
    private String contactEmail;

    private String contactPhone;
}
