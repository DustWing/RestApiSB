package com.example.restapisb;

import com.example.restapisb.database.model.User;
import com.example.restapisb.database.repos.UserRepo;
import com.example.restapisb.rest.dto.InUserDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class RestApiSbApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserRepo userRepo;

    @Test
    void testUsersGet() throws Exception {

        when(userRepo.findUsers()).thenReturn(
                List.of(
                        new User(
                                "ThisId", "testUsersGet", "test@test.com", 30
                        )
                )
        );

        this.mockMvc.perform(get("/api/users"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("testUsersGet")));

    }

    @Test
    void testUserGet() throws Exception {

        final String id = "testId";

        when(userRepo.findUser(id)).thenReturn(
                new User(
                        id, "testUserGet", "test@test.com", 42
                )
        );

        this.mockMvc.perform(get("/api/users/" + id))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("testUserGet")));

    }

    @Test
    void testUserPost() throws Exception {


        var input = new InUserDto(
                "InsertNewUser",
                "test@test.com",
                42
        );

        var content = objectMapper.writeValueAsString(input);

        this.mockMvc.perform(
                        post("/api/users")
                                .content(content)
                                .contentType(MediaType.APPLICATION_JSON)
                ).andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().string(containsString("InsertNewUser")));

    }

    @Test
    void testUserPut() throws Exception {

        final String id = "testId";

        when(userRepo.findUser(id)).thenReturn(
                new User(
                        id, "InsertUserPut", "test@test.com", 42
                )
        );


        var putUser = new InUserDto(
                "InsertNewUserPutEdit",
                "testEdit@test.com",
                43
        );

        final String bodyPut = objectMapper.writeValueAsString(putUser);

        this.mockMvc.perform(
                        put("/api/users/" + id)
                                .content(bodyPut)
                                .contentType(MediaType.APPLICATION_JSON)
                ).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("InsertNewUserPutEdit")));

    }

    @Test
    void testUserDelete() throws Exception {

        final String id = "testId";

        when(userRepo.findUser(id)).thenReturn(
                new User(
                        id, "InsertUserPut", "test@test.com", 42
                )
        );


        this.mockMvc.perform(
                        delete("/api/users/" + id)
                ).andDo(print())
                .andExpect(status().isNoContent());

    }

}
