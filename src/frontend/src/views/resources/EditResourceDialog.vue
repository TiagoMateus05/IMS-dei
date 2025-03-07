<template>
  <v-dialog v-model="dialogVisible" max-width="500px">
    <v-card>
      <v-card-title>Edit Resource</v-card-title>
      <v-card-text>
        <v-form>
          <v-text-field v-model="resourceData.name" label="Name"></v-text-field>
          <v-text-field v-model="resourceData.description" label="Description"></v-text-field>
          <v-text-field v-model="resourceData.location" label="Location"></v-text-field>
          <v-select
            v-model="resourceData.type"
            :items="resourceTypes"
            item-title="name"
            item-value="id"
              return-object
            label="Selecione a Categoria*"
            required
            @click="fetchResourceTypes()"
          ></v-select>

          <v-select
            v-model="resourceData.currentUser"
            :items="persons"
            item-title="name"
            item-value="id"
              return-object
            label="Selecione a Pessoa*"
            required
            @click="fetchPersons()"
          ></v-select>

          <v-text-field v-model="resourceData.problem" label="Problem"></v-text-field>
        </v-form>
      </v-card-text>
      <v-card-actions>
        <v-spacer></v-spacer>
        <v-btn color="error" text @click="removePerson">Remove Person</v-btn>
        <v-btn color="fix" text @click="fixedResource">Fixed</v-btn>
        <v-btn text @click="closeDialog">Cancel</v-btn>
        <v-btn color="primary" text @click="saveResource">Save</v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>
</template>

<script setup lang="ts">
import { ref, reactive, defineProps, defineEmits, watch } from 'vue'
import ResourceTypeService from '@/services/ResourceTypeService'
import ResourceService from '@/services/ResourceService'
import ResourceDto from '@/models/resources/ResourceDto'
import ResourceType from '@/models/resources/ResourceTypeDto'
import PersonService from '@/services/PersonService'
import PersonDto from '@/models/resources/PersonDto'

const persons = ref<PersonDto[]>([]);

// Props and emits
const props = defineProps({
  resource: {
    type: Object as () => ResourceDto, // Define the `resource` prop as required and typed
    required: true,
  },
})
const emit = defineEmits(['resource-updated', 'close-dialog']) // Events emitted to parent

// State and reactive data
const dialogVisible = ref(false) // Controls dialog visibility
const resourceData = reactive<ResourceDto>({
  id: undefined,
  name: '',
  description: '',
  location: '',
  type: null,
  currentUser: null,
  status: '',
  problem: '',
}) // Holds the editable data
const types = ref<ResourceType[]>([]) // Holds the list of resource types
const resourceTypes = ref<ResourceType[]>([])

// Watch the `resource` prop to populate `resourceData` when the dialog opens
watch(
  () => props.resource,
  (newResource) => {
    Object.assign(resourceData, newResource) // Copy data from the prop to `resourceData`
    dialogVisible.value = true // Open the dialog when resource data is updated
  },
  { immediate: true } // Trigger immediately when the component is created
)

const fetchResourceTypes = async () => {
  try {
    const response = await ResourceTypeService.getResourceTypes();
    resourceTypes.value = response; // Assign the response directly to resourceTypes
  } catch (error) {
    console.error('Failed to fetch resource types:', error);
  }
};

const fetchPersons = async () => {
  try {
    const response = await PersonService.getPersons();
    persons.value = response; // Assign the response directly to resourceTypes
  } catch (error) {
    console.error('Failed to fetch resource types:', error);
  }
};

// Close the dialog
function closeDialog() {
  dialogVisible.value = false
  emit('close-dialog')
}

// Save the edited resource
async function saveResource() {
  try {
    console.log('About to send:', JSON.stringify(resourceData, null, 2));
    await ResourceService.updateResource(resourceData)
    emit('resource-updated') // Notify parent to refresh the list
    closeDialog()
  } catch (error) {
    console.error('Failed to save resource:', error) // Log error message
  }
  console.log(resourceData)
}

async function fixedResource() {
  try {
    await ResourceService.fixedResource(resourceData)
    emit('resource-updated') // Notify parent to refresh the list
    closeDialog()
  } catch (error) {
    console.error('Failed to save resource:', error) // Log error message
  }
}

async function removePerson() {
  try {
    await ResourceService.removePerson(resourceData)
    emit('resource-updated') // Notify parent to refresh the list
    closeDialog()
  } catch (error) {
    console.error('Failed to save resource:', error) // Log error message
  }
}

// Fetch resource types when the component is mounted
fetchResourceTypes();
fetchPersons();
</script>
