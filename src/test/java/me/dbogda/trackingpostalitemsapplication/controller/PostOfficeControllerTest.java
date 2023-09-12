package me.dbogda.trackingpostalitemsapplication.controller;

import me.dbogda.trackingpostalitemsapplication.model.PostOffice;
import me.dbogda.trackingpostalitemsapplication.service.impl.PostOfficeServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PostOfficeController.class)
@ExtendWith(MockitoExtension.class)
class PostOfficeControllerTest {

    @MockBean
    PostOfficeServiceImpl postOfficeService;

    @Autowired
    MockMvc mockMvc;

    static final PostOffice NEW_POST_OFFICE = new PostOffice(450007, "Name", "Address");
    static final PostOffice POST_OFFICE_1 = new PostOffice(1L, 450007, "Name", "Address", new ArrayList<>());

    @Test
    @DisplayName("Создание почтового отделения")
    void whenCreateValidPostOffice_thenReturn200() throws Exception{
        when(postOfficeService.create(any(PostOffice.class)))
                .thenReturn(NEW_POST_OFFICE);

        mockMvc.perform(post("/offices")
                        .param("index", "450077")
                        .param("name", "Name")
                        .param("recipientsAddress", "Address"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("index").value(450007))
                .andExpect(jsonPath("name").value("Name"))
                .andExpect(jsonPath("recipientsAddress").value("Address"));

        verify(postOfficeService, times(1)).create(any(PostOffice.class));
    }

    @Test
    @DisplayName("Получение списка всех почтовых отделений")
    void whenGetAll_thenReturn200() throws Exception {
        when(postOfficeService.getAll())
                .thenReturn(List.of(POST_OFFICE_1));

        mockMvc.perform(get("/offices/get_all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].index").value(450007))
                .andExpect(jsonPath("$[0].name").value("Name"))
                .andExpect(jsonPath("$[0].recipientsAddress").value("Address"));

        verify(postOfficeService, times(1)).getAll();
    }

    @Test
    @DisplayName("Получение инфо почтового отделения по его ID")
    void whenGetById_thenReturn200() throws Exception {
        when(postOfficeService.getById(1L))
                .thenReturn(POST_OFFICE_1);

        mockMvc.perform(get("/offices/id")
                        .param("id", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(1))
                .andExpect(jsonPath("index").value(450007))
                .andExpect(jsonPath("name").value("Name"))
                .andExpect(jsonPath("recipientsAddress").value("Address"));

        verify(postOfficeService, times(1)).getById(1L);
    }

    @Test
    @DisplayName("Удаление почтового отделения по его ID")
    void whenDelete_thenReturn200() throws Exception {
        mockMvc.perform(delete("/offices")
                        .param("id", "1"))
                .andExpect(status().isOk());

        verify(postOfficeService, times(1)).deleteById(1L);
    }
}