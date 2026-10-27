package com.foodsave.repository;

import com.foodsave.model.FoodClaim;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FoodClaimRepository extends JpaRepository<FoodClaim, Long> {

    List<FoodClaim> findByClaimerId(Long claimerId);

    List<FoodClaim> findByListingId(Long listingId);

    List<FoodClaim> findByListingDonorId(Long donorId);

    boolean existsByListingIdAndClaimerId(Long listingId, Long claimerId);
}
