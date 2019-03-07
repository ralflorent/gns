// NoteRepository.java

package de.jacobs.university.GNS.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import de.jacobs.university.GNS.model.Note;

public interface NoteRepository extends JpaRepository<Note, Long>
{

}
