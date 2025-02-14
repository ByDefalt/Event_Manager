package tracking_events.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import tracking_events.model.EventDTO
import tracking_events.model.UtilisateurDTO
import tracking_events.repository.UtilisateurRepository
import tracking_events.service.UtilisateurService


@RestController
@RequestMapping("/api/users")
class UtilisateurController(private val utilisateurService: UtilisateurService) {

    @GetMapping
    fun getAllEvents(): List<UtilisateurDTO> {
        return utilisateurService.getAllUser().map { it.toDTO() }
    }

    @GetMapping("/{id}")
    fun getEventById(@PathVariable id: Long): UtilisateurDTO {
        return utilisateurService.getUtilisateurById(id).toDTO();

    }
}