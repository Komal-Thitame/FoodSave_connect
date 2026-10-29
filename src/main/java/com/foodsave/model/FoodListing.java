
package com.foodsave.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "food_listings")
public class FoodListing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String category;
    private String description;

    @Column(name = "donor_id", nullable = false)
    private Long donorId;

    @Column(name = "donor_name")
    private String donorName;

    @Column(name = "food_name")
    private String foodName;

    private Integer quantity;
    private String unit;

    @Column(name = "quantity_unit")
    private String quantityUnit;

    private String location;

    @Column(name = "pickup_address")
    private String pickupAddress;

    @Column(name = "prepared_at")
    private LocalDateTime preparedAt;

    @Column(name = "pickup_deadline")
    private LocalDateTime pickupDeadline;

    @Column(name = "expiry_date")
    private LocalDate expiryDate;

    @Column(name = "freshness_status", nullable = false)
    private String freshnessStatus;

    @Column(name = "image_url")
    private String imageUrl;

    private String status;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public FoodListing() {
    }

    @PrePersist
    public void beforeSave() {
        if (title == null || title.isBlank()) {
            title = foodName;
        }
        if (foodName == null || foodName.isBlank()) {
            foodName = title;
        }
        if (freshnessStatus == null) {
            freshnessStatus = "FRESH";
        }
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
        if (updatedAt == null) {
            updatedAt = LocalDateTime.now();
        }
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Long getDonorId() { return donorId; }
    public void setDonorId(Long donorId) { this.donorId = donorId; }

    public String getDonorName() { return donorName; }
    public void setDonorName(String donorName) { this.donorName = donorName; }

    public String getFoodName() { return foodName; }
    public void setFoodName(String foodName) { this.foodName = foodName; }

    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }

    public String getUnit() { return unit; }
    public void setUnit(String unit) { this.unit = unit; }

    public String getQuantityUnit() { return quantityUnit; }
    public void setQuantityUnit(String quantityUnit) { this.quantityUnit = quantityUnit; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public String getPickupAddress() { return pickupAddress; }
    public void setPickupAddress(String pickupAddress) { this.pickupAddress = pickupAddress; }

    public LocalDateTime getPreparedAt() { return preparedAt; }
    public void setPreparedAt(LocalDateTime preparedAt) { this.preparedAt = preparedAt; }

    public LocalDateTime getPickupDeadline() { return pickupDeadline; }
    public void setPickupDeadline(LocalDateTime pickupDeadline) { this.pickupDeadline = pickupDeadline; }

    public LocalDate getExpiryDate() { return expiryDate; }
    public void setExpiryDate(LocalDate expiryDate) { this.expiryDate = expiryDate; }

    public String getFreshnessStatus() { return freshnessStatus; }
    public void setFreshnessStatus(String freshnessStatus) { this.freshnessStatus = freshnessStatus; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
