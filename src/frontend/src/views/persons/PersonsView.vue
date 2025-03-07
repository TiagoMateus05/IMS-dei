<template>
  <v-row align="center">
    <v-col>
      <h2 class="text-left ml-1">Lista de Pessoas</h2>
    </v-col>
    <v-col cols="auto">
      <CreatePersonDialog @person-created="getPersons" />
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
    :headers="headers_persons"
    :items="persons"
    :search="search"
    :custom-filter="fuzzySearch"
    class="text-left"
  >
    <template v-slot:[`item.actions`]="{ item }">
      <v-icon @click="openLogsDialog(item)" class="mr-2">mdi-file-document</v-icon>
      <v-icon @click="openInfoDialog(item)" class="mr-2">mdi-information</v-icon>
      <v-icon @click="openEditDialog(item)" class="mr-2">mdi-pencil</v-icon>
      <v-icon @click="deletePerson(item)">mdi-delete</v-icon>
    </template>
  </v-data-table>

  <!-- EditPersonDialog -->
  <EditPersonDialog
    v-if="editDialog"
    :person="selectedPerson"
    @person-updated="getPersons"
    @close-dialog="closeEditDialog"
  />

  <!-- InfoPersonDialog -->
  <InfoPersonDialog
    v-if="infoDialog"
    :person="selectedPerson"
    @close-dialog="closeInfoDialog"
  />

  <!-- PersonsLogsDialog -->
  <PersonsLogsDialog
    v-if="logsDialog"
    :selectedPerson="selectedPerson"
    :dialog-visible="logsDialog"
    @close-dialog="closeLogsDialog"
  />
</template>

<script setup lang="ts">
import { ref } from 'vue';
import CreatePersonDialog from '@/views/persons/CreatePersonDialog.vue';
import EditPersonDialog from '@/views/persons/EditPersonDialog.vue';
import InfoPersonDialog from '@/views/persons/InfoPersonDialog.vue';
import PersonsLogsDialog from '@/views/persons/PersonsLogsDialog.vue';

import PersonService from '@/services/PersonService';
import type { PersonDto } from '@/models/persons/PersonDto';

const search = ref('');
const headers_persons = [
  {title: 'ID', text: 'ID', value: 'id', sortable: true },
  {title: 'Nome', text: 'Name', value: 'name', sortable: true  },
  {title: 'istID', text: 'istID', value: 'istID', sortable: true  },
  {text: 'Actions', value: 'actions', sortable: false },
];

const persons = ref<PersonDto[]>([]);
const editDialog = ref(false);
const infoDialog = ref(false);
const logsDialog = ref(false);
const selectedPerson = ref<PersonDto | null>(null);

const personTypes = {
  STUDENT: 'Aluno',
  TEACHER: 'Professor',
  WORKER: 'Funcionário',
};

// Fetch list of persons
const getPersons = async () => {
  try {
    const data = await PersonService.getPersons();
    persons.value = data.map((person: PersonDto) => ({
      ...person,
      type: personTypes[person.type as keyof typeof personTypes],
    }));
  } catch (error) {
    console.error('Failed to fetch persons:', error);
  }
};

// Open EditPersonDialog
const openEditDialog = (person: PersonDto) => {
  selectedPerson.value = { ...person };
  editDialog.value = true;
};

// Close EditPersonDialog
const closeEditDialog = () => {
  editDialog.value = false;
};

// Open InfoPersonDialog
const openInfoDialog = (person: PersonDto) => {
  selectedPerson.value = { ...person };
  infoDialog.value = true;
};

// Close InfoPersonDialog
const closeInfoDialog = () => {
  infoDialog.value = false;
};

// Open Logs Dialog
const openLogsDialog = (person: PersonDto) => {
  console.log('Opening Logs Dialog for:', person);
  selectedPerson.value = { ...person };
  logsDialog.value = true;
};

// Close Logs Dialog
const closeLogsDialog = () => {
  logsDialog.value = false;
};

// Delete a person
const deletePerson = async (person: PersonDto) => {
  try {
    await PersonService.deletePerson(person);
    getPersons();
  } catch (error) {
    console.error('Failed to delete person:', error);
  }
};

// Fuzzy search logic
const fuzzySearch = (value: string, search: string) => {
  const searchRegex = new RegExp(search.split('').join('.*'), 'i');
  return searchRegex.test(value);
};

// Initial fetch
getPersons();
</script>
