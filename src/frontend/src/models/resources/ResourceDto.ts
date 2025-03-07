import type PersonDto from "../persons/PersonDto";
import type ResourceTypeDto from "./ResourceTypeDto";

export default class ResourceDto {
    id?: string;
    name?: string;
    description?: string;
    type?: ResourceTypeDto;
    location?: string;
    currentUser?: PersonDto;
    status?: string;
    problem?: string;

    constructor(jsonObj: Partial<ResourceDto>) {
        Object.assign(this, jsonObj)
    }
}