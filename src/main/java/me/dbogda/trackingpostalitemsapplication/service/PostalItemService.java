package me.dbogda.trackingpostalitemsapplication.service;

import me.dbogda.trackingpostalitemsapplication.model.PostalItem;
import me.dbogda.trackingpostalitemsapplication.model.Report;

import java.util.LinkedList;
import java.util.List;

public interface PostalItemService {
    /**
     * Создание почтового отправления без привязки к почтовому отделению
     * @param postalItem
     * @return
     */
    PostalItem create(PostalItem postalItem);

    /**
     * Удаление отправления по ID
     * @param id
     * @return
     */
    String deleteById (Long id);

    /**
     *Обновление информации об отправлении
     * @param postalItem
     * @return
     */
    PostalItem update(PostalItem postalItem);

    /**
     * Получение информации об отправлении по ID
     * @param id
     * @return
     */
    PostalItem getById(Long id);

    /**
     * Получение информаии о всех отправлениях
     * @return
     */
    List<PostalItem> getAll();

    /**
     * Получение информации о перемещениях почтовго отправления
     * @param id
     * @return
     */
    List<PostalItem> getAllByPostOfficeId(Long id);

    /**
     * Перемещение почтового отравления между отделениями
     * @param itemId
     * @param officeId
     * @return
     */
    String moving(Long itemId, Long officeId);
}
