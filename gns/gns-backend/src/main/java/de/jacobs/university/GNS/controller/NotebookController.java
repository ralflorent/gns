/**
 * NotebookController.java
 * Main controller that processes requests for field notebook functionality
 */

package de.jacobs.university.GNS.controller;

import de.jacobs.university.GNS.model.Notebook;
import de.jacobs.university.GNS.repository.NotebookRepository;
import de.jacobs.university.GNS.response.NotebookCreateResponse;
import de.jacobs.university.GNS.response.NotebookListResponse;
import de.jacobs.university.GNS.response.NotebookUpdateResponse;
import de.jacobs.university.GNS.service.NotebookSaveResult;
import de.jacobs.university.GNS.service.NotebookService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class NotebookController
{
    private NotebookService notebookService;

    // Constructor
    @Autowired
    public NotebookController(NotebookRepository repo, NotebookService notebookService) {
        this.notebookService = notebookService;
    }

    // Return all existing notebooks
    @RequestMapping(value = "notes/list", method = RequestMethod.GET)
    public NotebookListResponse getNotebooks()
    {
        List<Notebook> entities = notebookService.getAllNotebooks();
        NotebookListResponse response = new NotebookListResponse(entities);

        return response;
    }

    // Create a new notebook
    @RequestMapping(value = "notes/add", method = RequestMethod.POST)
    public NotebookCreateResponse addNotebook(@RequestBody de.jacobs.university.GNS.request.Notebook notebook)
    {
        // check for required fields in JSON
        if (notebook.getNote() == null || notebook.getNote().isEmpty()) {
            return NotebookCreateResponse.failResponse("Field 'note' is required");
        }
        if (notebook.getCreated_by() == null || notebook.getCreated_by().isEmpty()) {
            return NotebookCreateResponse.failResponse("Field 'created_by' is required");
        }
        if (notebook.getDescription() == null) {
            notebook.setDescription("");
        }

        // create new notebook
        NotebookSaveResult result = notebookService.addNotebook(notebook.getCreated_by(), notebook.getDescription(), notebook.getNote());
        Notebook newNotebook = result.getNewNotebook();

        if (newNotebook != null) {
            return NotebookCreateResponse.successResponse(newNotebook.getId(), newNotebook.getNoteID());
        }
        else
        {
            // there was an error in saving or getting GPS data
            switch (result.getError())
            {
                case FAILED_TO_SAVE_TO_DB:
                    return NotebookCreateResponse.failResponse("Failed to save the notebook to the database");

                case GPS_FAILED_TO_GET_TIME:
                    return NotebookCreateResponse.failResponse("Failed to get date and time from GPS");

                case GPS_FAILED_TO_GET_LOCATION:
                    return NotebookCreateResponse.failResponse("Failed to get location from GPS");
            }

            return NotebookCreateResponse.failResponse("Unknown error occurred");
        }
    }

    // Update an existing notebook
    @RequestMapping(value = "notes/update", method = RequestMethod.POST)
    public NotebookUpdateResponse updateNotebook(@RequestBody de.jacobs.university.GNS.request.Notebook notebook)
    {
        boolean success = notebookService.updateNotebook(notebook.getId(), notebook.getNote(), notebook.getDescription());
        if (success) {
            return NotebookUpdateResponse.successResponse("Notebook was updated successfully");
        }
        else {
            return NotebookUpdateResponse.failResponse("Invalid id for notebook");
        }
    }

    // Delete (soft delete) a notebook
    @RequestMapping(value = "notes/delete", method = RequestMethod.DELETE)
    public NotebookUpdateResponse deleteNotebook(@RequestBody de.jacobs.university.GNS.request.Notebook notebook)
    {
        if (notebookService.softDeleteNotebook(notebook.getId())) {
            return NotebookUpdateResponse.successResponse("Notebook was deleted successfully");
        }
        else {
            return NotebookUpdateResponse.failResponse("Failed to delete notebook. Invalid ID.");
        }
    }
}
