// Notebook.java
// Notebook representation in JSON responses

package de.jacobs.university.GNS.response;

import lombok.Data;

import java.util.Date;

@Data
public class Notebook
{
    private Long id;
    private String note_id;
    private String description;
    private String note;
    private Double latitude;
    private Double longitude;
    private Date gns_date;
    private Date created_on;
    private String created_by;

    // Construct from JPA entity
    public static Notebook buildFromEntity(de.jacobs.university.GNS.model.Notebook entity)
    {
        Notebook nb = new Notebook();

        nb.id = entity.getId();
        nb.note_id = entity.getNoteID();
        nb.description = entity.getDescription();
        nb.note = entity.getNote();
        nb.latitude = entity.getLatitude();
        nb.longitude = entity.getLongitude();
        nb.gns_date = entity.getRecordedDateTime();
        nb.created_on = entity.getCreationDate();
        nb.created_by = entity.getCreatedByUserName();

        return nb;
    }
}
