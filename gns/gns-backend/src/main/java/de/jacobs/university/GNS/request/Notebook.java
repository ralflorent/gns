// Notebook.java
// Request object for notebook

package de.jacobs.university.GNS.request;

import lombok.Data;

@Data
public class Notebook
{
    private Long id;
    private String description;
    private String note;
    private String created_by;
}
