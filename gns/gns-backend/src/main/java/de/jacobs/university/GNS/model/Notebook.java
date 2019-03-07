// Notebook.java

package de.jacobs.university.GNS.model;

import lombok.Data;
import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "notebook")
@Data
public class Notebook
{
    @Id
    @Column(name = "notebook_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "creation_date")
    private Date creationDate;
}
