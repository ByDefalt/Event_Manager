<template>
  <div>
    <h1>Calendrier</h1>
    <div style="width: 500px; height: 500px;">
      <FullCalendar :options="calendarOptions" />
    </div>
  </div>
</template>

<script>
import { defineComponent } from 'vue';
import axios from 'axios';
import FullCalendar from '@fullcalendar/vue3';
import timeGridPlugin from '@fullcalendar/timegrid';
import interactionPlugin from '@fullcalendar/interaction';
import frLocale from '@fullcalendar/core/locales/fr';

export default defineComponent({
  components: {
    FullCalendar
  },
  data() {
    return {
      msg: 'Bienvenue sur la gestion des événements',
      eventsFetched: false,
      calendarOptions: {
        plugins: [timeGridPlugin, interactionPlugin],
        initialView: 'timeGridWeek',
        locale: frLocale,
        allDaySlot: false,
        events: [
          { title: 'Événement 1', start: '2025-02-13T10:00:00', end: '2025-02-13T12:00:00' },
          { title: 'Événement 2', start: '2025-02-14T14:00:00', end: '2025-02-14T16:00:00' }
        ]
      }
    };
  },
  methods: {
    async getEvents() {
      try {
        const apiUrl = process.env.VUE_APP_API;
        const response = await axios.get(`${apiUrl}/events`);
        this.calendarOptions.events = response.data.map(event => ({
          title: event.name,
          start: event.startHour,
          end: event.finishHour
        }));
      } catch (error) {
        console.error('Erreur lors de la récupération des événements:', error);
      } finally {
        this.eventsFetched = true;
      }
    }
  },
  mounted() {
    this.getEvents();
  }
});
</script>

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
