package me.dbogda.trackingpostalitemsapplication.service;

import me.dbogda.trackingpostalitemsapplication.model.PostOffice;

import java.util.List;

public interface PostOfficeService {
    /**
     * Создание почтового отделения в БД
     * @param postOffice
     * @return
     */
    PostOffice create(PostOffice postOffice);

    /**
     * Удаление почтового отделения по его ID
     * @param id
     * @return
     */
    String deleteById(Long id);

    /**
     * Обновление почтового отделения по его ID
     * @param postOffice
     * @return
     */
    PostOffice update(PostOffice postOffice);

    /**
     * Получение списка всех почтовых отеделений
     * @return
     */
    List<PostOffice> getAll();

    /**
     * Получение почтового отделения по его ID
     * @param id
     * @return
     */
    PostOffice getById(Long id);
}
