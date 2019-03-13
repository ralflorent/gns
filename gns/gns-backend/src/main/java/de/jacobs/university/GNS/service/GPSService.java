// GPSService.java
// Service for GPS related functionality

package de.jacobs.university.GNS.service;

import lombok.Data;

import com.ivkos.gpsd4j.client.GpsdClient;
import com.ivkos.gpsd4j.client.GpsdClientOptions;
import com.ivkos.gpsd4j.messages.reports.TPVReport;
import com.ivkos.gpsd4j.messages.DeviceMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


@Service
public class GPSService
{
    private static int connectionTimeout;
    private static int gpsdPort;
    private static String gpsdDevice;

    private static final int IDLE_TIMEOUT = 30;
    private static final int RECONNECT_ATTEMPTS = 5;
    private static final int RECONNECT_INTERVAL = 3000;

    private static GpsdClient gpsdClient;
    private static GPSLocation lastLocation;
    private static LocalDateTime lastDateTime;

    private static GPSLogger logger;

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
    @Autowired
    public GPSService(@Value("${gpsd.port}") int gpsdPort, @Value("${gpsd.device}") String gpsdDevice, @Value("${gpsd.connection.timeout}") int connectionTimeout, GPSLogger logger) {
        GPSService.logger = logger;
        GPSService.gpsdDevice = gpsdDevice;
        GPSService.gpsdPort = gpsdPort;
        GPSService.connectionTimeout = connectionTimeout;
        GPSService.initGPSDClient();
    }

    // Get the current GPS location
    public GPSLocation getGPSLocation() {
        return GPSService.lastLocation;
    }

    // Get the current date time
    public LocalDateTime getDateTime() {
        return GPSService.lastDateTime;
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

        GPSService.gpsdClient = new GpsdClient("localhost", GPSService.gpsdPort, options)
                .addErrorHandler(System.err::println)
                .addHandler(TPVReport.class, tpv -> {
                    // time
                    GPSService.lastDateTime = tpv.getTime();
                    //System.out.println("Time: " + GPSService.lastDateTime.toString());

                    // gps location
                    GPSService.lastLocation = new GPSLocation(tpv.getLatitude(), tpv.getLongitude());
                    //System.out.printf("Lat: %f, Lon: %f\n", lastLocation.latitude, lastLocation.longitude);

                    // log date/time and location to csv file
                    if (lastLocation != null && lastDateTime != null) {
                        logger.addLogEntry(lastLocation, lastDateTime);
                    }
                })
                .setSuccessfulConnectionHandler(client -> {
                    DeviceMessage device = new DeviceMessage();
                    device.setPath(GPSService.gpsdDevice);
                    device.setNative(true);

                    client.sendCommand(device);
                    client.watch();
                });
        GPSService.gpsdClient.start();
    }
}
