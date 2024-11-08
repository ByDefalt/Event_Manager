package tracking_events.repository

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import tracking_events.model.Event
import tracking_events.repository.EventRepository
import java.time.LocalDateTime

@DataJpaTest
class EventRepositoryTest {

    @Autowired
    private lateinit var eventRepository: EventRepository


}
