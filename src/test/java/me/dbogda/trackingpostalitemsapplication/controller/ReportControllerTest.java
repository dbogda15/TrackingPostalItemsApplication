package me.dbogda.trackingpostalitemsapplication.controller;

import me.dbogda.trackingpostalitemsapplication.model.Report;
import me.dbogda.trackingpostalitemsapplication.service.impl.ReportServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(ReportController.class)
class ReportControllerTest {

    @MockBean
    ReportServiceImpl reportService;

    @Autowired
    MockMvc mockMvc;

    static final Report NEW_REPORT = new Report("Report", 1L);
    static final Report REPORT_1 = new Report(1L, "Report", 1L);

    @Test
    @DisplayName("Создание нового отчёта")
    void whenCreateValidReport_thenReturn200() throws Exception {
        when(reportService.create(any(Report.class))).thenReturn(NEW_REPORT);
        mockMvc.perform(post("/reports")
                        .param("message", "Report")
                        .param("itemId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("message").value("Report"))
                .andExpect(jsonPath("postalItemId").value(1L));

        verify(reportService, times(1)).create(any(Report.class));
    }

    @Test
    @DisplayName("Удаление отчёта по его ID")
    void whenDelete_thenReturn200() throws Exception {
        mockMvc.perform(delete("/reports")
                        .param("id", "1"))
                .andExpect(status().isOk());

        verify(reportService, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("Обновление отчёта")
    void whenUpdate_thenReturn200() throws Exception {
        when(reportService.update(any(Report.class)))
                .thenReturn(REPORT_1);

        mockMvc.perform(put("/reports")
                        .param("id", "1")
                        .param("message", "Report")
                        .param("itemId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(1L))
                .andExpect(jsonPath("message").value("Report"))
                .andExpect(jsonPath("postalItemId").value(1L));

        verify(reportService, times(1)).update(any(Report.class));
    }

    @Test
    @DisplayName("Получение списка отчётов по ID почтового отделения")
    void whenGetAllByItemId_thenReturn200() throws Exception {
        when(reportService.getAllByItemId(1L))
                .thenReturn(List.of(REPORT_1));

        mockMvc.perform(get("/reports").
                        param("itemId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].message").value("Report"))
                .andExpect(jsonPath("$[0].postalItemId").value(1L));
    }
}