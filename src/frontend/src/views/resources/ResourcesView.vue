<template>
  <v-row align="center">
    <v-col>
      <h2 class="text-left ml-1">Lista de Recursos</h2>
    </v-col>
    <v-col cols="auto">
      <CreateResourceDialog @resource-created="getResources" />
    </v-col>
    <v-col cols="auto">
      <CreateTypesDialog @type-created="fetchResourceTypes" />
    </v-col>
  </v-row>

  <v-text-field
    v-model="search"
    label="Search"
    prepend-inner-icon="mdi-magnify"
    variant="outlined"
    hide-details
    single-line
  ></v-text-field>

  <v-data-table
    :headers="headers_resources"
    :items="resources"
    :search="search"
    :custom-filter="fuzzySearch"
    class="text-left"
  >
    <template v-slot:[`item.status`]="{ item }">
      <v-icon v-if="item.status === 'Available'" color="success">mdi-check</v-icon>
      <v-icon v-if="item.status === 'Broken'" color="warning">mdi-alert</v-icon>
      <v-icon v-if="item.status === 'Being Used'" color="error">mdi-close</v-icon>
    </template>

    <template v-slot:[`item.actions`]="{ item }">
      <v-icon @click="openLogsDialog(item)" class="mr-2">mdi-file-document</v-icon>
      <v-icon @click="openInfoDialog(item)" class="mr-2">mdi-information</v-icon>
      <v-icon @click="openEditDialog(item)" class="mr-2">mdi-pencil</v-icon>
      <v-icon @click="deleteResource(item)">mdi-delete</v-icon>
    </template>

    <template v-slot:[`item.currentUser.name`]="{ item }">
      <span v-if="item.currentUser">
        {{ item.currentUser.name }}
      </span>
      <span v-else>
        Sem utilizador
      </span>
    </template>
  </v-data-table>

  <!-- EditResourceDialog -->
  <EditResourceDialog
    v-if="editDialog"
    :resource="selectedResource"
    @resource-updated="getResources"
    @close-dialog="closeEditDialog"
  />

  <!-- InfoResourceDialog -->
  <InfoResourceDialog
    v-if="infoDialog"
    :resource="selectedResource"
    @close-dialog="closeInfoDialog"
  />

  <!-- ResourceLogsDialog -->
  <ResourceLogsDialog
    v-if="logsDialog"
    :selectedResource="selectedResource"
    :dialog-visible="logsDialog"
    @close-dialog="closeLogsDialog"
  />
</template>


<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue';
import CreateResourceDialog from '@/views/resources/CreateResourceDialog.vue';
import EditResourceDialog from '@/views/resources/EditResourceDialog.vue';
import CreateTypesDialog from './CreateTypesDialog.vue';
import InfoResourceDialog from './InfoResourceDialog.vue';
import ResourceLogsDialog from './ResourceLogsDialog.vue';

import ResourceTypeService from '@/services/ResourceTypeService';
import ResourceService from '@/services/ResourceService';
import type { ResourceDto } from '@/models/resources/ResourceDto';
import type { ResourceType } from '@/models/resources/ResourceTypeDto';

const search = ref('');
const headers_resources = [
  {title: 'ID', text: 'ID', value: 'id', sortable: true }, // Sortable
  {title: 'Nome', text: 'Name', value: 'name', sortable: true  }, // Sortable
  {title: 'Localização', text: 'Location', value: 'location', sortable: true  }, // Sortable
  {title: 'Categoria', text: 'Type', value: 'type.name', sortable: true  }, // Sortable (nested property)
  {title: 'Utilizador Atual', text: 'Current User', value: 'currentUser.name', sortable: true  }, // Sortable (nested property)
  {title: 'Disponibilidade', text: 'Availability', value: 'status', sortable: true  }, // Sortable
  { text: 'Actions', value: 'actions', sortable: false }, // Not sortable
];

const resources = ref<ResourceDto[]>([]);
const resourceTypes = ref<ResourceType[]>([]);

const editDialog = ref(false);
const infoDialog = ref(false);
const logsDialog = ref(false);
const selectedResource = ref<ResourceDto | null>(null);

const fetchResourceTypes = async () => {
  try {
    const response = await ResourceTypeService.getResourceTypes();
    resourceTypes.value = response;
  } catch (error) {
    console.error('Failed to fetch resource types:', error);
  }
};

const getResources = async () => {
  try {
    const data = await ResourceService.getResources();
    resources.value = data;
  } catch (error) {
    console.error('Failed to fetch resources:', error);
  }
};

// Open EditResourceDialog
const openEditDialog = (resource: ResourceDto) => {
  selectedResource.value = { ...resource };
  editDialog.value = true;
};

// Close EditResourceDialog
const closeEditDialog = () => {
  editDialog.value = false;
};

// Open InfoResourceDialog
const openInfoDialog = (resource: ResourceDto) => {
  selectedResource.value = { ...resource };
  infoDialog.value = true;
};

// Close InfoResourceDialog
const closeInfoDialog = () => {
  infoDialog.value = false;
};

// Open Logs Dialog
const openLogsDialog = (resource: ResourceDto) => {
  selectedResource.value = { ...resource };
  logsDialog.value = true;
};

// Close Logs Dialog
const closeLogsDialog = () => {
  logsDialog.value = false;
};

// Delete a resource
const deleteResource = async (resource: ResourceDto) => {
  try {
    await ResourceService.deleteResource(resource);
    getResources();
  } catch (error) {
    console.error('Failed to delete resource:', error);
  }
  console.log('Deleted resource:', resource);
};

// Fuzzy search logic
const fuzzySearch = (value: string, search: string) => {
  const searchRegex = new RegExp(search.split('').join('.*'), 'i');
  return searchRegex.test(value);
};

// Fetch resources and types on load
onMounted(() => {
  fetchResourceTypes();
  getResources();
});
</script>
