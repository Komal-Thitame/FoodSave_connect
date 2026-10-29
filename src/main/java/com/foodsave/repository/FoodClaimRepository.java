
package com.foodsave.repository;

import com.foodsave.model.FoodClaim;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FoodClaimRepository extends JpaRepository<FoodClaim, Long> {

    List<FoodClaim> findByClaimer_Id(Long claimerId);

    List<FoodClaim> findByListing_Id(Long listingId);

    boolean existsByListing_IdAndClaimer_Id(Long listingId, Long claimerId);
}
