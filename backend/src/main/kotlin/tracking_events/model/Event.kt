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

    var location: String? = null, // Location peut être null, mais ne doit pas être vide

    var creatorId: Long,

    @ElementCollection
    var organizers: MutableList<Utilisateur> = mutableListOf(),

    @ElementCollection
    var participants: MutableList<Utilisateur>? = mutableListOf(),

    var limitedPlaceNum: Int = 0, // Limitation du nombre de places (0 si illimité)

    @ElementCollection
    var waitingList: MutableList<Utilisateur>? = mutableListOf(),

    var isCancelled: Boolean = false,

    @ElementCollection
    var tags: MutableList<String>? = mutableListOf(),

    var isPrivate: Boolean = false,

    var notificationEnabled: Boolean = true,

    var createdDate: LocalDateTime = LocalDateTime.now(),

    @ElementCollection
    var listUser: MutableList<Utilisateur>? = mutableListOf(),

    @ElementCollection
    var listeAttente: MutableList<Utilisateur>? = mutableListOf(),

    var previousEventId: Long? = null, // Lien vers l'événement précédent

    var nextEventId: Long? = null // Lien vers l'événement suivant
) {

    // Constructeur par défaut


    // Validation des champs dans le bloc init
    init {
        require(name.isNotBlank()) { "Name cannot be empty" }
        require(startHour.isNotBlank()) { "Start hour cannot be empty" }
        require(finishHour.isNotBlank()) { "Finish hour cannot be empty" }
        require(creatorId != 0L) { "Creator ID cannot be empty" }
    }
    constructor() : this(
        id = 0,
        name = "l",
        startHour = "l",
        finishHour = "l",
        description = null,
        location = null,
        creatorId = 1,
        organizers = mutableListOf(),
        participants = mutableListOf(),
        limitedPlaceNum = 0,
        waitingList = mutableListOf(),
        isCancelled = false,
        tags = mutableListOf(),
        isPrivate = false,
        notificationEnabled = true,
        createdDate = LocalDateTime.now(),
        listUser = mutableListOf(),
        listeAttente = mutableListOf(),
        previousEventId = null,
        nextEventId = null
    )
}
