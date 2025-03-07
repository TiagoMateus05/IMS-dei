import axios from 'axios'

import PersonDto from '../models/persons/PersonDto'
import { get } from 'node_modules/axios/index.cjs'

const httpClient = axios.create()
httpClient.defaults.timeout = 10000
httpClient.defaults.baseURL = import.meta.env.VITE_ROOT_API
httpClient.defaults.headers.post['Content-Type'] = 'application/json'

export default class PersonService {

  // ------------------- Persons -------------------
  static async getPersons(): Promise<PersonDto[]> {
    return httpClient.get('/persons/all').then((response) => {
      return response.data.map((person: any) => {
        return new PersonDto(person)
      })
    })
  }

  /**
   * Create a new person.
   *
   * @param person The person to be created.
   * @returns The created person.
   */
  static async createPerson(person: PersonDto): Promise<PersonDto> {
    return httpClient.post('/persons/create', person).then((response) => {
      return new PersonDto(response.data)
    })
  }

  static async updatePerson(person: PersonDto): Promise<PersonDto> {
    console.log(person)
    return httpClient.put(`/persons/update/${person.id}`, person).then((response) => {
      return new PersonDto(response.data);
    });
  }

  static async deletePerson(person: PersonDto): Promise<void> {
    return httpClient.delete(`/persons/delete/${person.id}`)
  }
}
