export default class ResourceTypeDto {
    id?: string;
    name?: string;
    
    constructor(jsonObj: Partial<ResourceTypeDto>) {
        Object.assign(this, jsonObj)
    }

}