package me.dbogda.trackingpostalitemsapplication.service.impl;

import me.dbogda.trackingpostalitemsapplication.model.PostOffice;
import me.dbogda.trackingpostalitemsapplication.repository.PostOfficeRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PostOfficeServiceImplTest {

    @Mock
    PostOfficeRepository postOfficeRepository;

    @InjectMocks
    PostOfficeServiceImpl out;

    static final PostOffice NEW_POST_OFFICE = new PostOffice (450007, "Name", "Address");
    static final PostOffice POST_OFFICE = new PostOffice(1L, 450007, "Name", "Address", new ArrayList<>());
    static final PostOffice POST_OFFICE1 = new PostOffice(2L, 450007, "Name", "Address", new ArrayList<>());


    @Test
    @DisplayName("Создание почтового отделения")
    void shouldReturnValidOfficeWhenCreate() {
        when(postOfficeRepository.save(NEW_POST_OFFICE))
                .thenReturn(NEW_POST_OFFICE);

        assertEquals(NEW_POST_OFFICE, out.create(NEW_POST_OFFICE));

        verify(postOfficeRepository, times(1)).save(NEW_POST_OFFICE);
    }

    @Test
    @DisplayName("Получение инфо почтового отделения по его ID")
    void shouldReturnValidPostOfficeWhenGetById() {
        when(postOfficeRepository.findById(1L))
                .thenReturn(Optional.of(POST_OFFICE));

        assertEquals(POST_OFFICE, out.getById(1L));

        verify(postOfficeRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Удаление почтового отделения по его ID")
    void shouldReturnCorrectMessageWhenDeleteById() {
        when(postOfficeRepository.findById(1L))
                .thenReturn(Optional.of(POST_OFFICE));

        doNothing().when(postOfficeRepository).deleteById(1L);

        assertEquals("Успешно удалено!", out.deleteById(1L));

        verify(postOfficeRepository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("Обновление инфо почтового отделения")
    void shouldReturnValidOfficeWhenUpdate() {
        when(postOfficeRepository.save(POST_OFFICE))
                .thenReturn(POST_OFFICE);

        assertEquals(POST_OFFICE, out.update(POST_OFFICE));

        verify(postOfficeRepository, times(1)).save(POST_OFFICE);
    }

    @Test
    @DisplayName("Получение списка всех почтовых отделений")
    void getAll() {
        when(postOfficeRepository.findAll())
                .thenReturn(List.of(POST_OFFICE, POST_OFFICE1));

        assertEquals(List.of(POST_OFFICE, POST_OFFICE1), out.getAll());

        verify(postOfficeRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Выброс исключения, если объект не существует при поиске")
    void shouldThrowRuntimeExceptionWhenOfficeDoesntExistWhenGetById() {
        when(postOfficeRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(RuntimeException.class,()-> out.getById(1L));

        verify(postOfficeRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Выброс исключения, если объект не существует при удалении")
    void shouldThrowRuntimeExceptionWhenOfficeDoesntExistWhenDelete() {
        when(postOfficeRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(RuntimeException.class,()-> out.deleteById(1L));

        verify(postOfficeRepository, times(1)).findById(1L);
    }
}