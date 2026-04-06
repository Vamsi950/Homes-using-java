package com.realestate.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.realestate.model.Property;

@Repository
public interface PropertyRepository extends JpaRepository<Property, Long> {
    Page<Property> findByLocationContainingIgnoreCase(String location, Pageable pageable);
    Page<Property> findByPropertyTypeIgnoreCase(String propertyType, Pageable pageable);
    Page<Property> findByLocationContainingIgnoreCaseAndPropertyTypeIgnoreCase(String location, String propertyType, Pageable pageable);
}
