// GPSLogger.java
// GPS date time and location logging service

package de.jacobs.university.GNS.service;

import lombok.Data;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.LinkedList;

@Service
public class GPSLogger
{
    // Internal structure for a log item in queue
    @Data
    private class LogItem {
        private GPSService.GPSLocation location;
        private LocalDateTime dateTime;
        public LogItem(GPSService.GPSLocation location, LocalDateTime dateTime) {
            this.location = location;
            this.dateTime = dateTime;
        }
    }

    private static final String[] CSV_HEADERS = {"Latitude", "Longitude", "Date", "Time", "UTC"};

    private LinkedList<LogItem> items = new LinkedList<>();

    // Log the given gps location and date/time
    public void addLogEntry(GPSService.GPSLocation location, LocalDateTime dateTime)
    {
        // add to queue
        items.addLast(new LogItem(location, dateTime));

        // setup paths
        String userDirPath = System.getProperty("user.dir") + "/gps_logs/";

        // create folder if it does not exist
        try {
            if (!Files.exists(Paths.get(userDirPath))) {
                Files.createDirectory(Paths.get(userDirPath));
            }
        }
        catch (IOException ex) {
            System.err.println("Failed to create directory for GPS logs");
            System.err.println(ex.getLocalizedMessage());
            return;
        }

        String filename = getFilenameForToday();
        String filepath = userDirPath + filename;

        Path path = Paths.get(filepath);

        if (Files.exists(path)) {
            LogItem item = items.getFirst();
            items.removeFirst();
            appendEntry(item, path);
        }
        else {
            // create new file and append entry
            createLogFile(path);
        }
    }

    // Create the logging file if it does not exist
    // Writes the log file to user/home/logs/gns/ as csv files named by date YYYY_MM_DD.csv
    private void createLogFile(Path filepath)
    {
        // create new csv file with required headers
        try(BufferedWriter writer = Files.newBufferedWriter(filepath, Charset.forName("UTF-8")))
        {
            for (int i = 0; i < CSV_HEADERS.length; i++)
            {
                writer.write(CSV_HEADERS[i]);
                if (i != (CSV_HEADERS.length - 1)) {
                    writer.write(",");
                }
            }
            writer.write("\n");

            System.out.println("Log file created at: " + filepath.toString());
        }
        catch (IOException ex) {
            System.err.println(ex.getLocalizedMessage());
        }
    }

    // Appends the given entry to the log file
    private void appendEntry(LogItem item, Path filepath)
    {
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(filepath.toFile(), true)))
        {
            // extract date
            LocalDateTime dateTime = item.getDateTime();
            String date = String.format("%d-%02d-%02d", dateTime.getYear(), dateTime.getMonthValue(), dateTime.getDayOfMonth());

            // extract time and make sure 00 is included in the string
            String hrs = dateTime.getHour() == 0 ? "00" : String.format("%02d", dateTime.getHour());
            String mins = dateTime.getMinute() == 0 ? "00" : String.format("%02d", dateTime.getMinute());
            String secs = dateTime.getSecond() == 0 ? "00" : String.format("%02d", dateTime.getSecond());
            String time = String.format("%s:%s:%s", hrs, mins, secs);

            // latitude
            writer.write(item.location.getLatitude().toString() + ",");

            // longitude
            writer.write(item.location.getLongitude().toString() + ",");

            // date
            writer.write(date + ",");

            // time
            writer.write(time + ",");

            // UTC string
            writer.write(item.dateTime.toString() + "\n");
        }
        catch (IOException ex) {
            System.err.println(ex.getLocalizedMessage());
        }
    }

    // Returns the filename for today
    private String getFilenameForToday()
    {
        LocalDateTime now = LocalDateTime.now();

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(now.getYear()).append(("_"));
        stringBuilder.append(String.format("%02d", now.getMonthValue())).append("_");
        stringBuilder.append(String.format("%02d", now.getDayOfMonth())).append(".csv");

        return stringBuilder.toString();
    }
}
