// Response.java
// Generic Response Parent Class

package de.jacobs.university.GNS.response;

import lombok.Data;

@Data
public class Response
{
    protected String status;
    protected String message;
    protected Object data;

    // Build a success response with the given message
    public static Response successResponse(String message, Object data)
    {
        Response response = new Response();

        response.setStatus("success");
        response.setMessage(message);
        response.setData(data);

        return response;
    }

    // Build a generic fail response
    public static Response failureResponse(String message)
    {
        Response response = new Response();

        response.setStatus("failure");
        response.setMessage(message);
        response.setData(null);

        return response;
    }
}
