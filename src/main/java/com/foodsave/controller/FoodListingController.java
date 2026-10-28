
package com.foodsave.controller;

import com.foodsave.model.FoodListing;
import com.foodsave.service.FoodListingService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/foods")
public class FoodListingController {

    private final FoodListingService foodListingService;

    public FoodListingController(FoodListingService foodListingService) {
        this.foodListingService = foodListingService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FoodListing createFoodListing(@RequestBody FoodListing foodListing) {
        return foodListingService.createFoodListing(foodListing);
    }

    @GetMapping
    public List<FoodListing> getAllFoodListings() {
        return foodListingService.getAllFoodListings();
    }

    @GetMapping("/{id}")
    public FoodListing getFoodListingById(@PathVariable Long id) {
        return foodListingService.getFoodListingById(id);
    }
}
