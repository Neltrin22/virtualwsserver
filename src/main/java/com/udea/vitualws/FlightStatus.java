package com.udea.vitualws;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FlightStatus {
    private String flightNumber;
    private double altitude;
    private double speed;
    private String location;

    // Constructor
    public FlightStatus(String flightNumber, double altitude, double speed, String location) {
        this.flightNumber = flightNumber;
        this.altitude = altitude;
        this.speed = speed;
        this.location = location;
    }
}
