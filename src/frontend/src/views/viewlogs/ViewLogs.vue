<template>
    <div>
      <h1>Activity Logs</h1>
      <input
        v-model="searchQuery"
        placeholder="Search logs..."
        class="search-box"
      />
  
      <table>
        <thead>
          <tr>
            <th @click="sortTable('action')">Action</th>
            <th @click="sortTable('details')">Details</th>
            <th @click="sortTable('person')">Person</th>
            <th @click="sortTable('resource')">Resource</th>
            <th @click="sortTable('timestamp')">Timestamp</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="log in filteredLogs" :key="log.id">
            <td>{{ log.action }}</td>
            <td>{{ log.details }}</td>
            <td>{{ log.person?.name || "Sem Utilizador"}}</td>
            <td>{{ log.resource ? log.resource.name : "Recurso Apagado" }}</td>
            <td>{{ new Date(log.timestamp).toLocaleString() }}</td>
          </tr>
        </tbody>
      </table>
  
      <div class="pagination">
        <button @click="prevPage" :disabled="currentPage === 1">Previous</button>
        <span>Page {{ currentPage }} of {{ totalPages }}</span>
        <button @click="nextPage" :disabled="currentPage === totalPages">Next</button>
      </div>
    </div>
  </template>
  
  <script lang="ts">
  import { defineComponent, ref, computed, onMounted } from 'vue';
    import LogsService from '@/services/LogsService';
    import { Log } from '@/models/log//LogsDto';
  
  export default defineComponent({
    name: 'LogsView',
    setup() {
      const logs = ref<Log[]>([]);
      const searchQuery = ref('');
      const currentPage = ref(1);
      const itemsPerPage = 10;
  
      const fetchLogs = async () => {
        try {
          logs.value = await LogsService.getLogs();
          console.log(logs.value);
        } catch (error) {
          console.error('Failed to fetch logs:', error);
        }
      };
  
      onMounted(() => {
        fetchLogs();
      });
  
      const filteredLogs = computed(() => {
        const query = searchQuery.value.toLowerCase();
        return logs.value.filter(log =>
          log.action.toLowerCase().includes(query) ||
          log.details.toLowerCase().includes(query) ||
          log.person.name.toLowerCase().includes(query) ||
          log.resource.name.toLowerCase().includes(query)
        ).slice((currentPage.value - 1) * itemsPerPage, currentPage.value * itemsPerPage);
      });
  
      const totalPages = computed(() => {
        return Math.ceil(logs.value.length / itemsPerPage);
      });
  
      const sortTable = (key: keyof Log) => {
        logs.value.sort((a, b) => {
          if (a[key] < b[key]) return -1;
          if (a[key] > b[key]) return 1;
          return 0;
        });
      };
  
      const prevPage = () => {
        if (currentPage.value > 1) {
          currentPage.value--;
        }
      };
  
      const nextPage = () => {
        if (currentPage.value < totalPages.value) {
          currentPage.value++;
        }
      };
      fetchLogs();
      return {
        logs,
        searchQuery,
        currentPage,
        filteredLogs,
        totalPages,
        sortTable,
        prevPage,
        nextPage,
      };
    },

  });
  </script>
  
  <style scoped>
  .search-box {
    margin-bottom: 20px;
    padding: 10px;
    width: 100%;
    box-sizing: border-box;
  }
  
  table {
    width: 100%;
    border-collapse: collapse;
  }
  
  th, td {
    padding: 10px;
    border: 1px solid #ddd;
    text-align: left;
  }
  
  th {
    cursor: pointer;
  }
  
  .pagination {
    margin-top: 20px;
    display: flex;
    justify-content: space-between;
    align-items: center;
  }
  </style>