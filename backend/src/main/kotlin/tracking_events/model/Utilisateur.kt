package tracking_events.model

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
data class Utilisateur(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    var pseudo: String? = null, // Champ optionnel
    var firstName: String? = null,
    var lastName: String? = null,


    var email: String, // Champ obligatoire
    var phone: String? = null, // Champ optionnel

    @OneToMany(mappedBy = "creator")
    var eventCreator: MutableList<Event>? = mutableListOf(), // Liste des événements créés par l'utilisateur

    @ManyToMany(mappedBy = "organizers")
    var eventOrganisateur: MutableList<Event>? = mutableListOf(), // Liste des événements où l'utilisateur est organisateur

    @ManyToMany(mappedBy = "participants")
    var eventsParticipant: MutableList<Event>? = mutableListOf(), // Liste des événements auxquels l'utilisateur participe
    @ManyToMany(mappedBy = "waitingList")
    var waitingList: MutableList<Event>? = mutableListOf(),
    var preferredContact: String? = null, // Moyen de contact préféré (optionnel)
    var profilePicture: String? = null, // URL de l'image de profil (optionnel)

    var notifications: Boolean = false, // Notifications activées pour les événements (par défaut false)
    var bio: String? = null, // Brève description ou bio de l'utilisateur (optionnel)

    var registrationDate: LocalDateTime = LocalDateTime.now() // Date d'inscription (par défaut à la date actuelle)
){
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Utilisateur) return false
        return id == other.id
    }

    override fun hashCode(): Int {
        return id.hashCode() // Utiliser l'ID pour garantir une comparaison cohérente
    }
    init {
        validateUser()
    }

    private fun validateUser() {
        // Validation : Pseudo optionnel si prénom et nom sont remplis
        require(!(firstName.isNullOrEmpty() && !lastName.isNullOrEmpty())) {
            "Si le prénom est rempli, le nom doit l'être aussi, et inversement"
        }

        require(!(lastName.isNullOrEmpty() && !firstName.isNullOrEmpty())) {
            "Si le prénom est rempli, le nom doit l'être aussi, et inversement"
        }

        require(!(pseudo.isNullOrEmpty() && firstName.isNullOrEmpty() && lastName.isNullOrEmpty())) {
            "Le pseudo doit être rempli ou le nom/prénom"
        }

        require(email.isNotBlank()){"Email ne peut pas être vide"}

    }
}
