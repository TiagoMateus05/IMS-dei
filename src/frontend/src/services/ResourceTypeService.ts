import axios from 'axios'

import ResourceType from '../models/resources/ResourceTypeDto'
import { get } from 'node_modules/axios/index.cjs'
import ResourceTypeDto from '@/models/resources/ResourceTypeDto'

const httpClient = axios.create()
httpClient.defaults.timeout = 10000
httpClient.defaults.baseURL = import.meta.env.VITE_ROOT_API
httpClient.defaults.headers.post['Content-Type'] = 'application/json'

export default class PersonService {

  // ------------------- ResourcesType -------------------
    static async getResourceTypes(): Promise<ResourceTypeDto[]> {
        return httpClient.get('/resource-types/all').then((response) => {
        return response.data.map((resourceType: any) => {
            return new ResourceTypeDto(resourceType)
        })
        })
    }

    static async createResourceType(resourceType: ResourceTypeDto): Promise<ResourceTypeDto> {
        return httpClient.post('/resource-types/create', resourceType).then((response) => {
        return new ResourceTypeDto(response.data)
        })
    }
}