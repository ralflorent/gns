// NotebookListResponse.java
// POJO for wrapping the JSON response for notebook listing

package de.jacobs.university.GNS.response;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class NotebookListResponse
{
    private List<Notebook> notebooks;

    // Construct with notebook entity list
    public NotebookListResponse(List<de.jacobs.university.GNS.model.Notebook> entities)
    {
        notebooks = new ArrayList<>();

        for (de.jacobs.university.GNS.model.Notebook entity : entities) {
            Notebook nb = Notebook.buildFromEntity(entity);
            notebooks.add(nb);
        }
    }
}
