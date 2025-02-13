<template>
  <div class="callendarContainer">
    <h1>Calendrier</h1>
  <div class="callendar">
    <div>
      <FullCalendar :options="calendarOptions" />
    </div>
  </div>
  </div>
  <AddEventModal v-if="showModal" @close="showModal = false" @add-event="addEvent" />
</template>

<script>
import { defineComponent } from 'vue';
import axios from 'axios';
import FullCalendar from '@fullcalendar/vue3';
import timeGridPlugin from '@fullcalendar/timegrid';
import interactionPlugin from '@fullcalendar/interaction';
import frLocale from '@fullcalendar/core/locales/fr';
import dayGridPlugin from '@fullcalendar/daygrid';
import AddEventModal from './AddEventModal.vue'; // Import du composant modal

export default defineComponent({
  name: 'AffichageCalendrier',
  components: {
    FullCalendar,
    AddEventModal
  },
  data() {
    return {
      showModal: false,
      newEvent: {
        title: '',
        start: '',
        end: ''
      },
      calendarOptions: {
        plugins: [timeGridPlugin, interactionPlugin, dayGridPlugin],
        initialView: 'timeGridWeek',
        locale: frLocale,
        allDaySlot: false,
        events: [],
        headerToolbar: {
          left: 'prev,next today addEventButton',
          center: 'title',
          right: 'timeGridDay,timeGridWeek,dayGridMonth'
        },
        customButtons: {
          addEventButton: {
            text: '+',
            click: () => this.showModal = true
          }
        },
      }
    };
  },
  methods: {
    async addEvent(event) {
      this.calendarOptions.events.push(event);
      this.showModal = false;
    },
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
        alert('Erreur lors de la récupération des événements:');
      }
    }
  },
  mounted() {
    this.getEvents();
  }
});
</script>

<style scoped>
.callendarContainer {
  display: flex;
  flex-direction: column;
  align-items: center;
  background-color: #1b1b1b;
}
.callendar {
  background-color: #131314;
  width: 60%;
  padding: 20px;
  border-radius: 10px;
}
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
