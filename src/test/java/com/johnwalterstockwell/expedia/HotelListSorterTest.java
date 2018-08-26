package com.johnwalterstockwell.expedia;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.johnwalterstockwell.expedia.models.resource.Hotel;
import com.johnwalterstockwell.expedia.models.resource.Hotels;
import org.apache.commons.math3.ml.distance.EuclideanDistance;
import org.junit.Before;
import org.junit.Test;

import java.io.FileReader;
import java.net.URL;
import java.util.Iterator;
import java.util.List;

import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.fail;

public class HotelListSorterTest {

    private final String HOTELS_RESOURCE_FILE_NAME = "hotels.json";
    private HotelListSorter hotelListSorter;
    private Hotels hotels;
    private FileReader fileReader;
    private Gson gson;
    private EuclideanDistance euclideanDistance;


    @Before
    public void before() {
        euclideanDistance = new EuclideanDistance();
        hotelListSorter = new HotelListSorter();
        gson = new GsonBuilder().create();

        try {
            URL url = getClass().getClassLoader().getResource(HOTELS_RESOURCE_FILE_NAME);
            fileReader = new FileReader(url.getFile());
        } catch (Exception e) {
            e.printStackTrace();
            fail("Exception thrown while reading test resource file.");
        }

        hotels = gson.fromJson(fileReader, Hotels.class);
    }

    @Test
    public void getHotelsByDistanceFromCenterDescending() {
        List<Hotel> hotelList = hotelListSorter.getHotelsByDistanceFromCenter(hotels, true);
        assertNotNull(hotelList);

        final double[] originVector = new double[3];
        originVector[0] = hotels.getLocationX();
        originVector[1] = hotels.getLocationY();
        originVector[2] = hotels.getLocationZ();

        // Assert the list is in descending order
        Iterator<Hotel> hotelIterator = hotelList.iterator();
        Hotel currentHotel = hotelIterator.next();
        while(hotelIterator.hasNext())
        {
            final double[] currentHotelVector = new double[3];
            currentHotelVector[0] = currentHotel.getLocationX();
            currentHotelVector[1] = currentHotel.getLocationY();
            currentHotelVector[2] = currentHotel.getLocationZ();

            Hotel nextHotel = hotelIterator.next();
            final double[] nextHotelVector = new double[3];
            nextHotelVector[0] = nextHotel.getLocationX();
            nextHotelVector[1] = nextHotel.getLocationY();
            nextHotelVector[2] = nextHotel.getLocationZ();

            double currentHotelDistance = euclideanDistance.compute(originVector, currentHotelVector);
            double nextHotelDistance = euclideanDistance.compute(originVector, nextHotelVector);

            if(currentHotelDistance < nextHotelDistance )
            {
                fail("List not returned in descending order.");
            }

            currentHotel = nextHotel;
        }
    }

    @Test
    public void getHotelsByLowestPriceDescending() {
        List<Hotel> hotelList = hotelListSorter.getHotelsByLowestPrice(hotels, true);
        assertNotNull(hotelList);

        // Assert the list is in descending order
        Iterator<Hotel> hotelIterator = hotelList.iterator();
        Hotel currentHotel = hotelIterator.next();
        while(hotelIterator.hasNext())
        {
            Double currentMinimum = currentHotel.getRates().stream().mapToDouble(value -> value.getBasePrice()).min().getAsDouble();
            Hotel nextHotel = hotelIterator.next();
            Double nextMinimum = nextHotel.getRates().stream().mapToDouble(value -> value.getBasePrice()).min().getAsDouble();

            if(currentMinimum < nextMinimum )
            {
                fail("List not returned in descending order.");
            }

            currentHotel = nextHotel;
        }
    }

    @Test
    public void getHotelsByAverageUserRatingDescending() {
        List<Hotel> hotelList = hotelListSorter.getHotelsByAverageUserRating(hotels, true);
        assertNotNull(hotelList);

        // Assert the list is in descending order
        Iterator<Hotel> hotelIterator = hotelList.iterator();
        Hotel currentHotel = hotelIterator.next();
        while(hotelIterator.hasNext())
        {
            Double currentAverage = currentHotel.getUserRatings().stream().mapToDouble(value -> value.getRating()).average().getAsDouble();
            Hotel nextHotel = hotelIterator.next();
            Double nextAverate = nextHotel.getUserRatings().stream().mapToDouble(value -> value.getRating()).average().getAsDouble();

            if(currentAverage < nextAverate )
            {
                fail("List not returned in descending order.");
            }

            currentHotel = nextHotel;
        }
    }
}