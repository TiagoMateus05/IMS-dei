<template>
  <v-dialog v-model="dialogVisible" max-width="500px">
    <v-card>
      <v-card-title>Edit Person</v-card-title>
      <v-card-text>
        <v-form>
          <v-text-field v-model="personData.name" label="Name"></v-text-field>
          <v-text-field v-model="personData.age" label="Age"></v-text-field>
          <v-text-field v-model="personData.istID" label="istID"></v-text-field>
          <v-text-field v-model="personData.email" label="Email"></v-text-field>
          <v-select v-model="personData.type" :items="types" label="Type"></v-select>
        </v-form>
      </v-card-text>
      <v-card-actions>
        <v-spacer></v-spacer>
        <v-btn text @click="closeDialog">Cancel</v-btn>
        <v-btn color="primary" text @click="savePerson">Save</v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>
</template>

<script setup lang="ts">
import { ref, reactive, defineProps, defineEmits, watch } from 'vue'
import PersonService from '@/services/PersonService'
import PersonDto from '@/models/persons/PersonDto'

// Props and emits
const props = defineProps({
  person: {
    type: Object as () => PersonDto, // Define the `person` prop as required and typed
    required: true,
  },
})
const emit = defineEmits(['person-updated', 'close-dialog']) // Events emitted to parent

// State and reactive data
const dialogVisible = ref(false) // Controls dialog visibility
const personData = reactive<PersonDto>({
  id: undefined,
  name: '',
  age: '',
  istID: '',
  email: '',
  type: '',
}) // Holds the editable data
const types = ['Aluno', 'Professor', 'Funcionário'] // Type options

// Watch the `person` prop to populate `personData` when the dialog opens
watch(
  () => props.person,
  (newPerson) => {
    Object.assign(personData, newPerson) // Copy data from the prop to `personData`
    dialogVisible.value = true // Open the dialog when person data is updated
  },
  { immediate: true } // Trigger immediately when the component is created
)

// Close the dialog
function closeDialog() {
  dialogVisible.value = false
  emit('close-dialog')
}

function savePerson() {
  const personToSave = {
    ...personData,
    type: mapType(personData.type), // Map the user-friendly type to backend-compatible enum
  };
  PersonService.updatePerson(personToSave)
    .then(() => {
      emit('person-updated');
      closeDialog();
    })
    .catch((error) => {
      console.error('Failed to save person:', error);
    });
}

// Map frontend-friendly types to backend-compatible types
function mapType(type) {
  const typeMap = {
    "Aluno": "STUDENT",
    "Professor": "TEACHER",
    "Funcionário": "WORKER",
  };
  return typeMap[type] || type; // Return the mapped value or the original if no match is found
}
</script>
