package com.johnwalterstockwell.expedia;

import com.johnwalterstockwell.expedia.models.resource.Hotel;
import com.johnwalterstockwell.expedia.models.resource.Hotels;
import org.apache.commons.math3.ml.distance.EuclideanDistance;

import java.util.*;

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
            OptionalDouble hotel1Optional = hotel1.getRates().stream().mapToDouble(value -> value.getBasePrice()).min();
            OptionalDouble hotel2Optional = hotel2.getRates().stream().mapToDouble(value -> value.getBasePrice()).min();

            Double hotel1Minimum = Double.MAX_VALUE;
            Double hotel2Minimum = Double.MAX_VALUE;

            if (hotel1Optional.isPresent()) {
                hotel1Minimum = hotel1Optional.getAsDouble();
            }

            if (hotel2Optional.isPresent()) {
                hotel2Minimum = hotel2Optional.getAsDouble();
            }

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
            Double hotel1Average = Double.MIN_VALUE;
            Double hotel2Average = Double.MIN_VALUE;

            OptionalDouble hotel1Optional = hotel1.getUserRatings().stream().mapToDouble(value -> value.getRating()).average();
            OptionalDouble hotel2Optional = hotel2.getUserRatings().stream().mapToDouble(value -> value.getRating()).average();

            if (hotel1Optional.isPresent()) {
                hotel1Average = hotel1Optional.getAsDouble();
            }

            if (hotel2Optional.isPresent()) {
                hotel2Average = hotel2Optional.getAsDouble();
            }

            return hotel1Average.compareTo(hotel2Average);
        });

        if (descending) {
            Collections.reverse(sortedHotelList);
        }

        return sortedHotelList;
    }


}