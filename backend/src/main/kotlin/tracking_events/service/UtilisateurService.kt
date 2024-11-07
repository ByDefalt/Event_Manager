package tracking_events.service

import org.springframework.stereotype.Service
import tracking_events.model.Event
import tracking_events.model.Utilisateur
import tracking_events.repository.UtilisateurRepository

@Service
class UtilisateurService(private val utilisateurRepository: UtilisateurRepository) {
    fun getUtilisateurById(id: Long): Utilisateur {
        return utilisateurRepository.findById(id)
            .orElseThrow { RuntimeException("Utilisateur not found!") }
    }
}