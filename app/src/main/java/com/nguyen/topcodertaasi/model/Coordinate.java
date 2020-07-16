package com.nguyen.topcodertaasi.model;

public class Coordinate {
    String abbreviation;
    Double latitude;
    Double longitude;

    public Coordinate(String[] fields) {
        abbreviation = fields[0];
        latitude = Double.parseDouble(fields[1]);
        longitude = Double.parseDouble(fields[2]);
    }
}
