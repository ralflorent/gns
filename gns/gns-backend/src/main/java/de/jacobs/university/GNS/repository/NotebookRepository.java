// NotebookRepository.java

package de.jacobs.university.GNS.repository;

import de.jacobs.university.GNS.model.Notebook;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotebookRepository extends JpaRepository<Notebook, Long>
{


}
