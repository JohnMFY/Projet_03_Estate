package com.estate.backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class RentalUpdateInput {

    @NotBlank
    private String name;

    @NotNull
    private Double surface;

    @NotNull
    private Double price;

    @NotBlank
    private String description;

    public RentalUpdateInput() {
    }

    public String getName() {
        return name;
    }

    public Double getSurface() {
        return surface;
    }

    public Double getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurface(Double surface) {
        this.surface = surface;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}