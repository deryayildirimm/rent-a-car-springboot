package dev.patika.definexjavaspringbootbootcamp2025.hw3.tests;

import dev.patika.definexjavaspringbootbootcamp2025.hw3.controller.UserController;
import dev.patika.definexjavaspringbootbootcamp2025.hw3.dto.User;
import dev.patika.definexjavaspringbootbootcamp2025.hw3.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    void getUsers_ShouldReturnOk () throws Exception {
        mockMvc.perform(
                get("/api/user/list")
        ).andExpect(status().isOk());
    }

    @Test
    void registerUser_ShouldReturnCreated () throws Exception {



        String userJson = """
        {
            "name": "Test User",
            "email": "test@example.com",
            "licenseNumber": "ABC123"
        }
    """;
        mockMvc.perform(
                post("/api/user/register")
                        .contentType("application/json")
                        .content(userJson)
        ).andExpect(status().isCreated());

    }

    @Test
    void getUserProfile_ShouldReturnOk () throws Exception {

        UUID userId = UUID.randomUUID();
        User mockUser = User.builder()
                .id(userId)
                .name("Derya")
                .email("derya@example.com")
                .licenseNumber("dry1235")
                .build();

        Mockito.when(userService.getUserProfile(userId)).thenReturn(mockUser);

        mockMvc.perform(
                get("/api/user/" + userId)
        ).andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Derya"))
                .andExpect(jsonPath("$.email").value("derya@example.com"))
                .andExpect(jsonPath("$.licenseNumber").value("dry1235"));

    }

    @Test
    void getUserProfile_ShouldReturnBadRequest () throws Exception {

        // Kullanıcıdan geçersiz bir UUID geldiğinde,
        // sistemin 400 BAD REQUEST hatası döndüğünü test etmeliyiz.

        String invalidUserId = "invalid-uuid-format";

        mockMvc.perform(
                get("/api/user/" + invalidUserId)
        ).andExpect(status().isBadRequest());

    }

    @Test
    void updateUser_ShouldReturnOk () throws Exception {
        //  1. Geçerli bir kullanıcı ID'si ve güncellenmiş veriler oluşturalım
        UUID userId = UUID.randomUUID();
        User updatedUser = User.builder()
                .id(userId)
                .name("Updated Name")
                .email("updated@example.com")
                .licenseNumber("XYZ987")
                .build();

        //  2. `userService.update(userId, updatedUser)` çağırıldığında güncellenmiş kullanıcı dönmesini sağlayalım
        Mockito.when(userService.update(Mockito.any(User.class))).thenReturn(updatedUser);

        //  3. JSON formatında güncellenmiş kullanıcıyı oluşturalım
        String updatedUserJson = """
        {
            "id": "%s",
            "name": "Updated Name",
            "email": "updated@example.com",
            "licenseNumber": "XYZ987"
        }
        """.formatted(userId);

        //  4. PUT isteği gönderelim ve 200 OK bekleyelim
        mockMvc.perform(put("/api/user/" + userId)
                        .contentType("application/json") // JSON olarak gönderiyoruz
                        .content(updatedUserJson)) // Güncellenmiş JSON içeriği
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated Name")) // JSON doğrulama
                .andExpect(jsonPath("$.email").value("updated@example.com"))
                .andExpect(jsonPath("$.licenseNumber").value("XYZ987"));

    }

    @Test
    void updateUser_ShouldReturnBadRequest () throws Exception {

        //  1. Geçersiz bir kullanıcı ID oluşturalım (UUID formatına uymayan string)
        String invalidUserId = "invalid-uuid-format";

        //  2. Güncellenmiş ancak eksik bilgiler içeren JSON içeriği oluşturalım
        String invalidUserJson = """
        {
            "name": "",
            "email": "invalid-email",
            "licenseNumber": ""
        }
        """;

        //  3. PUT isteği gönderelim ve 400 BAD REQUEST bekleyelim
        mockMvc.perform(put("/api/user/" + invalidUserId)
                        .contentType("application/json")
                        .content(invalidUserJson))
                .andExpect(status().isBadRequest());
    }
}
