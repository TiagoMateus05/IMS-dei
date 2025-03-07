<template>
    <v-dialog v-model="dialogVisible" max-width="500px">
      <v-card>
        <v-card-title>Resource Info</v-card-title>
        <v-card-text>
            <div><strong>ID:</strong> {{ resourceData.id }}</div>
            <div><strong>Nome:</strong> {{ resourceData.age }}</div>
            <div><strong>Descrição:</strong> {{ resourceData.description }}</div>
            <div><strong>Localização:</strong> {{ resourceData.location }}</div>
            <div><strong>Tipo:</strong> {{ resourceData.type.name }}</div>
            
            <div>
              <strong>Utilizador Atual:</strong>
              <span v-if="resourceData.currentUser">
                {{ ' ' + resourceData.currentUser.name }}
              </span>
              <span v-else>
                Sem utilizador
              </span>
            </div>

            <div><strong>Disponibilidade:</strong> {{ resourceData.status }}</div>
            <div><strong>Problema:</strong> {{ resourceData.problem }}</div>

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
import Resourceervice from '@/services/ResourceService'
import ResourceDto from '@/models/resource/ResourceDto'

// Props and emits
const props = defineProps({
  resource: {
    type: Object as () => ResourceDto,
    required: true,
  },
})
const emit = defineEmits(['resource-updated', 'close-dialog'])

// State and reactive data
const dialogVisible = ref(false) // Controls dialog visibility

const resourceData = reactive<ResourceDto>({
    id: undefined,
    name: '',
    description: '',
    location: '',
    type: null,
    currentUser: '',
    status: '',
    problem: '',
}) // Holds the editable data


watch(
  () => props.resource,
  (newResource) => {
    Object.assign(resourceData, newResource)
    dialogVisible.value = true 
  },
  { immediate: true } // Trigger immediately when the component is created
)

// Close the dialog
function closeDialog() {
  dialogVisible.value = false
  emit('close-dialog') // Emit the `close-dialog` event to the parent
}
</script>