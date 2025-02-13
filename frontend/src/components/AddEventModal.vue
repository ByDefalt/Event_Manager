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
        <div class="button-group">
          <button @click="saveEvent">Enregistrer</button>
          <button @click="$emit('close')" class="cancel-button">Annuler</button>
        </div>
      </div>
    </div>
  </template>
  
  <script>
  export default {
    props: ['show'],
    data() {
      return {
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
            if(this.event.start < this.event.end){
                this.$emit('add-event', { ...this.event });
                this.resetForm();
            }else{
                alert('La date de fin doit être supérieure à la date de début');
                return;
            }
        } else {
          alert('Veuillez remplir tous les champs');
        }
      },
      resetForm() {
        this.event = { title: '', start: '', end: '' };
      }
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
  </style>
  
