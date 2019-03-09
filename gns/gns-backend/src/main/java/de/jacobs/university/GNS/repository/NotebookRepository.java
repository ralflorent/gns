// NotebookRepository.java

package de.jacobs.university.GNS.repository;

import de.jacobs.university.GNS.model.Notebook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface NotebookRepository extends JpaRepository<Notebook, Long>
{
    // Custom find all using soft delete field
    @Override
    @Query("SELECT e FROM #{#entityName} e WHERE e.is_deleted=0")
    List<Notebook> findAll();

    // Get notebooks that have been soft deleted
    @Query("SELECT e FROM #{#entityName} e WHERE e.is_deleted=1")
    List<Notebook> deletedNotebooks();

    // Soft delete a notebook
    @Query("UPDATE #{#entityName} e set e.is_deleted=1 where e.id=?1")
    void softDelete(Long id);
}
