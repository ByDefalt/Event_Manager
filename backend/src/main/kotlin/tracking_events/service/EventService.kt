package tracking_events.service

import tracking_events.model.Event
import tracking_events.repository.EventRepository
import org.springframework.stereotype.Service

@Service
class EventService(private val eventRepository: EventRepository,private val utilisateurService: UtilisateurService) {

    fun getAllEvents(): List<Event> {
        return eventRepository.findAll().filter{
            it.nextEventId==null
        }
    }
    fun getAllEventsWithHistory(): MutableList<Event> {
        return eventRepository.findAll()
    }

    fun getEventById(id: Long): Event {
        return eventRepository.findById(id)
            .orElseThrow { RuntimeException("Event not found!") }
    }

    fun getEventHistoryById(id: Long): MutableList<Event> {
        var event = getEventById(id)
        val result: MutableList<Event> = mutableListOf()
            while(event.previousEventId!=null){
                result.add(event)
                event=getEventById(event.previousEventId!!)
            }
        return result
    }

    fun createEvent(event: Event): Event {
        val creator = utilisateurService.getUtilisateurById(event.creatorId)

        // Vérifier si le créateur n'est pas déjà dans la liste des organisateurs
        if (!event.organizers.contains(creator)) {
            event.organizers.add(creator)
        }

        return eventRepository.save(event)
    }


    @Override
    fun createEvent(event: Event, previousEventId: Long): Event {
        event.previousEventId=previousEventId
        getEventById(previousEventId).nextEventId=event.id
        return eventRepository.save(event)
    }

    fun updateEvent(id: Long): Event {
        val event = getEventById(id)
        return createEvent(event,id)
    }

    fun deleteEvent(id: Long) {
        getEventHistoryById(id).forEach{ event ->
            eventRepository.deleteById(event.id)
        }
    }

}
