package com.realestate.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import com.realestate.model.Role;
import com.realestate.model.User;
import com.realestate.repository.UserRepository;
import com.realestate.model.Property;
import com.realestate.model.PropertyImage;
import com.realestate.repository.PropertyRepository;
import com.realestate.repository.PropertyImageRepository;
import java.math.BigDecimal;
import java.util.ArrayList;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PropertyRepository propertyRepository;

    @Autowired
    PropertyImageRepository propertyImageRepository;

    @Autowired
    PasswordEncoder encoder;

    @Override
    public void run(String... args) throws Exception {
        // Initialize Users if none exist
        if (userRepository.count() == 0) {
            User admin = new User();
            admin.setUsername("admin");
            admin.setEmail("admin@example.com");
            admin.setPassword(encoder.encode("admin123"));
            admin.setRole(Role.ADMIN);
            userRepository.save(admin);

            User user = new User();
            user.setUsername("vamsi");
            user.setEmail("vamsi@example.com");
            user.setPassword(encoder.encode("vamsi123"));
            user.setRole(Role.USER);
            userRepository.save(user);

            System.out.println("Default users created: admin/admin123 and vamsi/vamsi123");

            // Initialize sample properties linked to 'vamsi'
            Property p1 = new Property();
            p1.setTitle("Modern Luxury Villa");
            p1.setDescription("A beautiful modern villa with high-end finishes and a large pool.");
            p1.setPrice(new BigDecimal("850000"));
            p1.setLocation("Beverly Hills, CA");
            p1.setPropertyType("Villa");
            p1.setBedrooms(4);
            p1.setBathrooms(3);
            p1.setAreaSqft(3200);
            p1.setSeller(user);
            p1 = propertyRepository.save(p1);

            PropertyImage i1 = new PropertyImage();
            i1.setProperty(p1);
            i1.setImageUrl("https://images.unsplash.com/photo-1613490493576-7fde63acd811?auto=format&fit=crop&w=800&q=80");
            propertyImageRepository.save(i1);

            Property p2 = new Property();
            p2.setTitle("Downtown Apartment");
            p2.setDescription("Cozy city apartment near all the action with great views.");
            p2.setPrice(new BigDecimal("450000"));
            p2.setLocation("Manhattan, NY");
            p2.setPropertyType("Apartment");
            p2.setBedrooms(2);
            p2.setBathrooms(2);
            p2.setAreaSqft(1100);
            p2.setSeller(user);
            p2 = propertyRepository.save(p2);

            PropertyImage i2 = new PropertyImage();
            i2.setProperty(p2);
            i2.setImageUrl("https://images.unsplash.com/photo-1502672260266-1c1ef2d93688?auto=format&fit=crop&w=800&q=80");
            propertyImageRepository.save(i2);
            
            System.out.println("Sample properties with images created and linked to user 'vamsi'.");
        }
    }
}
