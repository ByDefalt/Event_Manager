package tracking_events.model

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
data class Event(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(nullable = false)
    var name: String,

    @Column(nullable = false)
    var startHour: LocalDateTime,

    @Column(nullable = false)
    var finishHour: LocalDateTime,

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

    @Column(nullable = false)
    var limitedPlaceNum: Int = 0, // Limitation du nombre de places (0 si illimité)

    @ManyToMany
    @JoinTable(
        name = "eventsWaitingList",
        joinColumns = [JoinColumn(name = "event_id")],
        inverseJoinColumns = [JoinColumn(name = "user_id")],
        uniqueConstraints = [UniqueConstraint(columnNames = ["event_id", "user_id"])]
    )
    var waitingList: MutableList<Utilisateur>? = mutableListOf(),

    @Column(nullable = false)
    var isCancelled: Boolean = false,

    @Column(nullable = false)
    var isPrivate: Boolean = false,

    @Column(nullable = false)
    var notificationEnabled: Boolean = true,

    @Column(nullable = false)
    var createdDate: LocalDateTime = LocalDateTime.now(),

    @OneToOne(fetch = FetchType.LAZY)
    var previousEvent: Event? = null, // Lien vers l'événement précédent, type DoubleChainage

    @OneToOne(fetch = FetchType.LAZY)
    var nextEvent: Event? = null // Lien vers l'événement suivant, type DoubleChainage
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Event) return false
        return id == other.id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }

    init {
        require(name.isNotBlank()) { "Name cannot be empty" }
    }
}
