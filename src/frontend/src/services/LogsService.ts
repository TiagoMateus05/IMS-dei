import axios from 'axios'

import LogsDto from '../models/log/LogsDto'
import { get } from 'node_modules/axios/index.cjs'

const httpClient = axios.create()
httpClient.defaults.timeout = 10000
httpClient.defaults.baseURL = import.meta.env.VITE_ROOT_API
httpClient.defaults.headers.post['Content-Type'] = 'application/json'

export default class LogsService {

  // ------------------- Logs -------------------
  static async getLogs(): Promise<LogsDto[]> {
    return httpClient.get('/logs/all').then((response) => {
      return response.data.map((log: any) => {
        return new LogsDto(log)
      })
    })
  }

  static async getLogsByResource(resourceid: string) {
    return httpClient.get(`/logs/resource/${resourceid}`).then((response) => {
      return response.data.map((log: any) => {
        return new LogsDto(log)
      })
    })
  }

  static async getLogsByUser(userid: string) {
    return httpClient.get(`/logs/person/${userid}`).then((response) => {
      return response.data.map((log: any) => {
        return new LogsDto(log)
      })
    })
  }

  static async getLogsByResourceAndUser(resourceid: string, userid: string) {
    return httpClient.get(`/logs/resource/${resourceid}/person/${userid}`).then((response) => {
      return response.data.map((log: any) => {
        return new LogsDto(log)
      })
    })
  }
}