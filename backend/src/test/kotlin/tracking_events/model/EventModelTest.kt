package tracking_events.model

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import tracking_events.repository.EventRepository

@DataJpaTest
class EventModelTest {

    @Autowired
    private lateinit var eventRepository: EventRepository


}
