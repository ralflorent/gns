/**
 * NotebookController.java
 * Main controller that processes requests for field notebook functionality
 */

package de.jacobs.university.GNS.controller;

import de.jacobs.university.GNS.model.Notebook;

import de.jacobs.university.GNS.repository.NotebookRepository;
import de.jacobs.university.GNS.response.*;

import de.jacobs.university.GNS.service.GPSService;
import de.jacobs.university.GNS.service.NotebookSaveResult;
import de.jacobs.university.GNS.service.NotebookService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:80", "http://localhost:4200"})
public class NotebookController
{
    private NotebookService notebookService;
    private GPSService gpsService;

    // Constructor
    @Autowired
    public NotebookController(NotebookRepository repo, NotebookService notebookService, GPSService gpsService) {
        this.notebookService = notebookService;
        this.gpsService = gpsService;
    }

    // Return all existing notebooks
    @RequestMapping(value = "api/v1/notes/list", method = RequestMethod.GET)
    public NotebookListResponse getNotebooks()
    {
        List<Notebook> entities = notebookService.getAllNotebooks();
        NotebookListResponse response = new NotebookListResponse(entities);

        return response;
    }

    // Returns information on one existing notebook
    @RequestMapping(value = "api/v1/notes/detail", method = RequestMethod.GET)
    public Response getNotebookDetail(@RequestParam Long id)
    {
        Notebook nb = notebookService.getNotebook(id);
        if (nb != null) {
            return Response.successResponse("Fetched notebook with ID: " + id.toString(), de.jacobs.university.GNS.response.Notebook.buildFromEntity(nb));
        }
        else {
            return Response.failureResponse("Failed to fetch notebook. Invalid ID: " + id.toString());
        }
    }

    // Create a new notebook
    @RequestMapping(value = "api/v1/notes/add", method = RequestMethod.POST)
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

    // Get the details for the next notebook that is about to be created
    @RequestMapping(value = "api/v1/notes/add", method = RequestMethod.GET)
    public Response getNotebookPrecreationInfo()
    {
        // get date/time and gps location
        GPSService.GPSLocation location = gpsService.getGPSLocation();
        LocalDateTime dateTime = gpsService.getDateTime();

        if (location == null || dateTime == null) {
            return Response.failureResponse("Failed to fetch location and date from GPS");
        }
        else
        {
            String nextID = notebookService.getNextValidNotebookID();
            NotebookPreCreateResponse data = NotebookPreCreateResponse.build(nextID, location.getLatitude(), location.getLongitude(), dateTime);
            return Response.successResponse("Fetched notebook successfully", data);
        }
    }

    // Update an existing notebook
    @RequestMapping(value = "api/v1/notes/update", method = RequestMethod.POST)
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
    @RequestMapping(value = "api/v1/notes/delete", method = RequestMethod.DELETE)
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
