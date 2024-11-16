<template>
  <div class="hello">
    <h1>{{ msg }}</h1>
    <button @click="getEvents">Récupérer les événements</button>
    <div v-if="eventsFetched">
    <div v-if="events.length > 0 ">
      <h2>Liste des événements</h2>
      <ul>
        <li v-for="event in events" :key="event.id">
          {{ event }}
        </li>
      </ul>
    </div>
    <div v-else>
      <p>Aucun événement disponible</p>
    </div>
  </div>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      msg: 'Bienvenue sur la gestion des événements',
      events: [],
      eventsFetched: false,
    }
  },
  methods: {
    async getEvents() {
      try {
        const apiUrl = process.env.VUE_APP_API;
        const response = await axios.get(`${apiUrl}/events`); // URL de votre API backend
        this.events = response.data
      } catch (error) {
        console.error('Erreur lors de la récupération des événements:', error)
      }finally {
        this.eventsFetched = true; // Indiquer que l'appel a été effectué (qu'il y ait des événements ou non)
      }
    }
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
h3 {
  margin: 40px 0 0;
}
ul {
  list-style-type: none;
  padding: 0;
}
li {
  display: inline-block;
  margin: 0 10px;
}
a {
  color: #42b983;
}
</style>
