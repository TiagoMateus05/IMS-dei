<template>
    <v-dialog :model-value="dialogVisible" @update:modelValue="dialogVisible" max-width="800px">
      <v-card>
        <v-card-title>Logs for {{ selectedPerson.name }}</v-card-title>
        <v-card-text>
          <v-data-table
            :headers="headers"
            :items="logs"
            class="elevation-1"
          >
            <template v-slot:item.person="{ item }">
              <span>{{ item.person || 'No User' }}</span>
            </template>
  
            <template v-slot:item.resource="{ item }">
              <v-chip>{{ item?.resource || 'Deleted Resource' }}</v-chip>
            </template>
  
            <template v-slot:item.timestamp="{ item }">
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
  import type { PersonDto } from '@/models/persons/PersonDto'
  
  const props = defineProps<{
    selectedPerson: PersonDto
    dialogVisible: boolean
  }>()
  
  const emit = defineEmits(['close-dialog', 'update:dialogVisible'])
  
  const logs = ref<Log[]>([])
  
  const headers = [
    { text: 'Action', value: 'action' },
    { text: 'Details', value: 'details' },
    { text: 'Resource', value: 'resource' },
    { text: 'Timestamp', value: 'timestamp' },
  ]
  
  const fetchLogs = async () => {
    try {
      const data = await LogsService.getLogsByUser(props.selectedPerson.id);
      logs.value = data.map(log => ({
        ...log,
        resource: log.resource?.name || 'Deleted Resource',
        person: log.person?.name || 'No User',
      }));
      console.log('Fetched logs:', logs.value);
    } catch (error) {
      console.error('Failed to fetch logs:', error);
    }
  }
  
  watch(
    () => props.dialogVisible,
    (newVal) => {
      if (newVal) {
        fetchLogs();
      }
    }
  )
  
  const closeDialog = () => {
    emit('close-dialog');
    emit('update:dialogVisible', false);
  }

  fetchLogs();
  </script>
  