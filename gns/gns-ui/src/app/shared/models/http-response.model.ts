export interface HttpResponse<T> {
    status: string;
    message: string;
    data: T;
}
