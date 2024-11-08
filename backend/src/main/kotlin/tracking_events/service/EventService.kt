package tracking_events.service

import tracking_events.model.Event
import tracking_events.repository.EventRepository
import org.springframework.stereotype.Service

@Service
class EventService(private val eventRepository: EventRepository) {

    fun getAllEvents(): List<Event> {
        return eventRepository.findAll()
    }

    fun getEventById(id: Long): Event {
        return eventRepository.findById(id)
            .orElseThrow { RuntimeException("Event not found!") }
    }

}
