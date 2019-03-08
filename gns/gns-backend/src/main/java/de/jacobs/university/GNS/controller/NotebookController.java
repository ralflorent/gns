/**
 * NotebookController.java
 * Main controller that processes requests for field notebook functionality
 */

package de.jacobs.university.GNS.controller;

import de.jacobs.university.GNS.model.Notebook;

import de.jacobs.university.GNS.repository.NotebookRepository;
import de.jacobs.university.GNS.response.NotebookListResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class NotebookController
{
    private NotebookRepository repo;

    // Constructor
    @Autowired
    public NotebookController(NotebookRepository repo) {
        this.repo = repo;
    }

    // Return all existing notebooks
    @RequestMapping(value = "notes/list", method = RequestMethod.GET)
    public NotebookListResponse getNotebooks()
    {
        List<Notebook> entities = repo.findAll();
        NotebookListResponse response = new NotebookListResponse(entities);

        return response;
    }

    // Create a new notebook
    @RequestMapping(value = "notes/add", method = RequestMethod.POST)
    public String addNotebook(@RequestBody de.jacobs.university.GNS.request.Notebook notebook)
    {
        // TODO
        return "success";
    }
}
