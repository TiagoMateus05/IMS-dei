<template>
  <UtilBar />
  <NavBar :navbarItems="navbarItems" />
</template>

<script setup lang="ts">
import UtilBar from '@/components/UtilBar.vue'
import NavBar from '@/components/NavBar.vue'

const navbarItems = [
  { name: 'Pessoal', path: '/persons', icon: 'mdi-account-group' },
  { name: 'Recursos', path: '/resources', icon: 'mdi-briefcase' },
  { name: 'Logs', path: '/viewlogs', icon: 'mdi-auto-fix'}
]
</script>
