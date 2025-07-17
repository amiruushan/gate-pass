package com.example.Gate_pass_system.configuration;

import com.example.Gate_pass_system.entity.*;
import com.example.Gate_pass_system.repo.ItemCategoryRepository;
import com.example.Gate_pass_system.repo.LocationRepository;
import com.example.Gate_pass_system.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;


@Component
public class DataInitializer implements CommandLineRunner {


    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private ItemCategoryRepository itemCategoryRepository;



    @Override
    public void run(String... args) throws Exception {
        // Initialize hardcoded locations
        List<String> predefinedLocations = Arrays.asList(
                "Colombo Main",
                "Panadura Branch",
                "Kurunegala Branch",
                "Matara Brach",
                "Hambantota Branch",
                "Kalutara Branch",
                "Anuradhapura Branch",
                "Polonnaruwa Branch",
                "Kandy Branch",
                "Matale Branch",
                "Dabulla Branch",
                "Galle Branch",
                "Jaffna Branch",
                "Batticaloa Branch"
        );

        for (String locationName : predefinedLocations) {
            if (!locationRepository.existsByLocationName(locationName)) {
                Location location = new Location();
                location.setLocationName(locationName);
                locationRepository.save(location);
                System.out.println("Location '" + locationName + "' created successfully.");
            } else {
                System.out.println("Location '" + locationName + "' already exists.");
            }
        }

        // Initialize hardcoded categories
        List<String> predefinedCategories = Arrays.asList(
                "Routers",
                "Land Phones",
                "Copper Cables",
                "Smart Phones"
        );

        for (String categoryName : predefinedCategories) {
            if (!itemCategoryRepository.existsByCategoryName(categoryName)) {
                ItemCategory category = new ItemCategory();
                category.setCategoryName(categoryName);
                itemCategoryRepository.save(category);
                System.out.println("Category '" + categoryName + "' created successfully.");
            } else {
                System.out.println("Category '" + categoryName + "' already exists.");
            }
        }


    }

}