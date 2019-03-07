// GPSService.java
// Service for GPS related functionality

package de.jacobs.university.GNS.service;

import lombok.Data;

import com.ivkos.gpsd4j.client.GpsdClient;
import com.ivkos.gpsd4j.client.GpsdClientOptions;
import com.ivkos.gpsd4j.messages.PollMessage;
import com.ivkos.gpsd4j.messages.reports.TPVReport;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;


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

    // Internal class for representation of lat,long
    @Data
    public class GPSLocation
    {
        private Double latitude;
        private Double longitude;

        public GPSLocation(Double latitude, Double longitude) {
            this.latitude = latitude;
            this.longitude = longitude;
        }
    }

    // Get the current GPS location
    // TODO: Need to implement callback like Swift somehow...
    public GPSLocation getGPSLocation()
    {
        GpsdClient client = GPSService.getGPSDClient();
        client.start();

        client.addErrorHandler(errorMessage -> { System.err.println("ERROR GPSD: " + errorMessage.getMessage());});

        final GPSLocation[] gpsLocation = new GPSLocation[1];

        client.sendCommand(new PollMessage(), pollMessageResponse -> {
           List<TPVReport> tpvList = pollMessageResponse.getTPVList();
           if (!tpvList.isEmpty())
           {
               TPVReport tpv = tpvList.get(0);

               Double lat = tpv.getLatitude();
               Double lon = tpv.getLongitude();
               gpsLocation[0] = new GPSLocation(lat, lon);
           }
        });

        // wait for some response from client
        while(gpsLocation[0] == null) {
            // wait ...
        }

        return gpsLocation[0];
    }

    // Creates and returns a GPSD client
    private static GpsdClient getGPSDClient()
    {
        GpsdClientOptions options = new GpsdClientOptions();

        options.setConnectTimeout(connectionTimeout);
        options.setReconnectOnDisconnect(true);
        options.setIdleTimeout(IDLE_TIMEOUT);
        options.setReconnectAttempts(RECONNECT_ATTEMPTS);
        options.setReconnectInterval(RECONNECT_INTERVAL);

        return new GpsdClient("localhost", gpsdPort, options);
    }
}
