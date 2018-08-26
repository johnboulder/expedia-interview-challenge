package com.johnwalterstockwell.expedia;

import com.johnwalterstockwell.expedia.models.resource.Hotel;
import com.johnwalterstockwell.expedia.models.resource.Hotels;
import org.apache.commons.math3.ml.distance.EuclideanDistance;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Responsible for getting a list of hotels and returning it in a specific order based on the requested criteria.
 */
public class HotelListSorter {

    private final EuclideanDistance euclideanDistance;

    public HotelListSorter() {
        euclideanDistance = new EuclideanDistance();
    }

    public List<Hotel> getHotelsByDistanceFromCenter(final Hotels hotels, final Boolean descending) {
        final double[] originVector = new double[3];
        originVector[0] = hotels.getLocationX();
        originVector[1] = hotels.getLocationY();
        originVector[2] = hotels.getLocationZ();
        final List<Hotel> sortedHotelList = hotels.getHotels();

        sortedHotelList.sort((hotel1, hotel2) -> {
            final double[] hotel1Vector = new double[3];
            hotel1Vector[0] = hotel1.getLocationX();
            hotel1Vector[1] = hotel1.getLocationY();
            hotel1Vector[2] = hotel1.getLocationZ();

            final double[] hotel2Vector = new double[3];
            hotel2Vector[0] = hotel2.getLocationX();
            hotel2Vector[1] = hotel2.getLocationY();
            hotel2Vector[2] = hotel2.getLocationZ();

            Double hotel1Distance = euclideanDistance.compute(originVector, hotel1Vector);
            Double hotel2Distance = euclideanDistance.compute(originVector, hotel2Vector);

            return hotel1Distance.compareTo(hotel2Distance);
        });

        if (descending) {
            Collections.reverse(sortedHotelList);
        }

        return sortedHotelList;
    }

    public List<Hotel> getHotelsByLowestPrice(final Hotels hotels, final Boolean descending) {
        final List<Hotel> sortedHotelList = hotels.getHotels();

        sortedHotelList.sort((hotel1, hotel2) -> {
            Double hotel1Minimum = hotel1.getRates().stream().mapToDouble(value -> value.getBasePrice()).min().getAsDouble();
            Double hotel2Minimum = hotel2.getRates().stream().mapToDouble(value -> value.getBasePrice()).min().getAsDouble();
            return hotel1Minimum.compareTo(hotel2Minimum);
        });

        if (descending) {
            Collections.reverse(sortedHotelList);
        }

        return sortedHotelList;
    }

    public List<Hotel> getHotelsByAverageUserRating(final Hotels hotels, final Boolean descending) {

        final List<Hotel> sortedHotelList = hotels.getHotels();

        sortedHotelList.sort((hotel1, hotel2) -> {
            Double hotel1Average = hotel1.getUserRatings().stream().mapToDouble(value -> value.getRating()).average().getAsDouble();
            Double hotel2Average = hotel2.getUserRatings().stream().mapToDouble(value -> value.getRating()).average().getAsDouble();
            return hotel1Average.compareTo(hotel2Average);
        });

        if (descending) {
            Collections.reverse(sortedHotelList);
        }

        return sortedHotelList;
    }


}