// NotebookUpdateResponse.java
// Response for a notebook update operation

package de.jacobs.university.GNS.response;

import lombok.Data;

@Data
public class NotebookUpdateResponse
{
    private String status;
    private String message;

    public static NotebookUpdateResponse successResponse(String message) {
        NotebookUpdateResponse response = new NotebookUpdateResponse();
        response.setMessage(message);
        response.setStatus("success");
        return response;
    }

    public static NotebookUpdateResponse failResponse(String message) {
        NotebookUpdateResponse response = new NotebookUpdateResponse();
        response.setMessage(message);
        response.setStatus("failure");
        return response;
    }
}
