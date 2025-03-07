<template>
    <v-dialog v-model="dialogVisible" max-width="500px">
      <v-card>
        <v-card-title>Person Info</v-card-title>
        <v-card-text>
            <div><strong>Nome:</strong> {{ personData.name }}</div>
            <div><strong>Idade:</strong> {{ personData.age }}</div>
            <div><strong>istID:</strong> {{ personData.istID }}</div>
            <div><strong>Email:</strong> {{ personData.email }}</div>
            <div><strong>Tipo:</strong> {{ personData.type }}</div>
        </v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn text @click="closeDialog">Close</v-btn>
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
  emit('close-dialog') // Emit the `close-dialog` event to the parent
}
</script>