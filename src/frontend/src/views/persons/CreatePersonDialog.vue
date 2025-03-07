<template>
    <div class="pa-4 text-center">
      <v-dialog v-model="dialog" max-width="400">
        <template v-slot:activator="{ props: activatorProps }">
          <v-btn
            class="text-none font-weight-regular"
            prepend-icon="mdi-plus"
            text="Adicionar Pessoa"
            v-bind="activatorProps"
            color="primary"
          ></v-btn>
        </template>
  
        <v-card prepend-icon="mdi-account" title="Nova Pessoa">
          <v-card-text>
            <v-text-field label="Nome*" required v-model="newPerson.name"></v-text-field>
            <v-text-field label="Idade*" required v-model="newPerson.age"></v-text-field>
            <v-text-field label="istID*" required v-model="newPerson.istID"></v-text-field>
            <v-text-field label="Email*" required v-model="newPerson.email"></v-text-field>
  
            <v-autocomplete
              :items="['Aluno', 'Professor', 'Funcionário']"
              label="Categoria*"
              auto-select-first
              required
              v-model="newPerson.type"
            ></v-autocomplete>
          </v-card-text>
  
          <v-divider></v-divider>
  
          <v-card-actions>
            <v-spacer></v-spacer>
  
            <v-btn text="Close" variant="plain" @click="dialog = false"></v-btn>
  
            <v-btn
              color="primary"
              text="Save"
              variant="tonal"
              @click="
                dialog = false;
                savePerson()
              "
            ></v-btn>
          </v-card-actions>
        </v-card>
      </v-dialog>
    </div>
  </template>
  
  <script setup lang="ts">
  import { ref } from 'vue'
  import type PersonDto from '@/models/persons/PersonDto'
  import PersonService from '@/services/PersonService'
  
  const dialog = ref(false)
  
  const emit = defineEmits(['person-created'])
  
  
  const typeMappings = {
    Aluno: 'STUDENT',
    Professor: 'TEACHER',
    Funcionário: 'WORKER'
  }
  
  const newPerson = ref<PersonDto>({
    name: '',
    age: '',
    istID: '',
    email: '',
    type: ''
  })
  
  const savePerson = async () => {
    newPerson.value.type = typeMappings[(newPerson.value.type) as keyof typeof typeMappings]
  
    await PersonService.createPerson(newPerson.value)
    newPerson.value = {
      name: '',
      age: '',
      istID: '',
      email: '',
      type: ''
    }
    emit('person-created')
  }
  </script>
  