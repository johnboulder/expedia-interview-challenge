package com.johnwalterstockwell.expedia.models.resource;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class Rate {

    @SerializedName("base_price")
    @Expose
    private Double basePrice;
    @SerializedName("room_id")
    @Expose
    private String roomId;
    @SerializedName("name")
    @Expose
    private String name;
}