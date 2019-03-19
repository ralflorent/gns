// NotebookSaveResult.java
// The class that represents the result of saving a notebook

package de.jacobs.university.GNS.service;

import de.jacobs.university.GNS.model.Notebook;
import lombok.Data;

@Data
public class NotebookSaveResult
{
    // can be null
    private NotebookServiceError error;

    // can be null
    private Notebook newNotebook;

    public NotebookSaveResult(Notebook notebook, NotebookServiceError error) {
        this.newNotebook = notebook;
        this.error = error;
    }
}
