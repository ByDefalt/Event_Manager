package tracking_events.model

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
data class Event(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    var name: String,

    var startHour: String,

    var finishHour: String,

    var description: String? = null,

    var location: String? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creator_id", nullable = false)
    var creator: Utilisateur,

    @ManyToMany
    @JoinTable(
        name = "eventsOrganizers",
        joinColumns = [JoinColumn(name = "event_id")],
        inverseJoinColumns = [JoinColumn(name = "user_id")],
        uniqueConstraints = [UniqueConstraint(columnNames = ["event_id", "user_id"])]
    )
    var organizers: MutableList<Utilisateur> = mutableListOf(),

    @ManyToMany
    @JoinTable(
        name = "eventsParticipants",
        joinColumns = [JoinColumn(name = "event_id")],
        inverseJoinColumns = [JoinColumn(name = "user_id")],
        uniqueConstraints = [UniqueConstraint(columnNames = ["event_id", "user_id"])]
    )
    var participants: MutableList<Utilisateur>? = mutableListOf(),

    var limitedPlaceNum: Int = 0, // Limitation du nombre de places (0 si illimité)

    @ManyToMany
    @JoinTable(
        name = "eventsWaitingList",
        joinColumns = [JoinColumn(name = "event_id")],
        inverseJoinColumns = [JoinColumn(name = "user_id")],
        uniqueConstraints = [UniqueConstraint(columnNames = ["event_id", "user_id"])]
    )
    var waitingList: MutableList<Utilisateur>? = mutableListOf(),

    var isCancelled: Boolean = false,

    var isPrivate: Boolean = false,

    var notificationEnabled: Boolean = true,

    var createdDate: LocalDateTime = LocalDateTime.now(),

    @OneToOne(fetch = FetchType.LAZY)
    var previousEvent: Event? = null, // Lien vers l'événement précédent, type DoubleChainage

    @OneToOne(fetch = FetchType.LAZY)
    var nextEvent: Event? = null // Lien vers l'événement suivant, type DoubleChainage
) {

    // Validation des champs dans le bloc init
    init {
        require(name.isNotBlank()) { "Name cannot be empty" }
        require(startHour.isNotBlank()) { "Start hour cannot be empty" }
        require(finishHour.isNotBlank()) { "Finish hour cannot be empty" }
    }
}
