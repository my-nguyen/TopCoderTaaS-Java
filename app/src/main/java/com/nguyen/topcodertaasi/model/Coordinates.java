package com.nguyen.topcodertaasi.model;

import android.content.Context;

import com.google.android.gms.maps.model.LatLng;
import com.opencsv.CSVReader;

import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Coordinates {
    private static Coordinates instance = null;
    private List<Coordinate> list;

    public static Coordinates getInstance(Context context, String filename) {
        if (instance == null) {
            instance = new Coordinates(context, filename);
        }
        return instance;
    }

    // creates a list of Coordinates from a CSV file located in the assets folder
    private Coordinates(Context context, String filename) {
        list = new ArrayList();
        try {
            InputStreamReader stream = new InputStreamReader(context.getAssets().open(filename));
            CSVReader reader = new CSVReader(stream);
            String[] nextLine;
            while ((nextLine = reader.readNext()) != null) {
                list.add(new Coordinate(nextLine));
            }
            stream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // search in the list of coordinates for the country whose abbreviation matches the parameter
    private int search(String abbreviation) {
        int left = 0, right = list.size() - 1;
        while (left <= right) {
            int middle = left + (right - left) / 2;
            if (list.get(middle).abbreviation.compareTo(abbreviation) == 0) {
                return middle;
            } else if (list.get(middle).abbreviation.compareTo(abbreviation) < 0) {
                left = middle + 1;
            } else {
                right = middle - 1;
            }
        }
        return -1;
    }

    // converts a country abbreviation to a latitude-longitude pair
    public LatLng toLatLng(String country) {
        int index = search(country);
        if (index != -1) {
            Coordinate coordinate = list.get(index);
            return new LatLng(coordinate.latitude, coordinate.longitude);
        } else {
            return null;
        }
    }
}
