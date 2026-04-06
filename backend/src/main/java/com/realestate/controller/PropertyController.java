package com.realestate.controller;

import java.util.Optional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.realestate.dto.request.PropertyRequest;
import com.realestate.dto.response.MessageResponse;
import com.realestate.model.Property;
import com.realestate.model.PropertyImage;
import com.realestate.model.User;
import com.realestate.repository.PropertyImageRepository;
import com.realestate.repository.PropertyRepository;
import com.realestate.repository.UserRepository;
import com.realestate.service.FileStorageService;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/properties")
public class PropertyController {

    @Autowired
    PropertyRepository propertyRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PropertyImageRepository propertyImageRepository;

    @Autowired
    FileStorageService fileStorageService;

    @GetMapping
    public ResponseEntity<Page<Property>> getAllProperties(
            @RequestParam(required = false) String location,
            @RequestParam(required = false) String propertyType,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
            
        Pageable paging = PageRequest.of(page, size);
        Page<Property> pageTuts;

        boolean hasLocation = location != null && !location.trim().isEmpty();
        boolean hasPropertyType = propertyType != null && !propertyType.trim().isEmpty();

        if (hasLocation && hasPropertyType) {
            pageTuts = propertyRepository.findByLocationContainingIgnoreCaseAndPropertyTypeIgnoreCase(location.trim(), propertyType.trim(), paging);
        } else if (hasLocation) {
            pageTuts = propertyRepository.findByLocationContainingIgnoreCase(location.trim(), paging);
        } else if (hasPropertyType) {
            pageTuts = propertyRepository.findByPropertyTypeIgnoreCase(propertyType.trim(), paging);
        } else {
            pageTuts = propertyRepository.findAll(paging);
        }

        return ResponseEntity.ok(pageTuts);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPropertyById(@PathVariable("id") long id) {
        Optional<Property> propertyData = propertyRepository.findById(id);

        if (propertyData.isPresent()) {
            return ResponseEntity.ok(propertyData.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> createProperty(@Valid @RequestBody PropertyRequest propertyRequest) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User seller = userRepository.findByUsername(userDetails.getUsername()).orElseThrow(() -> new RuntimeException("User not found"));

        Property property = new Property();
        property.setTitle(propertyRequest.getTitle());
        property.setDescription(propertyRequest.getDescription());
        property.setPrice(propertyRequest.getPrice());
        property.setPropertyType(propertyRequest.getPropertyType());
        property.setLocation(propertyRequest.getLocation());
        property.setBedrooms(propertyRequest.getBedrooms());
        property.setBathrooms(propertyRequest.getBathrooms());
        property.setAreaSqft(propertyRequest.getAreaSqft());
        property.setSeller(seller);

        Property savedProperty = propertyRepository.save(property);
        return ResponseEntity.ok(savedProperty);
    }

    @PostMapping("/{id}/images")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> uploadImages(@PathVariable("id") long id, @RequestParam("files") MultipartFile[] files) {
        Optional<Property> propertyData = propertyRepository.findById(id);
        if (!propertyData.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        
        Property property = propertyData.get();
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        
        if (!property.getSeller().getUsername().equals(userDetails.getUsername())) {
             return ResponseEntity.status(403).body(new MessageResponse("Error: You can only upload images to your own property!"));
        }

        for (MultipartFile file : files) {
            String fileName = fileStorageService.storeFile(file);
            String fileDownloadUri = "/uploads/" + fileName;

            PropertyImage propertyImage = new PropertyImage();
            propertyImage.setProperty(property);
            propertyImage.setImageUrl(fileDownloadUri);
            propertyImageRepository.save(propertyImage);
            property.getImages().add(propertyImage);
        }

        return ResponseEntity.ok(new MessageResponse("Images uploaded successfully!"));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<?> deleteProperty(@PathVariable("id") long id) {
        if (!propertyRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        propertyRepository.deleteById(id);
        return ResponseEntity.ok(new MessageResponse("Property deleted successfully!"));
    }
}
