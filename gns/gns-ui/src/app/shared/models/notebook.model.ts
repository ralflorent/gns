/**
 * JSON Signature for the notebook model
 *
 *
 * Created on April 06, 2019
 * @author Ralph Florent <ralflornt@gmail.com>
 */
export interface Notebook {
    id: number;
    noteId: string;
    description: string;
    note: string;
    latitude: number;
    longitude: number;
    gnsDate: string | Date;
    createdOn?: string | Date;
    createdBy?: string;
}

export interface NotebookFormError {
    description?: string;
    note?: string;
    createdBy?: string;
}