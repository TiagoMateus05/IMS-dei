import type PersonDto from "../persons/PersonDto";
import type ResourceDto from "../resources/ResourceDto";

export default class LogDto {
    id?: string;
    action?: string;
    details?: string;
    person?: PersonDto;
    resource?: ResourceDto;
    timestamp?: Date;

    constructor(jsonObj: Partial<LogDto>) {
        Object.assign(this, jsonObj)
    }
}