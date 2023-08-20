package me.dbogda.trackingpostalitemsapplication.service.impl;

import me.dbogda.trackingpostalitemsapplication.model.PostOffice;
import me.dbogda.trackingpostalitemsapplication.model.PostalItem;
import me.dbogda.trackingpostalitemsapplication.model.Report;
import me.dbogda.trackingpostalitemsapplication.repository.PostOfficeRepository;
import me.dbogda.trackingpostalitemsapplication.repository.PostalItemRepository;
import me.dbogda.trackingpostalitemsapplication.repository.ReportRepository;
import me.dbogda.trackingpostalitemsapplication.service.PostalItemService;
import org.aspectj.lang.annotation.Before;
import org.assertj.core.internal.bytebuddy.pool.TypePool;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PostalItemServiceImplTest {

    @Mock
    PostalItemRepository postalItemRepository;
    @Mock
    PostOfficeRepository postOfficeRepository;
    @Mock
    ReportRepository reportRepository;

    @InjectMocks
    PostalItemServiceImpl out;

    static final PostalItem NEW_POSTAL_ITEM = new PostalItem(PostalItem.Type.POSTCARD, 450000, "Address", "Name");
    static final PostOffice POST_OFFICE_1 = new PostOffice(1L, 450007, "Name", "Address", new ArrayList<>());
    static final PostOffice POST_OFFICE_2 = new PostOffice(2L, 450077, "Name", "Address", new ArrayList<>());
    static final Report NEW_REPORT = new Report(1L, "Отправление создано! В отделении с ID = " + POST_OFFICE_1.getId(), 1L);

    static final PostalItem POSTAL_ITEM_POSTCARD = new PostalItem(1L, PostalItem.Type.POSTCARD, 450000, "Address", "Name", PostalItem.Status.ON_THE_WAY, 1L, List.of(NEW_REPORT));
    static final PostalItem POSTAL_ITEM_PACKAGE = new PostalItem(2L, PostalItem.Type.PACKAGE, 450000, "Address", "Name", PostalItem.Status.ON_THE_WAY, 2L, new ArrayList<>());
    static final PostalItem POSTAL_ITEM_LETTER = new PostalItem(3L, PostalItem.Type.LETTER, 450000, "Address", "Name", PostalItem.Status.NEW, new ArrayList<>());

    @Test
    @DisplayName("Создание нового почтового отправления (без начальной точки)")
    void shouldReturnValidItemWhenCreate() {
        when(postalItemRepository.save(NEW_POSTAL_ITEM))
                .thenReturn(NEW_POSTAL_ITEM);

        assertEquals(NEW_POSTAL_ITEM, out.create(NEW_POSTAL_ITEM));

        verify(postalItemRepository, times(1)).save(NEW_POSTAL_ITEM);
    }

    @Test
    @DisplayName("Поиск по ID")
    void shouldReturnCorrectItemById() {
        when(postalItemRepository.findById(1L))
                .thenReturn(Optional.of(POSTAL_ITEM_POSTCARD));

        assertEquals(POSTAL_ITEM_POSTCARD, out.getById(1L));

        verify(postalItemRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Удаление по ID")
    void shouldReturnCorrectMessageWenDeleteById() {
        when(postalItemRepository.findById(1L))
                .thenReturn(Optional.of(POSTAL_ITEM_POSTCARD));

        doNothing().when(postalItemRepository).deleteById(1L);

        assertEquals("Успешно удалено!", out.deleteById(1L));

        verify(postalItemRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Вывод списка всех отправлений")
    void shouldReturnValidListWhenGetAll() {
        when(postalItemRepository.findAll())
                .thenReturn(List.of(POSTAL_ITEM_POSTCARD, POSTAL_ITEM_PACKAGE));

        assertEquals(List.of(POSTAL_ITEM_POSTCARD, POSTAL_ITEM_PACKAGE), out.getAll());

        verify(postalItemRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Вывод списка всех отправлений, находящихся в конткретном почтовом отделении")
    void getAllByPostOfficeId() {
        when(postalItemRepository.getAllByPostOfficeId(1L))
                .thenReturn(List.of(POSTAL_ITEM_POSTCARD, POSTAL_ITEM_PACKAGE));

        assertEquals(List.of(POSTAL_ITEM_POSTCARD, POSTAL_ITEM_PACKAGE), out.getAllByPostOfficeId(1L));

        verify(postalItemRepository, times(1)).getAllByPostOfficeId(1L);
    }

    @Test
    @DisplayName("Обвноление инфо по отправлению")
    void shouldReturnValidItemWhenUpdate() {
        when(postalItemRepository.save(POSTAL_ITEM_PACKAGE))
                .thenReturn(POSTAL_ITEM_PACKAGE);

        assertEquals(POSTAL_ITEM_PACKAGE, out.update(POSTAL_ITEM_PACKAGE));

        verify(postalItemRepository, times(1)).save(POSTAL_ITEM_PACKAGE);
    }

    @Test
    @DisplayName("Выброс исключения, если объект не существует при поиске")
    void shouldThrowRuntimeExceptionWhenItemDoesntExist() {
        when(postalItemRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(RuntimeException.class,()-> out.getById(1L));

        verify(postalItemRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Выброс исключения, если объект не существует при удалении")
    void shouldThrowRuntimeExceptionWhenItemDoesntExistWhenDelete() {
        when(postalItemRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(RuntimeException.class,()-> out.deleteById(1L));

        verify(postalItemRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Возвращает отчёт при первом поступлении отправления в почтовом отделении")
    void shouldUpdateInfoAboutPostalItemWhenMove_IfStatement(){
        when(postalItemRepository.findById(3L))
                .thenReturn(Optional.of(POSTAL_ITEM_LETTER));
        when(postOfficeRepository.findById(2L))
                .thenReturn(Optional.of(POST_OFFICE_2));

        List<Report> reports = POSTAL_ITEM_LETTER.getTrackingStory();
        assertTrue(reports.isEmpty());

        Long lastPoint = POST_OFFICE_2.getId();
        POSTAL_ITEM_LETTER.setStatus(PostalItem.Status.ON_THE_WAY);
        Report report = new Report(1L, "Отправление создано! В отделении с ID = " + lastPoint, POSTAL_ITEM_LETTER.getId());
        updateInfoAfterMoving(report, POSTAL_ITEM_LETTER, POSTAL_ITEM_LETTER.getTrackingStory(), lastPoint);

        assertEquals(report.getMessage(), out.moving(3L, 2L));

    }

    private void updateInfoAfterMoving(Report report, PostalItem item, List<Report> reports, Long officeId) {
        reportRepository.save(report);
        reports.add(report);
        item.setTrackingStory(reports);
        item.setPostOfficeId(officeId);
        postalItemRepository.save(item);
    }
}