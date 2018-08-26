package com.johnwalterstockwell.expedia.models.resource;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class UserRating {

    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("rating")
    @Expose
    private Integer rating;
}