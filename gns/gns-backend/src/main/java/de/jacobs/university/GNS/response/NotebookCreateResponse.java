// NotebookCreateResponse.java
// Response for API endpoint for creating a new notebook

package de.jacobs.university.GNS.response;

import de.jacobs.university.GNS.service.NotebookServiceError;
import lombok.Data;

@Data
public class NotebookCreateResponse
{
    private String status;
    private String message;
    private Long id;
    private String notebook_id;

    public static NotebookCreateResponse successResponse(Long id, String notebookID)
    {
        NotebookCreateResponse response = new NotebookCreateResponse();

        response.status = "success";
        response.message = "Notebook created successfully";
        response.id = id;
        response.notebook_id = notebookID;

        return response;
    }

    public static NotebookCreateResponse failResponse(String errorMessage)
    {
        NotebookCreateResponse response = new NotebookCreateResponse();

        response.id = (long) -1;
        response.notebook_id = "N/A";
        response.status = "failure";
        response.message = errorMessage;

        return response;
    }
}
