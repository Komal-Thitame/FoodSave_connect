
package com.foodsave.controller;

import com.foodsave.model.FoodClaim;
import com.foodsave.service.FoodClaimService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/claims")
public class FoodClaimController {

    private final FoodClaimService foodClaimService;

    public FoodClaimController(FoodClaimService foodClaimService) {
        this.foodClaimService = foodClaimService;
    }

    // Request body for creating a claim
    public static class ClaimRequest {
        public Long listingId;
        public Long claimerId;
        public String message;
    }

    // Request body for updating claim status
    public static class StatusRequest {
        public String status;
    }

    // Return safe response fields instead of full User/FoodListing objects
    private Map<String, Object> toResponse(FoodClaim claim) {
        Map<String, Object> response = new LinkedHashMap<>();

        response.put("id", claim.getId());
        response.put("listingId", claim.getListing().getId());
        response.put("claimerId", claim.getClaimer().getId());
        response.put("status", claim.getStatus());
        response.put("message", claim.getMessage());
        response.put("pickupTime", claim.getPickupTime());
        response.put("claimedAt", claim.getClaimedAt());
        response.put("updatedAt", claim.getUpdatedAt());

        return response;
    }

    // Create a new food claim
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Map<String, Object> claimFood(
            @RequestBody ClaimRequest request) {

        FoodClaim claim = foodClaimService.claimFood(
                request.listingId,
                request.claimerId,
                request.message
        );

        return toResponse(claim);
    }

    // Get all claims made by a user
    @GetMapping("/user/{claimerId}")
    public List<Map<String, Object>> getClaimsByUser(
            @PathVariable Long claimerId) {

        return foodClaimService.getClaimsByClaimer(claimerId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    // Approve or reject a pending claim
    @PutMapping("/{claimId}/status")
    public Map<String, Object> updateClaimStatus(
            @PathVariable Long claimId,
            @RequestBody StatusRequest request) {

        if (request.status == null || request.status.isBlank()) {
            throw new IllegalArgumentException(
                    "Status must be APPROVED or REJECTED");
        }

        FoodClaim.ClaimStatus status;

        try {
            status = FoodClaim.ClaimStatus.valueOf(
                    request.status.trim().toUpperCase()
            );
        } catch (IllegalArgumentException exception) {
            throw new IllegalArgumentException(
                    "Status must be APPROVED or REJECTED");
        }

        FoodClaim claim = foodClaimService.updateClaimStatus(
                claimId,
                status
        );

        return toResponse(claim);
    }

    @PutMapping("/{claimId}/pickup-time")
    public Map<String, Object> schedulePickup(
            @PathVariable Long claimId,
            @RequestBody PickupTimeRequest request) {

        if (request.pickupTime == null || request.pickupTime.isBlank()) {
            throw new IllegalArgumentException(
                    "Pickup time is required");
        }

        java.time.LocalDateTime pickupTime;

        try {
            pickupTime = java.time.LocalDateTime.parse(
                    request.pickupTime);
        } catch (java.time.format.DateTimeParseException exception) {
            throw new IllegalArgumentException(
                    "Use format yyyy-MM-ddTHH:mm:ss");
        }

        FoodClaim claim = foodClaimService.schedulePickup(
                claimId, pickupTime);

        return toResponse(claim);
    }

    public static class PickupTimeRequest {
        public String pickupTime;
    }

}
