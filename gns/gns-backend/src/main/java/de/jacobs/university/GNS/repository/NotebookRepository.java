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
    @Query(value = "SELECT * FROM notebooks WHERE is_deleted=0", nativeQuery = true)
    List<Notebook> findAll();

    // Get all notebooks including soft deleted notebooks
    @Query(value = "SELECT * FROM notebooks", nativeQuery = true)
    List<Notebook> findAllIncludingDeleted();

    // Get notebooks that have been soft deleted
    @Query(value = "SELECT * FROM notebooks WHERE is_deleted=1", nativeQuery = true)
    List<Notebook> deletedNotebooks();

    // Soft delete a notebook
    @Query(value = "UPDATE notebooks SET is_deleted=1 WHERE id=?1", nativeQuery = true)
    boolean softDelete(Long id);

    // Get last record
    Notebook findTopByOrderByIdDesc();

    // Search by description
    @Query(value = "SELECT * FROM notebooks WHERE description LIKE %?1%", nativeQuery = true)
    List<Notebook> searchByDescription(String query);
}
