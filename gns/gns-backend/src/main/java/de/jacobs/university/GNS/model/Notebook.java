// Notebook.java

package de.jacobs.university.GNS.model;

import lombok.Data;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "notebooks")
@Where(clause = "is_deleted=0")
@Data
public class Notebook
{
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "note_id")
    private String noteID;

    @Column(name = "description")
    private String description;

    @Lob
    @Column(name = "note")
    private String note;

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "longitude")
    private Double longitude;

    @Column(name = "gns_date")
    private Date recordedDateTime;

    @Column(name = "created_on")
    private Date creationDate;

    @Column(name = "created_by")
    private String createdByUserName;

    @Column(name = "is_deleted")
    private int isDeleted;
}
