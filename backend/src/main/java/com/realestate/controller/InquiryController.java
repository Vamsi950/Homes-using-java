package com.realestate.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.realestate.dto.request.InquiryRequest;
import com.realestate.dto.response.MessageResponse;
import com.realestate.model.Inquiry;
import com.realestate.model.Property;
import com.realestate.repository.InquiryRepository;
import com.realestate.repository.PropertyRepository;

import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/properties/{propertyId}/inquiries")
public class InquiryController {
    
    @Autowired
    InquiryRepository inquiryRepository;

    @Autowired
    PropertyRepository propertyRepository;

    @PostMapping
    public ResponseEntity<?> createInquiry(@PathVariable(value = "propertyId") Long propertyId,
                                           @Valid @RequestBody InquiryRequest inquiryRequest) {
        Optional<Property> propertyData = propertyRepository.findById(propertyId);
        
        if (!propertyData.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Inquiry inquiry = new Inquiry();
        inquiry.setProperty(propertyData.get());
        inquiry.setMessage(inquiryRequest.getMessage());
        inquiry.setContactEmail(inquiryRequest.getContactEmail());
        inquiry.setContactPhone(inquiryRequest.getContactPhone());

        inquiryRepository.save(inquiry);
        return ResponseEntity.ok(new MessageResponse("Inquiry sent successfully!"));
    }
}
