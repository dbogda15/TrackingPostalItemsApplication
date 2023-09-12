package me.dbogda.trackingpostalitemsapplication.service.impl;

import lombok.AllArgsConstructor;
import me.dbogda.trackingpostalitemsapplication.model.Report;
import me.dbogda.trackingpostalitemsapplication.repository.ReportRepository;
import me.dbogda.trackingpostalitemsapplication.service.ReportService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ReportServiceImpl implements ReportService {

    ReportRepository reportRepository;
    @Override
    public Report create(Report report) {
        return reportRepository.save(report);
    }

    @Override
    public String deleteById(Long id) {
        String message;
        Optional<Report> report = reportRepository.findById(id);
        if (report.isPresent()){
            reportRepository.deleteById(id);
            message = "Успешно удалено!";
        }
        else throw new RuntimeException("такого трека не существует!");
        return message;
    }

    @Override
    public Report update(Report report) {
        return reportRepository.save(report);
    }

    @Override
    public List<Report> getAllByItemId(Long id) {
        return reportRepository.getAllByPostalItemId(id);
    }
}
