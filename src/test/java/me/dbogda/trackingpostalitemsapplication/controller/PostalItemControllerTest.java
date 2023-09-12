package me.dbogda.trackingpostalitemsapplication.controller;

import me.dbogda.trackingpostalitemsapplication.model.PostOffice;
import me.dbogda.trackingpostalitemsapplication.model.PostalItem;
import me.dbogda.trackingpostalitemsapplication.model.Report;
import me.dbogda.trackingpostalitemsapplication.service.impl.PostalItemServiceImpl;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PostalItemController.class)
@ExtendWith(MockitoExtension.class)
class PostalItemControllerTest {

    @MockBean
    PostalItemServiceImpl postalItemService;

    @Autowired
    MockMvc mockMvc;

    static final PostalItem NEW_POSTAL_ITEM = new PostalItem(PostalItem.Type.POSTCARD, 450000, "Address", "Name");
    static final PostOffice POST_OFFICE_1 = new PostOffice(1L, 450007, "Name", "Address", new ArrayList<>());
    static final PostOffice POST_OFFICE_2 = new PostOffice(2L, 450077, "Name", "Address", new ArrayList<>());
    static final Report REPORT = new Report(2L, "Отправление покинуло почтовое отделение с ID = " + POST_OFFICE_1.getId() + " и направляется к отделению с ID = " + POST_OFFICE_2.getId(), 1L);
    static final PostalItem POSTAL_ITEM_POSTCARD = new PostalItem(1L, PostalItem.Type.POSTCARD, 450000, "Address", "Name", PostalItem.Status.ON_THE_WAY, 1L, List.of(REPORT));
    static final PostalItem POSTAL_ITEM_PACKAGE = new PostalItem(2L, PostalItem.Type.PACKAGE, 450000, "Address", "Name", PostalItem.Status.ON_THE_WAY, 2L, new ArrayList<>());

    @Test
    @DisplayName("Создание нового почтового отправления (без начальной точки)")
    void whenCreateValidPostalItem_thenReturn200() throws Exception{
        when(postalItemService.create(any(PostalItem.class))).thenReturn(NEW_POSTAL_ITEM);
        mockMvc.perform(post("/items")
                .param("type", "POSTCARD")
                .param("recipientsIndex", "450000")
                .param("recipientsAddress", "Address")
                .param("recipientsName", "Name"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("type").value("POSTCARD"))
                .andExpect(jsonPath("recipientsIndex").value(450000))
                .andExpect(jsonPath("recipientsAddress").value("Address"))
                .andExpect(jsonPath("recipientsName").value("Name"));

        verify(postalItemService, times(1)).create(any(PostalItem.class));

    }

    @Test
    @DisplayName("Перемещение между почтовыми отделениями")
    void whenMovingIsCorrect_thenReturn200() throws Exception{
        when(postalItemService.moving(1L, 2L))
                .thenReturn(REPORT.getMessage());

        mockMvc.perform(put("/items/moving")
                        .param("itemId", "1")
                        .param("officeId", "2"))
                .andExpect(status().isOk())
                .andExpect(content().string("Отправление покинуло почтовое отделение с ID = " + POST_OFFICE_1.getId()
                        + " и направляется к отделению с ID = " + POST_OFFICE_2.getId()));

        verify(postalItemService, times(1)).moving(POSTAL_ITEM_POSTCARD.getId(), POST_OFFICE_2.getId());
    }

    @Test
    @DisplayName("Получение почтового отправления по его ID")
    void whenGetByIdReturnValidItem_thenReturn200() throws Exception{
        when(postalItemService.getById(2L))
                .thenReturn(POSTAL_ITEM_PACKAGE);
        mockMvc.perform(get("/items/item_id")
                .param("id", "2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(2L))
                .andExpect(jsonPath("type").value("PACKAGE"))
                .andExpect(jsonPath("recipientsIndex").value(450000))
                .andExpect(jsonPath("recipientsAddress").value("Address"))
                .andExpect(jsonPath("recipientsName").value("Name"))
                .andExpect(jsonPath("status").value("ON_THE_WAY"))
                .andExpect(jsonPath("postOfficeId").value(2L));

        verify(postalItemService, times(1)).getById(2L);
    }

    @Test
    @DisplayName("Получение почтового отправления по ID почтового отделения")
    void whenReturnValidListAfterGetByOfficeId_thenReturn200() throws Exception {
        when(postalItemService.getAllByPostOfficeId(2L))
                .thenReturn(List.of(POSTAL_ITEM_PACKAGE));
        mockMvc.perform(get("/items/office_id")
                        .param("id", "2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(2L))
                .andExpect(jsonPath("$[0].type").value("PACKAGE"))
                .andExpect(jsonPath("$[0].recipientsIndex").value(450000))
                .andExpect(jsonPath("$[0].recipientsAddress").value("Address"))
                .andExpect(jsonPath("$[0].recipientsName").value("Name"))
                .andExpect(jsonPath("$[0].status").value("ON_THE_WAY"))
                .andExpect(jsonPath("$[0].postOfficeId").value(2L));

        verify(postalItemService, times(1)).getAllByPostOfficeId(2L);
    }

    @Test
    @DisplayName("Получение всех почтовых отправлений")
    void whenGetAll_thenReturn200() throws Exception {
        when(postalItemService.getAll())
                .thenReturn(List.of(POSTAL_ITEM_PACKAGE));
        mockMvc.perform(get("/items/getAll")
                        .param("id", "2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(2L))
                .andExpect(jsonPath("$[0].type").value("PACKAGE"))
                .andExpect(jsonPath("$[0].recipientsIndex").value(450000))
                .andExpect(jsonPath("$[0].recipientsAddress").value("Address"))
                .andExpect(jsonPath("$[0].recipientsName").value("Name"))
                .andExpect(jsonPath("$[0].status").value("ON_THE_WAY"))
                .andExpect(jsonPath("$[0].postOfficeId").value(2L));

        verify(postalItemService, times(1)).getAll();
    }

    @Test
    @DisplayName("Удаление почтового отправления по его ID")
    void whenDelete_thenReturn200() throws Exception {
        mockMvc.perform(delete("/items")
                        .param("id","1"))
                .andExpect(status().isOk());

        verify(postalItemService, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("Обновление почтового отправления")
    void whenUpdate_thenReturn200() throws Exception {
        when(postalItemService.getById(2L))
                .thenReturn(POSTAL_ITEM_PACKAGE);
        when(postalItemService.update(any(PostalItem.class)))
                .thenReturn(POSTAL_ITEM_PACKAGE);

        mockMvc.perform(put("/items/update")
                        .param("id", "2")
                        .param("type", "PACKAGE")
                        .param("recipientsIndex", "450000")
                        .param("recipientsAddress", "Address")
                        .param("recipientsName", "Name"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(2L))
                .andExpect(jsonPath("type").value("PACKAGE"))
                .andExpect(jsonPath("recipientsIndex").value(450000))
                .andExpect(jsonPath("recipientsAddress").value("Address"))
                .andExpect(jsonPath("recipientsName").value("Name"))
                .andExpect(jsonPath("status").value("ON_THE_WAY"))
                .andExpect(jsonPath("postOfficeId").value(2L));

        verify(postalItemService, times(1)).update(any(PostalItem.class));
    }
}