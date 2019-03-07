// NoteRepository.java

package de.jacobs.university.GNS.model;

import lombok.Data;
import javax.persistence.*;

@Entity
@Table(name = "note")
@Data
public class Note
{
    @Id
    @Column(name = "note_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String text;
}
