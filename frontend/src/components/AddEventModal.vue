<template>
  <div class="modal-overlay" @click.self="$emit('close')">
    <div class="modal-content">
      <h3>Ajouter un événement</h3>
      <div class="form-group">
        <label for="title">Titre : </label>
        <input id="title" v-model="event.title" type="text" placeholder="Nom de l'événement" />
      </div>
      <div class="form-group">
        <label for="start">Date de début : </label>
        <input id="start" v-model="event.start" type="datetime-local" />
      </div>
      <div class="form-group">
        <label for="end">Date de fin : </label>
        <input id="end" v-model="event.end" type="datetime-local" />
      </div>
      <div class="form-group">
        <label for="description">Description : </label>
        <input id="description" v-model="event.description" type="text" />
      </div>
      <div class="form-group">
        <label for="location">Lieu : </label>
        <input id="location" v-model="event.location" type="text" />
      </div>
      <div class="form-group">
        <label for="isPrivate">Privée : </label>
        <input id="isPrivate" v-model="event.isPrivate" type="checkbox" />
      </div>
      <div class="form-group">
        <label for="notificationEnabled">Notification : </label>
        <input id="notificationEnabled" v-model="event.notificationEnabled" type="checkbox" />
      </div>
      <div class="form-group">
        <label for="limitedPlace">Place Limité : </label>
        <input id="limitedPlace" type="checkbox" v-model="isLimitedPlace" />
      </div>
      <div class="form-group">
        <label for="limitedPlaceNum">Nombre : </label>
        <input id="limitedPlaceNum" v-model="limitedPlaceNum" type="Number" :disabled="!isLimitedPlace" />
      </div>
      <div class="form-group">
        <label for="organizers">Organisateurs : </label>
        <multiselect v-model="Organizers" :options="utilisateurs" :multiple="true" :searchable="true"
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


export default {
  components: { Multiselect },
  props: {
    utilisateurs: []
  },
  data() {
    return {
      Organizers: [],
      isLimitedPlace: false,
      event: {
        title: '',
        start: '',
        end: ''
      }
    };
  },
  methods: {
    saveEvent() {
      if (this.event.title && this.event.start && this.event.end) {
        if (this.event.start < this.event.end) {
          this.$emit('add-event', { ...this.event });
          this.resetForm();
        } else {
          alert('La date de fin doit être supérieure à la date de début');
          return;
        }
      } else {
        alert('Veuillez remplir tous les champs');
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
