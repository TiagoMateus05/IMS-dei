import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'
import PersonsView from '@/views/persons/PersonsView.vue'
import ResourcesView from '@/views/resources/ResourcesView.vue'
import LogsView from '@/views/viewlogs/ViewLogs.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: HomeView
    },
    {
      path: '/about',
      name: 'about',
      component: HomeView
    },
    {
      path: '/contact',
      name: 'contact',
      component: HomeView
    },
    {
      path: '/resources',
      name: 'resources',
      component: ResourcesView
    },
    {
      path: '/persons',
      name: 'persons',
      component: PersonsView
    },
    {
      path: '/viewlogs',
      name: 'viewlogs',
      component: LogsView
    },
    {
      path: '/register',
      name: 'register',
      component: HomeView
    },
    {
      path: '/profile',
      name: 'profile',
      component: HomeView
    },
    {
      path: '/admin',
      name: 'admin',
      component: HomeView
    }
  ]
})

export default router
