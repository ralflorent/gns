import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
    name: 'completesequence'
})
export class CompleteSequencePipe implements PipeTransform {

    transform(value: string, charLimit: number): string {
        return value;
    }
}
