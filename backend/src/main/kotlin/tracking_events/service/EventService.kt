package tracking_events.service

import tracking_events.model.Event
import tracking_events.repository.EventRepository
import org.springframework.stereotype.Service
import tracking_events.model.EventDTO
import tracking_events.model.Utilisateur

@Service
class EventService(private val eventRepository: EventRepository, private val utilisateurService: UtilisateurService) {

    fun getAllEvents(): List<Event> {
        return eventRepository.findAll()
    }

    fun getEventById(id: Long): Event {
        return eventRepository.findById(id)
            .orElseThrow { RuntimeException("Event not found!") }
    }

    fun createEvent(event: Event): Event {
        return eventRepository.save(event)
    }

    @Override
    fun createEvent(eventDTO: EventDTO): Event {
        val event = Event(
            name = eventDTO.name,
            startHour = eventDTO.startHour,
            finishHour = eventDTO.finishHour,
            description = eventDTO.description,
            location = eventDTO.location,
            limitedPlaceNum = eventDTO.limitedPlaceNum,
            isCancelled = eventDTO.isCancelled,
            isPrivate = eventDTO.isPrivate,
            notificationEnabled = eventDTO.notificationEnabled,
            createdDate = eventDTO.createdDate,
            previousEvent = eventDTO.previousEvent,
            nextEvent = eventDTO.nextEvent,
            creator = utilisateurService.getUtilisateurByPseudo(eventDTO.creator),
            organizers = eventDTO.organizers.map { utilisateurService.getUtilisateurByPseudo(it) }.toMutableList(),
            participants = mutableListOf(),
            waitingList = mutableListOf()
        )
        return eventRepository.save(event)
    }

}
