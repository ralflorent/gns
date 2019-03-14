// NotebookService.java
// Service class for handling functionality related to notebooks

package de.jacobs.university.GNS.service;

import de.jacobs.university.GNS.model.Notebook;
import de.jacobs.university.GNS.repository.NotebookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class NotebookService
{
    private NotebookRepository repo;
    private GPSService gpsService;

    private static final int USER_VIEWABLE_ID_MAX = 99999;

    @Autowired
    public NotebookService(NotebookRepository repo, GPSService gpsService) {
        this.repo = repo;
        this.gpsService = gpsService;
    }

    // Get all (valid / not soft-deleted notebooks)
    public List<Notebook> getAllNotebooks() {
        return repo.findAll();
    }

    // Search for notes by description
    public List<Notebook> searchNotebooks(String searchString) {
        return repo.searchByDescription(searchString);
    }

    // Get one notebook by id
    public Notebook getNotebook(Long id)
    {
        Optional<Notebook> nbOptional = repo.findById(id);
        if (nbOptional.isPresent()) {
            return nbOptional.get();
        }
        else {
            return null;
        }
    }

    // Add a new notebook. Returns null error object on success. Returns error object on failure.
    public NotebookSaveResult addNotebook(String username, String description, String note)
    {
        Notebook nb = new Notebook();

        // GPS location
        GPSService.GPSLocation location = gpsService.getGPSLocation();
        if (location == null) {
            return new NotebookSaveResult(null, NotebookServiceError.GPS_FAILED_TO_GET_LOCATION);
        }
        nb.setLatitude(location.getLatitude());
        nb.setLongitude(location.getLongitude());

        // GPS date time
        LocalDateTime dateTime = gpsService.getDateTime();
        if (dateTime == null) {
            return new NotebookSaveResult(null, NotebookServiceError.GPS_FAILED_TO_GET_TIME);
        }
        nb.setCreationDate(dateTime);
        nb.setRecordedDateTime(dateTime);

        // user viewable note id
        nb.setNoteID(getNextValidNotebookID());

        // fields provided by client
        nb.setDescription(description);
        nb.setCreatedByUserName(username);
        nb.setNote(note);

        // soft delete
        nb.setIsDeleted(0);

        Notebook savedNotebook = repo.save(nb);
        if (savedNotebook == null) {
            return new NotebookSaveResult(null, NotebookServiceError.FAILED_TO_SAVE_TO_DB);
        }
        else {
            return new NotebookSaveResult(savedNotebook, null);
        }
    }

    // Update an existing notebook's note and description
    public Notebook updateNotebook(Long id, String note, String description) {
        Optional<Notebook> nbOptional = repo.findById(id);
        return nbOptional.get();
    }

    // Soft delete a notebook
    public boolean softDeleteNotebook(Long id)
    {
        // get notebook and set is deleted
        Optional<Notebook> nbOptional = repo.findById(id);
        if (nbOptional.isPresent())
        {
            Notebook nb = nbOptional.get();
            nb.setIsDeleted(1);
            return ((repo.save(nb)) != null);
        }
        else {
            return false;
        }
    }

    // Get the next valid user viewable notebook ID
    // ID Format: SS-DDDD
    public String getNextValidNotebookID()
    {
        // get last record in db
        Notebook last = repo.findTopByOrderByIdDesc();

        if (last == null) {
            return "AA-00001";
        }

        // split into SS and DDDD parts
        String[] lastIDComponents = last.getNoteID().split("-");
        String letters = lastIDComponents[0];
        int number = Integer.parseInt(lastIDComponents[1]);

        String newLetters = "AA";
        int newNumber = 0;

        // process numerical part
        if ((number + 1) > USER_VIEWABLE_ID_MAX)
        {
            // will exceed the max number so increment string part
            char secondLetter = letters.charAt(1);
            if (secondLetter == 'Z')
            {
                // increment first letter
                char firstLetter = letters.charAt(0);
                firstLetter += 1;

                newLetters = String.valueOf(firstLetter) + String.valueOf(secondLetter);
            }
            else {
                char newSecondLetter = secondLetter;
                newSecondLetter += 1;
                newLetters = String.valueOf(letters.charAt(0)) + String.valueOf(newSecondLetter);
            }
        }
        else {
            newLetters = letters;
            newNumber = number + 1;
        }

        return String.format("%s-%05d", newLetters, newNumber);
    }
}
