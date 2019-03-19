/**
 * NotebookController.java
 * Main controller that processes requests for field notebook functionality
 */

package de.jacobs.university.GNS.controller;

import de.jacobs.university.GNS.model.Notebook;

import de.jacobs.university.GNS.repository.NotebookRepository;

import de.jacobs.university.GNS.response.NotebookPreCreateResponse;
import de.jacobs.university.GNS.response.Response;

import de.jacobs.university.GNS.service.GPSService;
import de.jacobs.university.GNS.service.NotebookSaveResult;
import de.jacobs.university.GNS.service.NotebookService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
    public Response getNotebooks()
    {
        List<Notebook> entities = notebookService.getAllNotebooks();
        List<de.jacobs.university.GNS.response.Notebook> notebooks = new ArrayList<>();

        for (Notebook entity : entities) {
            notebooks.add(de.jacobs.university.GNS.response.Notebook.buildFromEntity(entity));
        }

        return Response.successResponse("Notebook List", notebooks);
    }

    // Search notebook by description
    @RequestMapping(value = "api/v1/notes/search", method = RequestMethod.GET)
    public Response searchNotebooks(@RequestParam(name = "q") String query)
    {
        List<Notebook> resultList = notebookService.searchNotebooks(query);
        List<de.jacobs.university.GNS.response.Notebook> notebooks = new ArrayList<>();

        for (Notebook entity : resultList) {
            notebooks.add(de.jacobs.university.GNS.response.Notebook.buildFromEntity(entity));
        }

        return Response.successResponse("Query returned: " + notebooks.size() + " result(s)", notebooks);
    }

    // Returns information on one existing notebook
    @RequestMapping(value = "api/v1/notes/{id}/details", method = RequestMethod.GET)
    public Response getNotebookDetail(@PathVariable Long id)
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
    public Response addNotebook(@RequestBody de.jacobs.university.GNS.request.Notebook notebook)
    {
        // check for required fields in JSON
        if (notebook.getNote() == null || notebook.getNote().isEmpty()) {
            return Response.failureResponse("Field 'note' is required");
        }
        if (notebook.getCreated_by() == null || notebook.getCreated_by().isEmpty()) {
            return Response.failureResponse("Field 'created_by' is required");
        }
        if (notebook.getDescription() == null) {
            notebook.setDescription("");
        }

        // create new notebook
        NotebookSaveResult result = notebookService.addNotebook(notebook.getCreated_by(), notebook.getDescription(), notebook.getNote());
        Notebook newNotebook = result.getNewNotebook();

        if (newNotebook != null) {
            return Response.successResponse("Notebook created successfully", de.jacobs.university.GNS.response.Notebook.buildFromEntity(newNotebook));
        }
        else
        {
            // there was an error in saving or getting GPS data
            switch (result.getError())
            {
                case FAILED_TO_SAVE_TO_DB:
                    return Response.failureResponse("Failed to save the notebook to the database");

                case GPS_FAILED_TO_GET_TIME:
                    return Response.failureResponse("Failed to get date and time from GPS");

                case GPS_FAILED_TO_GET_LOCATION:
                    return Response.failureResponse("Failed to get location from GPS");
            }

            return Response.failureResponse("Unknown error occurred");
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
    public Response updateNotebook(@RequestBody de.jacobs.university.GNS.request.Notebook notebook)
    {
        Notebook newNotebook = notebookService.updateNotebook(notebook.getId(), notebook.getNote(), notebook.getDescription());
        if (newNotebook != null) {
            return Response.successResponse("Notebook was updated successfully", de.jacobs.university.GNS.response.Notebook.buildFromEntity(newNotebook));
        }
        else {
            return Response.failureResponse("Invalid id for notebook");
        }
    }

    // Delete (soft delete) a notebook
    @RequestMapping(value = "api/v1/notes/delete", method = RequestMethod.DELETE)
    public Response deleteNotebook(@RequestBody de.jacobs.university.GNS.request.Notebook notebook)
    {
        if (notebookService.softDeleteNotebook(notebook.getId())) {
            return Response.successResponse("Notebook was deleted successfully", null);
        }
        else {
            return Response.failureResponse("Failed to delete notebook. Invalid ID.");
        }
    }
}
