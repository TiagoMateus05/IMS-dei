export default class PersonDto {
    id?: string;
    name?: string;
    istID?: string;
    email?: string;
    age?: number;
    type?: string;

    constructor(jsonObj: Partial<PersonDto>) {
        Object.assign(this, jsonObj)
    }

}