<template>
  <div class="modal-overlay" @click.self="$emit('close')">
    <div class="modal-content">
      <h3>Ajouter un événement</h3>
      <div class="form-group">
        <label for="title">Titre : </label>
        <input id="title" v-model="eventDTO.name" type="text" placeholder="Nom de l'événement" />
        <span v-if="erreurs.nom">{{ erreurs.nom }}</span>
      </div>
      <div class="form-group">
        <label for="start">Date de début : </label>
        <input id="start" v-model="eventDTO.startHour" type="datetime-local" />
      </div>
      <div class="form-group">
        <label for="end">Date de fin : </label>
        <input id="end" v-model="eventDTO.finishHour" type="datetime-local" />
      </div>
      <div class="form-group">
        <label for="description">Description : </label>
        <input id="description" v-model="eventDTO.description" type="text" />
        <span v-if="erreurs.nom">{{ erreurs.description }}</span>
      </div>
      <div class="form-group">
        <label for="location">Lieu : </label>
        <input id="location" v-model="eventDTO.location" type="text" />
        <span v-if="erreurs.nom">{{ erreurs.location }}</span>
      </div>
      <div class="form-group">
        <label for="isPrivate">Privée : </label>
        <input id="isPrivate" v-model="eventDTO.isPrivate" type="checkbox" />
      </div>
      <div class="form-group">
        <label for="notificationEnabled">Notification : </label>
        <input id="notificationEnabled" v-model="eventDTO.notificationEnabled" type="checkbox" />
      </div>
      <div class="form-group">
        <label for="limitedPlace">Place Limité : </label>
        <input id="limitedPlace" type="checkbox" v-model="eventDTO.isLimitedPlace" />
      </div>
      <div class="form-group">
        <label for="limitedPlaceNum">Nombre : </label>
        <input id="limitedPlaceNum" v-model="eventDTO.limitedPlaceNum" type="Number" :disabled="!isLimitedPlace" />
        <span v-if="erreurs.nom">{{ erreurs.limitedPlaceNum }}</span>
      </div>
      <div class="form-group">
        <label for="organizers">Organisateurs : </label>
        <multiselect v-model="eventDTO.organizers" :options="utilisateurs" :multiple="true" :searchable="true"
          :close-on-select="false" placeholder="Choisissez des organisateurs" label="name" track-by="name" />
      </div>
      <div class="button-group">
        <button @click="saveEvent">Enregistrer</button>
        <button @click="$emit('close')" class="cancel-button">Annuler</button>
      </div>
    </div>
  </div>
</template>

<script>
import Multiselect from 'vue-multiselect';
import 'vue-multiselect/dist/vue-multiselect.min.css';
import axios from 'axios';
import { toRaw } from 'vue';

export default {
  components: { Multiselect },
  props: {
    utilisateurs: []
  },
  data() {
    return {
      erreurs: {},
      eventDTO: {
        name: "",
        startHour: new Date().toISOString().slice(0, -1),  // Supprime le 'Z' si nécessaire
        finishHour: new Date().toISOString().slice(0, -1),
        createdDate: new Date().toISOString().slice(0, -1),
        creator: "vcxvcx",
        description: '',
        location: '',
        isPrivate: false,
        notificationEnabled: false,
        isLimitedPlace: false,
        limitedPlaceNum: 0,
        organizers: [],
        participants: [],
        waitingList: [],
        previousEvent: null,
        nextEvent: null,
      },
      event: {
        title: "",
        start: "",
        end: "",
      }
    };
  },
  methods: {
    async saveEvent() {
      this.verifyForm();
      this.event.title = this.eventDTO.name;
      this.event.start = this.eventDTO.startHour;
      this.event.end = this.eventDTO.finishHour;
      this.$emit('add-event', { ...this.event });
      this.postEvents();
      this.resetForm();
    },
    async postEvents() {
      const apiUrl = process.env.VUE_APP_API;
      await axios.post(`${apiUrl}/events`, toRaw(this.eventDTO));
    },
    verifyForm() {
      if (!this.eventDTO.name) {
        this.erreurs.nom = "Le nom de l'événement est obligatoire";
        return;
      }
      if (!this.eventDTO.description) {
        this.erreurs.description = "La description de l'événement est obligatoire";
        return;
      }
      if (!this.eventDTO.location) {
        this.erreurs.location = "Le lieu de l'événement est obligatoire";
        return;
      }
      if (this.eventDTO.isLimitedPlace && this.eventDTO.limitedPlaceNum <= 0) {
        this.erreurs.limitedPlaceNum = "Le nombre de place doit être supérieur à 0";
        return;
      }
      if (this.eventDTO.startHour >= this.eventDTO.finishHour) {
        alert('La date de fin doit être supérieure à la date de début');
        return;
      }
      if (this.eventDTO.startHour) {
        this.eventDTO.startHour += ':00'; // Ajoute les secondes manquantes
      }
      if (this.eventDTO.finishHour) {
        this.eventDTO.finishHour += ':00';
      }
    },
    resetForm() {
      this.event = { title: '', start: '', end: '' };
    },
  }
};
</script>

<style scoped>
.modal-overlay {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 1000;
}

.modal-content {
  background: white;
  padding: 20px;
  border-radius: 10px;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
  width: 400px;
  max-width: 90%;
}

.form-group {
  margin-bottom: 15px;
}

.button-group {
  display: flex;
  justify-content: flex-end;
  gap: 10px;
}

button {
  padding: 10px 20px;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  background-color: #42b983;
  color: white;
}

.cancel-button {
  background-color: #e74c3c;
}

.dropdown {
  position: relative;
  display: inline-block;
  width: 100%;
}

.dropdown-select {
  padding: 8px 12px;
  border: 1px solid #ccc;
  cursor: pointer;
  background: white;
}

.dropdown-menu {
  position: absolute;
  top: 100%;
  left: 0;
  background: white;
  border: 1px solid #ccc;
  width: 100%;
  max-height: 150px;
  overflow-y: auto;
  z-index: 1000;
}

.dropdown-item {
  padding: 5px;
}
</style>
