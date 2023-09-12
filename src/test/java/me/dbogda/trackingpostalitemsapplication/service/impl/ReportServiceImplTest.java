package me.dbogda.trackingpostalitemsapplication.service.impl;

import me.dbogda.trackingpostalitemsapplication.model.Report;
import me.dbogda.trackingpostalitemsapplication.repository.ReportRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReportServiceImplTest {

    @Mock
    ReportRepository reportRepository;

    @InjectMocks
    ReportServiceImpl out;

    static final Report NEW_REPORT = new Report("Message", 1L);
    static final Report REPORT = new Report(1L, "Message", 1L);

    @Test
    @DisplayName("Создание нового отчёта")
    void shouldReturnValidReportWhenCreate() {
        when(reportRepository.save(NEW_REPORT))
                .thenReturn(NEW_REPORT);

        assertEquals(NEW_REPORT, out.create(NEW_REPORT));

        verify(reportRepository, times(1)).save(NEW_REPORT);
    }

    @Test
    @DisplayName("Удаление отчёта по его ID")
    void shouldReturnCorrectMessageWhenDeleteById() {
        when(reportRepository.findById(1L))
                .thenReturn(Optional.of(REPORT));

        doNothing().when(reportRepository).deleteById(1L);

        assertEquals("Успешно удалено!", out.deleteById(1L));

        verify(reportRepository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("Обновление отчёта")
    void shouldReturnCorrectReportWhenUpdate() {
        when(reportRepository.save(REPORT))
                .thenReturn(REPORT);

        assertEquals(REPORT, out.update(REPORT));

        verify(reportRepository, times(1)).save(REPORT);
    }

    @Test
    @DisplayName("Получение списка отчётов по ID почтового отделения")
    void shouldReturnCorrectListWhenGetAllByItemId() {
        when(reportRepository.getAllByPostalItemId(1L))
                .thenReturn(List.of(NEW_REPORT, REPORT));

        assertEquals(List.of(NEW_REPORT, REPORT), out.getAllByItemId(1L));

        verify(reportRepository, times(1)).getAllByPostalItemId(1L);
    }

    @Test
    @DisplayName("Выброс исключения, если объект не существует при удалении")
    void shouldThrowRuntimeExceptionWhenReportDoesntExistWhenDelete() {
        when(reportRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(RuntimeException.class,()-> out.deleteById(1L));

        verify(reportRepository, times(1)).findById(1L);
    }

}