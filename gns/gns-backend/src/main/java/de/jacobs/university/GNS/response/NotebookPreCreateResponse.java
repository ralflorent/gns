// NotebookPreCreateResponse.java
// The response for pre-creating a notebook with info on the id and lat,lon

package de.jacobs.university.GNS.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class NotebookPreCreateResponse
{
    private String note_id;
    private LocalDateTime dateTime;
    private Double latitude;
    private Double longitude;

    public static NotebookPreCreateResponse build(String nextNoteID, Double latitude, Double longitude, LocalDateTime dateTime)
    {
        NotebookPreCreateResponse response = new NotebookPreCreateResponse();

        response.setNote_id(nextNoteID);
        response.setLatitude(latitude);
        response.setLongitude(longitude);
        response.setDateTime(dateTime);

        return response;
    }
}
