<template>
    <div class="pa-4 text-center">
        <v-dialog v-model="dialog" max-width="400">
            <template v-slot:activator="{ props: activatorProps }">
                <v-btn
                    class="text-none font-weight-regular"
                    prepend-icon="mdi-plus"
                    text="Gerir Categorias"
                    v-bind="activatorProps"
                    color="primary"
                ></v-btn>
            </template>

            <v-card prepend-icon="mdi-account" title="Gerir Categorias">
                <!-- Form to Add New Type -->
                <v-text class="pa-4 text-center" style="padding-left: 5%; padding-right: 5%; font-size: smaller; font-style: italic;">Uma vez criado, a categoria não pode ser apagada a não ser por um administrador da base de dados</v-text>
                <v-card-text>
                    <v-text-field label="Nome*" required v-model="newType.name"></v-text-field>
                </v-card-text>

                
                <v-card-actions>
                    <v-spacer></v-spacer>
                    
                    <v-btn text="Close" variant="plain" @click="dialog = false"></v-btn>
                    
                    <v-btn
                    color="primary"
                    text="Criar"
                    variant="tonal"
                    @click="handleSaveType"
                    ></v-btn>
                </v-card-actions>
                
                <v-divider></v-divider>
                <!-- List of Existing Types -->
                <v-card-text>
                    <h4 class="text-left">Categorias Existentes</h4>
                    <v-table>
                        <thead>
                            <tr>
                                <th class="text-left">ID</th>
                                <th class="text-left">Nome</th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr v-for="type in types" :key="type.id">
                                <td>{{ type.id }}</td>
                                <td>{{ type.name }}</td>
                            </tr>
                        </tbody>
                    </v-table>
                </v-card-text>

            </v-card>
        </v-dialog>
    </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import type TypeDto from '@/models/resources/ResourceTypeDto'
import TypeService from '@/services/ResourceTypeService'

const dialog = ref(false)

// List of types
const types = ref<TypeDto[]>([])

// New type form data
const newType = ref<TypeDto>({
    name: '',
})

// Fetch the list of existing types
const fetchTypes = async () => {
    const response = await TypeService.getResourceTypes();
    types.value = response.map((type: any) => ({
        id: type.id,
        name: type.name,
    }));
};

// Save a new type
const saveType = async () => {
    await TypeService.createResourceType(newType.value)
    console.log('Type created:', newType.value)
    await fetchTypes()
    newType.value = { name: '' }
}

// Handle save type with validation
const handleSaveType = () => {
    if (newType.value.name.trim() === '') {
        alert('Nome é obrigatório');
        return;
    }
    saveType();
}

fetchTypes()

</script>
