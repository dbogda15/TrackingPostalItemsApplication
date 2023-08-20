package me.dbogda.trackingpostalitemsapplication.service.impl;

import lombok.AllArgsConstructor;
import me.dbogda.trackingpostalitemsapplication.model.PostOffice;
import me.dbogda.trackingpostalitemsapplication.model.PostalItem;
import me.dbogda.trackingpostalitemsapplication.model.Report;
import me.dbogda.trackingpostalitemsapplication.repository.PostOfficeRepository;
import me.dbogda.trackingpostalitemsapplication.repository.PostalItemRepository;
import me.dbogda.trackingpostalitemsapplication.repository.ReportRepository;
import me.dbogda.trackingpostalitemsapplication.service.PostalItemService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@AllArgsConstructor
@Service
public class PostalItemServiceImpl implements PostalItemService {

    private final PostalItemRepository postalItemRepository;
    private final PostOfficeRepository postOfficeRepository;
    private final ReportRepository reportRepository;

    @Override
    public PostalItem create(PostalItem postalItem) {
        return postalItemRepository.save(postalItem);
    }

    @Override
    public String deleteById(Long id) {
        String message;
        Optional<PostalItem> item = postalItemRepository.findById(id);
        if (item.isPresent()){
            postalItemRepository.deleteById(id);
            message = "Успешно удалено!";
        }
        else throw new RuntimeException("такого отправления не существует!");
        return message;
    }

    @Override
    public PostalItem update(PostalItem postalItem) {
        return postalItemRepository.save(postalItem);
    }

    @Override
    public PostalItem getById(Long id) {
        Optional<PostalItem> item = postalItemRepository.findById(id);
        if (item.isPresent()){
            return item.get();
        }
        else throw new RuntimeException("такого отправления не существует!");
    }

    @Override
    public List<PostalItem> getAll() {
        return postalItemRepository.findAll();
    }

    @Override
    public List<PostalItem> getAllByPostOfficeId(Long id) {
        return postalItemRepository.getAllByPostOfficeId(id);
    }

    @Override
    public String moving(Long itemId, Long officeId) {

        PostalItem item = getById(itemId);

        if (postOfficeRepository.findById(officeId).isEmpty()){
            throw new IllegalArgumentException("Проверьте ID почтового отделения!");
        }

        PostOffice office = postOfficeRepository.findById(officeId).get();

        List<Report> reports = item.getTrackingStory();
        Long lastPoint;

        if (reports.isEmpty()){
            lastPoint = officeId;
            Report report = new Report("Отправление создано! В отделении с ID = " + lastPoint, itemId);
            item.setStatus(PostalItem.Status.ON_THE_WAY);
            updateInfoAfterMoving(report, item, reports, lastPoint);
        }
        else if (!Objects.equals(item.getPostOfficeId(), officeId)){
            lastPoint = item.getPostOfficeId();
            Report report = new Report("Отправление покинуло почтовое отделение с ID = " + lastPoint + " и направляется к отделению с ID = " + officeId, itemId);
            item.setStatus(PostalItem.Status.ON_THE_WAY);
            updateInfoAfterMoving(report, item, reports, officeId);
        }
        deliveringChecking(item, office);
        return reports.get(reports.size()-1).getMessage();
    }


    private void updateInfoAfterMoving (Report report, PostalItem item, List<Report> reports, Long officeId) {
        reportRepository.save(report);
        reports.add(report);
        item.setTrackingStory(reports);
        item.setPostOfficeId(officeId);
        postalItemRepository.save(item);
    }

    private void deliveringChecking(PostalItem item, PostOffice office){
        if (item.getRecipientsIndex().equals(office.getIndex())){
            Report report = new Report("Отправление прибыло в нужное отделение! Можно забирать из отделения: " + office.getName() + " " + office.getIndex(), item.getId());
            item.setStatus(PostalItem.Status.DELIVERED);
            updateInfoAfterMoving(report, item, item.getTrackingStory(), office.getId());
        }
    }
}
