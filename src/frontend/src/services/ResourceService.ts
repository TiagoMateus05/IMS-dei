import axios from "axios";

import ResourceDto from "@/models/resources/ResourceDto";

const httpClient = axios.create();
httpClient.defaults.timeout = 10000;
httpClient.defaults.baseURL = import.meta.env.VITE_ROOT_API;
httpClient.defaults.headers.post["Content-Type"] = "application/json";

export default class ResourceService {
  // ------------------- Resources -------------------

    static async getResourceTypes(): Promise<any[]> {
        return httpClient.get("/resource-types").then((response) => {
            return response.data; // Returns the list of resource types
        }); 
    }
    
    static async getResources(): Promise<ResourceDto[]> {
        return httpClient.get("/resources/all").then((response) => {
        return response.data.map((resource: any) => {
            return new ResourceDto(resource);
        });
        });
    }

    static async createResource(resource: ResourceDto): Promise<ResourceDto> {
        return httpClient.post("/resources/create", resource).then((response) => {
        return new ResourceDto(response.data);
        });
    }

    static async updateResource(resource: ResourceDto): Promise<ResourceDto> {
        return httpClient.put(`/resources/update/${resource.id}`, resource).then((response) => {
        return new ResourceDto(response.data);
        });
    }

    static async deleteResource(resource: ResourceDto): Promise<void> {
        return httpClient.delete(`/resources/delete/${resource.id}`);
    }

    static async fixedResource(resource: ResourceDto): Promise<ResourceDto> {
        return httpClient.put(`/resources/fixed/${resource.id}`).then((response) => {
        return new ResourceDto(response.data);
        });
    }

    static async removePerson(resource: ResourceDto): Promise<ResourceDto> {
        return httpClient.put(`/resources/remove-person/${resource.id}`).then((response) => {
        return new ResourceDto(response.data);
        });
    }

}