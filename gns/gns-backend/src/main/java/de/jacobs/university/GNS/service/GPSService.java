// GPSService.java
// Service for GPS related functionality

package de.jacobs.university.GNS.service;

import lombok.Data;

import com.ivkos.gpsd4j.client.GpsdClient;
import com.ivkos.gpsd4j.client.GpsdClientOptions;
import com.ivkos.gpsd4j.messages.reports.TPVReport;
import com.ivkos.gpsd4j.messages.DeviceMessage;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


@Service
public class GPSService
{
    @Value("${gpsd.connection.timeout}")
    private static int connectionTimeout;

    @Value("${gpsd.port}")
    private static int gpsdPort;

    private static final int IDLE_TIMEOUT = 30;
    private static final int RECONNECT_ATTEMPTS = 5;
    private static final int RECONNECT_INTERVAL = 3000;

    private static GpsdClient gpsdClient;
    private static GPSLocation lastLocation;
    private static LocalDateTime lastDateTime;

    // Internal class for representation of lat,long
    @Data
    public static class GPSLocation
    {
        private Double latitude;
        private Double longitude;

        public GPSLocation(Double latitude, Double longitude) {
            this.latitude = latitude;
            this.longitude = longitude;
        }
    }

    // Constructor to create service
    public GPSService() {
        GPSService.initGPSDClient();
        System.out.println("Client Setup");
    }

    // Get the current GPS location
    public GPSLocation getGPSLocation() {
        return GPSService.lastLocation;
    }

    // Creates and returns a GPSD client
    private static void initGPSDClient()
    {
        GpsdClientOptions options = new GpsdClientOptions();

        options.setConnectTimeout(connectionTimeout);
        options.setReconnectOnDisconnect(true);
        options.setIdleTimeout(IDLE_TIMEOUT);
        options.setReconnectAttempts(RECONNECT_ATTEMPTS);
        options.setReconnectInterval(RECONNECT_INTERVAL);

        int port = GPSService.gpsdPort;

        GPSService.gpsdClient = new GpsdClient("localhost", 2947, options)
                .addErrorHandler(System.err::println)
                .addHandler(TPVReport.class, tpv -> {
                    // time
                    GPSService.lastDateTime = tpv.getTime();
                    System.out.println("Time: " + GPSService.lastDateTime.toString());

                    // gps location
                    GPSService.lastLocation = new GPSLocation(tpv.getLatitude(), tpv.getLongitude());
                    System.out.printf("Lat: %f, Lon: %f\n", lastLocation.latitude, lastLocation.longitude);
                })
                .setSuccessfulConnectionHandler(client -> {
                    DeviceMessage device = new DeviceMessage();
                    device.setPath("/dev/ttyUSB0");
                    device.setNative(true);

                    client.sendCommand(device);
                    client.watch();
                });
        GPSService.gpsdClient.start();
    }
}
