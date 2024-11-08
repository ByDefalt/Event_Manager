package tracking_events.controller

import tracking_events.model.Event
import tracking_events.service.EventService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/events")
class EventController(private val eventService: EventService) {

    @GetMapping
    fun getAllEvents(): List<Event> {
        return eventService.getAllEvents()
    }

    @GetMapping("/{id}")
    fun getEventById(@PathVariable id: Long): Event {
        return eventService.getEventById(id)
    }

}
