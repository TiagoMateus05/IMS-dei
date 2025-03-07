<template>
    <v-dialog :model-value="dialogVisible" @update:model-value="updateDialogVisible" max-width="800px">
      <v-card>
        <v-card-title>Logs for {{ selectedResource.name }}</v-card-title>
        <v-card-text>
          <v-data-table
            :headers="headers"
            :items="logs"
            class="elevation-1"
          >
            <template v-slot:[`item.person`]="{ item }">
              <span v-if="item.person">{{ item.person.name }}</span>
              <span v-else>No User</span>
            </template>
  
            <template v-slot:[`item.resource`]="{ item }">
              <v-chip>{{ item.resource?.name || 'Unknown Resource' }}</v-chip>
            </template>
  
            <template v-slot:[`item.timestamp`]="{ item }">
              <span>{{ new Date(item.timestamp).toLocaleString() }}</span>
            </template>
          </v-data-table>
        </v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn @click="closeDialog">Fechar</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </template>
  
  <script setup lang="ts">
  import { ref, watch } from 'vue'
  import LogsService from '@/services/LogsService'
  import type { Log } from '@/models/logs'
  import type { ResourceDto } from '@/models/resources/ResourceDto'
  
  const props = defineProps<{
    selectedResource: ResourceDto
    dialogVisible: boolean
  }>()
  
  const emit = defineEmits(['close-dialog', 'update:dialogVisible'])
  
  const logs = ref<Log[]>([])
  
  const headers = [
    { text: 'Action', value: 'action' },
    { text: 'Details', value: 'details' },
    { text: 'Person', value: 'person' },
    { text: 'Timestamp', value: 'timestamp' },
  ]
  
  const fetchLogs = async () => {
    try {
      console.log('Fetching logs for resource:', props.selectedResource.id)
      const data = await LogsService.getLogsByResource(props.selectedResource.id)
      logs.value = data
    } catch (error) {
      console.error('Failed to fetch logs:', error)
    }
  }
  
  watch(
    () => props.dialogVisible,
    (newVal) => {
      if (newVal) {
        fetchLogs()
      }
    },
    { immediate: true }
  )
  
  const closeDialog = () => {
    emit('close-dialog')
    emit('update:dialogVisible', false)
  }
  
  const updateDialogVisible = (value: boolean) => {
    emit('update:dialogVisible', value)
  }

  fetchLogs()
  </script>