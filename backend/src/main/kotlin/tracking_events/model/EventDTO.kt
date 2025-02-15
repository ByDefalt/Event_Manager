package tracking_events.model

import com.fasterxml.jackson.annotation.JsonFormat
import java.time.LocalDateTime

class EventDTO (

    var name: String,
    var startHour: LocalDateTime,
    var finishHour: LocalDateTime,
    var description: String?,
    var location: String?,
    var creator: String,
    var organizers: MutableList<String>,
    var participants: MutableList<String>?,
    var limitedPlaceNum: Int,
    var waitingList: MutableList<String>?,
    var isCancelled: Boolean,
    var isPrivate: Boolean,
    var notificationEnabled: Boolean,
    var createdDate: LocalDateTime,
    var previousEvent: Event?,
    var nextEvent: Event?
    ){
}