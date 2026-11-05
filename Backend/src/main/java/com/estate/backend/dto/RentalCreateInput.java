package com.estate.backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

public class RentalCreateInput {

    @NotBlank
    private String name;

    @NotNull
    private Double surface;

    @NotNull
    private Double price;

    @NotNull
    private MultipartFile picture;

    @NotBlank
    private String description;

    public RentalCreateInput() {
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

    public MultipartFile getPicture() {
        return picture;
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

    public void setPicture(MultipartFile picture) {
        this.picture = picture;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}