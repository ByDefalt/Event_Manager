package tracking_events.controller

import tracking_events.model.Event
import tracking_events.service.EventService
import org.springframework.web.bind.annotation.*
import tracking_events.model.EventDTO

@RestController
@RequestMapping("/api/events")
class EventController(private val eventService: EventService) {

    @GetMapping
    fun getAllEvents(): List<EventDTO> {
        return eventService.getAllEvents().map { it.toDTO() }
    }

    @GetMapping("/{id}")
    fun getEventById(@PathVariable id: Long): EventDTO {
        return eventService.getEventById(id).toDTO();
    }

}
