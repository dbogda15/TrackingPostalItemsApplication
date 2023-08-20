package me.dbogda.trackingpostalitemsapplication.service;

import me.dbogda.trackingpostalitemsapplication.model.Report;

import java.util.List;

public interface ReportService {
    /**
     * Создание нового отчёта
     * @param report
     * @return
     */
    Report create(Report report);

    /**
     * Удаление отчёта по его ID
     * @param id
     * @return
     */
    String deleteById(Long id);

    /**
     * Обновление отчёта
     * @param report
     * @return
     */
    Report update(Report report);

    /**
     * Получение списка отчётов по ID почтового отделения
     * @param id
     * @return
     */
    List<Report> getAllByItemId(Long id);
}
