package com.johnwalterstockwell.expedia.models.resource;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class Hotels {

    @SerializedName("location_id")
    @Expose
    private Integer locationId;
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
    @SerializedName("taxes")
    @Expose
    private List<Tax> taxes = null;
    @SerializedName("hotels")
    @Expose
    private List<Hotel> hotels = null;
}