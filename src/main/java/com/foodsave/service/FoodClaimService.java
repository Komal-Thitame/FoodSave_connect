
package com.foodsave.service;

import com.foodsave.exception.DuplicateClaimException;
import com.foodsave.model.FoodClaim;
import com.foodsave.model.FoodListing;
import com.foodsave.model.User;
import com.foodsave.repository.FoodClaimRepository;
import com.foodsave.repository.FoodListingRepository;
import com.foodsave.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.time.LocalDateTime;

@Service
public class FoodClaimService {

    private final FoodClaimRepository foodClaimRepository;
    private final FoodListingRepository foodListingRepository;
    private final UserRepository userRepository;

    public FoodClaimService(
            FoodClaimRepository foodClaimRepository,
            FoodListingRepository foodListingRepository,
            UserRepository userRepository) {
        this.foodClaimRepository = foodClaimRepository;
        this.foodListingRepository = foodListingRepository;
        this.userRepository = userRepository;
    }

    // Create a food claim
    @Transactional
    public FoodClaim claimFood(
            Long listingId,
            Long claimerId,
            String message) {

        FoodListing listing = foodListingRepository.findById(listingId)
                .orElseThrow(() ->
                        new RuntimeException("Food listing not found"));

        User claimer = userRepository.findById(claimerId)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        if (!"AVAILABLE".equalsIgnoreCase(listing.getStatus())) {
            throw new RuntimeException("Food is not available");
        }

        if (foodClaimRepository
                .existsByListing_IdAndClaimer_Id(listingId, claimerId)) {
            throw new DuplicateClaimException(
                    "You have already claimed this food");
        }

        FoodClaim claim = FoodClaim.builder()
                .listing(listing)
                .claimer(claimer)
                .status(FoodClaim.ClaimStatus.PENDING)
                .message(message)
                .build();

        return foodClaimRepository.save(claim);
    }

    // Update a pending claim to APPROVED or REJECTED
    @Transactional
    public FoodClaim updateClaimStatus(
            Long claimId,
            FoodClaim.ClaimStatus newStatus) {

        FoodClaim claim = foodClaimRepository.findById(claimId)
                .orElseThrow(() ->
                        new RuntimeException("Claim not found"));

        if (newStatus != FoodClaim.ClaimStatus.APPROVED
                && newStatus != FoodClaim.ClaimStatus.REJECTED) {
            throw new IllegalArgumentException(
                    "Status must be APPROVED or REJECTED");
        }

        if (claim.getStatus() != FoodClaim.ClaimStatus.PENDING) {
            throw new IllegalStateException(
                    "Only pending claims can be updated");
        }

        claim.setStatus(newStatus);

        return foodClaimRepository.save(claim);
    }

    // Get all claims made by a user
    @Transactional(readOnly = true)
    public List<FoodClaim> getClaimsByClaimer(Long claimerId) {
        return foodClaimRepository.findByClaimer_Id(claimerId);
    }

    @Transactional
    public FoodClaim schedulePickup(
            Long claimId,
            LocalDateTime pickupTime) {

        FoodClaim claim = foodClaimRepository.findById(claimId)
                .orElseThrow(() ->
                        new RuntimeException("Claim not found"));

        if (claim.getStatus() != FoodClaim.ClaimStatus.APPROVED) {
            throw new IllegalStateException(
                    "Pickup can only be scheduled for approved claims");
        }

        if (pickupTime.isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException(
                    "Pickup time cannot be in the past");
        }

        claim.setPickupTime(pickupTime);

        return foodClaimRepository.save(claim);
    }

}
