package tracking_events

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.junit.jupiter.api.extension.ExtendWith
import tracking_events.model.Event
import tracking_events.model.Utilisateur
import tracking_events.repository.UtilisateurRepository
import java.time.LocalDateTime

@DataJpaTest
@ExtendWith(SpringExtension::class)
class EventRepositoryTest {
    private val ut=Utilisateur(email="jsp", pseudo = "ps1")
    @Test
    fun `should throw exception when name is empty`() {
        val exception = assertThrows<IllegalArgumentException> {
            Event(name = "", startHour = "10:00", finishHour = "12:00", creatorId = ut.id, organizers = mutableListOf(
                ut))
        }
        assertEquals("Name cannot be empty", exception.message)
    }

    @Test
    fun `should throw exception when start hour is empty`() {
        val exception = assertThrows<IllegalArgumentException> {
            Event(name = "Event", startHour = "", finishHour = "12:00", creatorId = 1, organizers = mutableListOf(Utilisateur(email="jsp", pseudo = "ps1")))
        }
        assertEquals("Start hour cannot be empty", exception.message)
    }

    @Test
    fun `should throw exception when finish hour is empty`() {
        val exception = assertThrows<IllegalArgumentException> {
            Event(name = "Event", startHour = "10:00", finishHour = "", creatorId = 1, organizers = mutableListOf(Utilisateur(email="jsp", pseudo = "ps1")))
        }
        assertEquals("Finish hour cannot be empty", exception.message)
    }

    @Test
    fun `should throw exception when description is empty`() {
        val exception = assertThrows<IllegalArgumentException> {
            Event(name = "Event", startHour = "10:00", finishHour = "12:00", creatorId = 1, description = "", organizers = mutableListOf(Utilisateur(email="jsp", pseudo = "ps1")))
        }
        assertEquals("Description cannot be empty", exception.message)
    }

    @Test
    fun `should throw exception when location is empty`() {
        val exception = assertThrows<IllegalArgumentException> {
            Event(name = "Event", startHour = "10:00", finishHour = "12:00", creatorId = 1, location = "", organizers = mutableListOf(Utilisateur(email="jsp", pseudo = "ps1")))
        }
        assertEquals("Location cannot be empty", exception.message)
    }

    @Test
    fun `should throw exception when creator is not in organizers`() {
        val exception = assertThrows<IllegalArgumentException> {
            Event(name = "Event", startHour = "10:00", finishHour = "12:00", creatorId = ut.id, organizers = mutableListOf(Utilisateur(email="jsp", pseudo = "ps1")))
        }
        assertEquals("the Creator is not organizer", exception.message)
    }

    @Test
    fun `should throw exception when one of the tags is empty`() {
        val exception = assertThrows<IllegalArgumentException> {
            Event(
                name = "Event",
                startHour = "10:00",
                finishHour = "12:00",
                creatorId = 1,
                tags = mutableListOf("Tag1", ""),
                organizers = mutableListOf(Utilisateur(email="jsp", pseudo = "ps1"))
            )
        }
        assertEquals("Tag cannot be empty", exception.message)
    }

    @Test
    fun `should create event successfully with valid inputs`() {
        val event = Event(
            name = "Valid Event",
            startHour = "10:00",
            finishHour = "12:00",
            creatorId = 1,
            organizers = mutableListOf(ut) // Creator is included in organizers
        )
        assertNotNull(event)
        assertEquals("Valid Event", event.name)
        assertEquals("10:00", event.startHour)
        assertEquals("12:00", event.finishHour)
        assertEquals(1, event.organizers.size)
        assertTrue(event.organizers.contains(ut)) // Check that creatorId is in organizers
    }

}
