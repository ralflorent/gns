// NotebookDetailResponse.java
// Response for getting notebook details

package de.jacobs.university.GNS.response;

import lombok.Data;

@Data
public class NotebookDetailResponse
{
    private String status;
    private String message;
    private Notebook notebook;

    public static NotebookDetailResponse successResponse(Notebook notebook)
    {
        NotebookDetailResponse response = new NotebookDetailResponse();

        response.setStatus("success");
        response.setMessage("Notebook fetched with id: " + notebook.getNote_id());
        response.setNotebook(notebook);

        return response;
    }

    public static NotebookDetailResponse failResponse(String message)
    {
        NotebookDetailResponse response = new NotebookDetailResponse();

        response.setStatus("failure");
        response.setMessage(message);
        response.setNotebook(null);

        return response;
    }
}
