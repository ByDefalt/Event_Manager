package tracking_events.model

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.context.ApplicationContext
import org.springframework.dao.DataIntegrityViolationException
import tracking_events.repository.UtilisateurRepository
import java.time.LocalDateTime

@DataJpaTest
class UtilisateurModelTest {

    @Autowired
    lateinit var context: ApplicationContext

    // Test de la création d'un utilisateur valide
    @Test
    fun `should create valid user`() {
        val utilisateur = Utilisateur(
            email = "test@example.com",
            firstName = "John",
            lastName = "Doe",
            pseudo = "johnnydoe",
            phone = "1234567890",
            preferredContact = "email",
            notifications = true,
            bio = "Développeur passionné",
            registrationDate = LocalDateTime.now()
        )

        // Utilisation du repository ou service pour sauvegarder l'utilisateur, ici on assume un repository
        val savedUser = context.getBean(UtilisateurRepository::class.java).save(utilisateur)

        assertNotNull(savedUser)
        assertEquals("John", savedUser.firstName)
        assertEquals("Doe", savedUser.lastName)
        assertEquals("test@example.com", savedUser.email)
        assertNotNull(savedUser.registrationDate)
    }

    // Test de la validation du nom et prénom
    @Test
    fun `should throw exception when firstName is filled but lastName is empty`() {
        val exception = assertThrows<IllegalArgumentException> {
            Utilisateur(
                email = "test@example.com",
                firstName = "John",
                lastName = "",
                pseudo = "johnnydoe"
            )
        }

        assertEquals("Si le prénom est rempli, le nom doit l'être aussi, et inversement", exception.message)
    }

    @Test
    fun `should throw exception when lastName is filled but firstName is empty`() {
        val exception = assertThrows<IllegalArgumentException> {
            Utilisateur(
                email = "test@example.com",
                firstName = "",
                lastName = "Doe",
                pseudo = "johnnydoe"
            )
        }

        assertEquals("Si le prénom est rempli, le nom doit l'être aussi, et inversement", exception.message)
    }

    // Test de la validation du pseudo ou nom/prénom
    @Test
    fun `should throw exception when pseudo is empty and name is missing`() {
        val exception = assertThrows<IllegalArgumentException> {
            Utilisateur(
                email = "test@example.com",
                firstName = "",
                lastName = "",
                pseudo = ""
            )
        }

        assertEquals("Le pseudo doit être rempli ou le nom/prénom", exception.message)
    }

    // Test de la validation de l'email
    @Test
    fun `should throw exception when email is empty`() {
        val exception = assertThrows<IllegalArgumentException> {
            Utilisateur(
                email = "",
                firstName = "John",
                lastName = "Doe",
                pseudo = "johnnydoe"
            )
        }

        assertEquals("Email ne peut pas être vide", exception.message)
    }

    // Test de la création d'un utilisateur sans prénom et nom, mais avec pseudo
    @Test
    fun `should allow creation with pseudo when name is missing`() {
        val utilisateur = Utilisateur(
            email = "test@example.com",
            firstName = "",
            lastName = "",
            pseudo = "johnnydoe"
        )

        val savedUser = context.getBean(UtilisateurRepository::class.java).save(utilisateur)

        assertNotNull(savedUser)
        assertEquals("johnnydoe", savedUser.pseudo)
    }

    // Test pour vérifier les propriétés optionnelles (comme le téléphone et la bio)
    @Test
    fun `should create user with optional fields`() {
        val utilisateur = Utilisateur(
            email = "test@example.com",
            firstName = "John",
            lastName = "Doe",
            pseudo = "johnnydoe",
            phone = "1234567890",
            preferredContact = "email",
            notifications = true,
            bio = "Développeur passionné",
            registrationDate = LocalDateTime.now()
        )

        val savedUser = context.getBean(UtilisateurRepository::class.java).save(utilisateur)

        assertNotNull(savedUser.phone)
        assertEquals("Développeur passionné", savedUser.bio)
    }
}
