
package com.foodsave.repository;

import com.foodsave.model.FoodListing;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FoodListingRepository
        extends JpaRepository<FoodListing, Long> {
}
