package com.realestate.config;

import com.realestate.model.Property;
import com.realestate.model.PropertyImage;
import com.realestate.model.Role;
import com.realestate.model.User;
import com.realestate.repository.PropertyRepository;
import com.realestate.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class DataSeeder implements CommandLineRunner {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PropertyRepository propertyRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        if (userRepository.count() == 0) {
            User admin = new User();
            admin.setUsername("admin");
            admin.setEmail("admin@realestate.com");
            admin.setPassword(passwordEncoder.encode("password123"));
            admin.setRole(Role.ADMIN);
            userRepository.save(admin);

            User testUser = new User();
            testUser.setUsername("testuser");
            testUser.setEmail("test@user.com");
            testUser.setPassword(passwordEncoder.encode("password123"));
            testUser.setRole(Role.USER);
            userRepository.save(testUser);

            Property p1 = new Property();
            p1.setTitle("Luxury Oceanview Villa");
            p1.setDescription("A beautiful 5 bedroom villa with stunning ocean views, private pool, and modern amenities.");
            p1.setPrice(new BigDecimal("1250000"));
            p1.setPropertyType("Villa");
            p1.setLocation("Malibu, CA");
            p1.setBedrooms(5);
            p1.setBathrooms(4);
            p1.setAreaSqft(4500);
            p1.setSeller(admin);

            PropertyImage pi1_1 = new PropertyImage();
            pi1_1.setImageUrl("https://images.unsplash.com/photo-1613490900233-141c5560d75d");
            pi1_1.setProperty(p1);
            PropertyImage pi1_2 = new PropertyImage();
            pi1_2.setImageUrl("https://images.unsplash.com/photo-1512917774080-9991f1c4c750");
            pi1_2.setProperty(p1);

            p1.getImages().add(pi1_1);
            p1.getImages().add(pi1_2);
            propertyRepository.save(p1);

            Property p2 = new Property();
            p2.setTitle("Modern Downtown Apartment");
            p2.setDescription("Experience city life in this beautifully furnished high-rise apartment.");
            p2.setPrice(new BigDecimal("450000"));
            p2.setPropertyType("Apartment");
            p2.setLocation("Downtown LA");
            p2.setBedrooms(2);
            p2.setBathrooms(2);
            p2.setAreaSqft(1200);
            p2.setSeller(admin);

            PropertyImage pi2 = new PropertyImage();
            pi2.setImageUrl("https://images.unsplash.com/photo-1502672260266-1c1c24240f57");
            pi2.setProperty(p2);
            p2.getImages().add(pi2);
            propertyRepository.save(p2);
            
            Property p3 = new Property();
            p3.setTitle("Suburban Family Home");
            p3.setDescription("Spacious home situated in a quiet cul-de-sac. Excellent schools and parks nearby.");
            p3.setPrice(new BigDecimal("680000"));
            p3.setPropertyType("House");
            p3.setLocation("Irvine, CA");
            p3.setBedrooms(4);
            p3.setBathrooms(3);
            p3.setAreaSqft(2800);
            p3.setSeller(testUser);

            PropertyImage pi3 = new PropertyImage();
            pi3.setImageUrl("https://images.unsplash.com/photo-1570129477492-45c003edd2be");
            pi3.setProperty(p3);
            p3.getImages().add(pi3);
            propertyRepository.save(p3);
            Property p4 = new Property();
            p4.setTitle("Cozy Mountain Cabin");
            p4.setDescription("Escape to this picturesque mountain cabin featuring a massive stone fireplace, vaulted ceilings, and a wraparound deck with panoramic forest views. Includes a private hot tub.");
            p4.setPrice(new BigDecimal("550000"));
            p4.setPropertyType("House");
            p4.setLocation("Aspen, CO");
            p4.setBedrooms(3);
            p4.setBathrooms(2);
            p4.setAreaSqft(1800);
            p4.setSeller(testUser);

            PropertyImage pi4_1 = new PropertyImage();
            pi4_1.setImageUrl("https://images.unsplash.com/photo-1542718610-a1d656d1884c");
            pi4_1.setProperty(p4);
            
            PropertyImage pi4_2 = new PropertyImage();
            pi4_2.setImageUrl("https://images.unsplash.com/photo-1518780664697-55e3ad937233");
            pi4_2.setProperty(p4);

            p4.getImages().add(pi4_1);
            p4.getImages().add(pi4_2);
            propertyRepository.save(p4);

            Property p5 = new Property();
            p5.setTitle("Contemporary Beachfront Condo");
            p5.setDescription("Ultra-modern condo directly on the beach. Enjoy floor-to-ceiling windows, smart home technology, resort-style amenities, and direct beach access.");
            p5.setPrice(new BigDecimal("1200000"));
            p5.setPropertyType("Condo");
            p5.setLocation("Miami Beach, FL");
            p5.setBedrooms(2);
            p5.setBathrooms(2);
            p5.setAreaSqft(1500);
            p5.setSeller(admin);

            PropertyImage pi5_1 = new PropertyImage();
            pi5_1.setImageUrl("https://images.unsplash.com/photo-1512918728675-ed5a9ecdebfd");
            pi5_1.setProperty(p5);
            
            PropertyImage pi5_2 = new PropertyImage();
            pi5_2.setImageUrl("https://images.unsplash.com/photo-1600607687920-4e2a09cf159d");
            pi5_2.setProperty(p5);

            p5.getImages().add(pi5_1);
            p5.getImages().add(pi5_2);
            propertyRepository.save(p5);
        }
    }
}
