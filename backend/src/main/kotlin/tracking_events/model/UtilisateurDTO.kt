package tracking_events.model

import java.time.LocalDateTime

class UtilisateurDTO(
    var pseudo: String? = null,
    var firstName: String? = null,
    var lastName: String? = null,
    var email: String,
    var phone: String? = null,
    var eventCreator: MutableList<String>?,
    var eventOrganisateur: MutableList<String>?,
    var eventsParticipant: MutableList<String>?,
    var waitingList: MutableList<String>?,
    var preferredContact: String? = null,
    var notifications: Boolean = false,
    var bio: String? = null,
    var registrationDate: LocalDateTime
) {

}