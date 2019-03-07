// GPSController.java
// Controller responsible for testing
package de.jacobs.university.GNS.controller;

import de.jacobs.university.GNS.service.GPSService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GPSController
{
    private GPSService service;

    @Autowired
    public GPSController(GPSService gpsService)
    {
        this.service = gpsService;
    }

    // Get the current GPS location
    @RequestMapping(value = "gps/current_location", method = RequestMethod.GET)
    public GPSService.GPSLocation getGPSLocation()
    {
        return service.getGPSLocation();
    }
}
