package com.foodsave.repository;

import com.foodsave.model.FoodListing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FoodListingRepository extends JpaRepository<FoodListing, Long> {

    List<FoodListing> findByStatus(FoodListing.ListingStatus status);

    List<FoodListing> findByDonorId(Long donorId);

    List<FoodListing> findByCategory(String category);

    @Query("SELECT f FROM FoodListing f WHERE f.status = 'AVAILABLE' AND " +
           "(LOWER(f.title) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(f.category) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(f.location) LIKE LOWER(CONCAT('%', :keyword, '%')))")
    List<FoodListing> searchListings(@Param("keyword") String keyword);

    @Query("SELECT f FROM FoodListing f WHERE f.status = 'AVAILABLE' AND f.freshnessStatus != 'EXPIRED'")
    List<FoodListing> findAllAvailable();
}
