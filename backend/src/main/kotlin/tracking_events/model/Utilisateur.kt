package tracking_events.model

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
data class Utilisateur(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    var pseudo: String? = null,

    var firstName: String? = null,

    var lastName: String? = null,

    @Column(nullable = false)
    var email: String,

    var phone: String? = null,

    @OneToMany(mappedBy = "creator")
    var eventCreator: MutableList<Event>? = mutableListOf(),

    @ManyToMany(mappedBy = "organizers")
    var eventOrganisateur: MutableList<Event>? = mutableListOf(),

    @ManyToMany(mappedBy = "participants")

    var eventsParticipant: MutableList<Event>? = mutableListOf(),

    @ManyToMany(mappedBy = "waitingList")
    var waitingList: MutableList<Event>? = mutableListOf(),

    var preferredContact: String? = null,

    @Column(nullable = false)
    var notifications: Boolean = false,

    var bio: String? = null,

    @Column(nullable = false)
    var registrationDate: LocalDateTime = LocalDateTime.now()
){
    fun toDTO(): UtilisateurDTO {
        return UtilisateurDTO(
            pseudo = this.pseudo,
            firstName = this.firstName,
            lastName = this.lastName,
            email = this.email,
            phone = this.phone,
            eventCreator = (this.eventCreator?.map { it.name }?.toMutableList()),
            eventOrganisateur = (this.eventOrganisateur?.map { it.name }?.toMutableList()),
            eventsParticipant = (this.eventsParticipant?.map { it.name }?.toMutableList()),
            waitingList = (this.waitingList?.map { it.name }?.toMutableList()),
            preferredContact = this.preferredContact,
            notifications = this.notifications,
            bio = this.bio,
            registrationDate = this.registrationDate
        )
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Utilisateur) return false
        return id == other.id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
    init {
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
    constructor() : this(
        id = 0,
        pseudo = "m",
        firstName = "m",
        lastName = "m",
        email = "m",
        phone = null,
        eventCreator = mutableListOf(),
        eventOrganisateur = mutableListOf(),
        eventsParticipant = mutableListOf(),
        waitingList = mutableListOf(),
        preferredContact = null,
        notifications = false,
        bio = null,
        registrationDate = LocalDateTime.now()
    )

}
