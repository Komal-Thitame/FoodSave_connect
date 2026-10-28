
package com.foodsave.service;

import com.foodsave.model.FoodListing;
import com.foodsave.repository.FoodListingRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FoodListingService {

    private final FoodListingRepository foodListingRepository;

    public FoodListingService(FoodListingRepository foodListingRepository) {
        this.foodListingRepository = foodListingRepository;
    }

    public FoodListing createFoodListing(FoodListing foodListing) {
        foodListing.setStatus("AVAILABLE");
        return foodListingRepository.save(foodListing);
    }

    public List<FoodListing> getAllFoodListings() {
        return foodListingRepository.findAll();
    }

    public FoodListing getFoodListingById(Long id) {
        return foodListingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(
                        "Food listing not found with id: " + id
                ));
    }
}
