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