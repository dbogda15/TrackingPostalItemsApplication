package me.dbogda.trackingpostalitemsapplication.repository;

import me.dbogda.trackingpostalitemsapplication.model.Report;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReportRepository extends JpaRepository <Report, Long> {
    List<Report> getAllByPostalItemId(Long id);
}
