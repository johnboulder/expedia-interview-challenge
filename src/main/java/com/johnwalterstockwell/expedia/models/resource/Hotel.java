package com.johnwalterstockwell.expedia.models.resource;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class Hotel {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("location_x")
    @Expose
    private Double locationX;
    @SerializedName("location_y")
    @Expose
    private Double locationY;
    @SerializedName("location_z")
    @Expose
    private Double locationZ;
    @SerializedName("rates")
    @Expose
    private List<Rate> rates = null;
    @SerializedName("star_rating")
    @Expose
    private Integer starRating;
    @SerializedName("user_ratings")
    @Expose
    private List<UserRating> userRatings = null;
}