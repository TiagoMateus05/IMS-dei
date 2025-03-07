<template>
  <div class="pa-4 text-center">
    <v-dialog v-model="dialog" max-width="400">
      <template v-slot:activator="{ props: activatorProps }">
        <v-btn
          class="text-none font-weight-regular"
          prepend-icon="mdi-plus"
          text="Adicionar Recurso"
          v-bind="activatorProps"
          color="primary"
        ></v-btn>
      </template>

      <v-card prepend-icon="mdi-account" title="Novo Recurso">
        <v-card-text>
          <v-text-field label="Nome*" required v-model="newResource.name"></v-text-field>
          <v-text-field label="Descrição*" required v-model="newResource.description"></v-text-field>

          <v-select
            v-model="newResource.type"
            :items="resourceTypes"
            item-title="name"
            item-value="id"
              return-object
            label="Selecione a Categoria*"
            required
            @click="fetchResourceTypes()"
          ></v-select>

          <v-text-field label="Localização*" required v-model="newResource.location"></v-text-field>
        </v-card-text>

        <v-divider></v-divider>

        <v-card-actions>
          <v-spacer></v-spacer>

          <v-btn text="Close" variant="plain" @click="dialog = false"></v-btn>

          <v-btn
            color="primary"
            text="Save"
            variant="tonal"
            @click="saveResource"
          ></v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue';
import type ResourceDto from '@/models/resources/ResourceDto';
import ResourceService from '@/services/ResourceService';
import ResourceTypeService from '@/services/ResourceTypeService';
import type TypeDto from '@/models/resources/ResourceTypeDto';

const dialog = ref(false);

const emit = defineEmits(['resource-created']);

const resourceTypes = ref<TypeDto[]>([]); // Correctly define the type as an array

const newResource = ref<ResourceDto>({
  name: '',
  description: '',
  type: null, // Use type for the selected resource type object
  location: '',
});

const fetchResourceTypes = async () => {
  try {
    const response = await ResourceTypeService.getResourceTypes();
    resourceTypes.value = response; // Assign the response directly to resourceTypes
  } catch (error) {
    console.error('Failed to fetch resource types:', error);
  }
};

const saveResource = async () => {
  if (!newResource.value.name || !newResource.value.description || !newResource.value.type || !newResource.value.location) {
    alert('Please fill in all required fields.');
    return;
  }

  try {
    await ResourceService.createResource(newResource.value);
    newResource.value = {
      name: '',
      description: '',
      type: null, // Use type for the selected resource type object
      location: '',
    };
      
    emit('resource-created');
    closeDialog();
  } catch (error) {
    console.error('Failed to save resource:', error);
  }
  console.log(newResource.value);
};

const closeDialog = () => {
  dialog.value = false;
};

// Fetch resource types when the component is mounted
onMounted(() => {
  fetchResourceTypes();
});
</script>