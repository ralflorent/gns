export interface Notebook {
    id: number;
    noteId: string;
    description: string;
    note: string;
    latitude: number;
    longitude: number;
    date: string | Date;
    createdOn?: string | Date;
    createdBy?: string;
}