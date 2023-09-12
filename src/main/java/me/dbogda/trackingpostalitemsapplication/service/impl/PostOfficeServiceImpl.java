package me.dbogda.trackingpostalitemsapplication.service.impl;

import lombok.AllArgsConstructor;
import me.dbogda.trackingpostalitemsapplication.model.PostOffice;
import me.dbogda.trackingpostalitemsapplication.repository.PostOfficeRepository;
import me.dbogda.trackingpostalitemsapplication.service.PostOfficeService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class PostOfficeServiceImpl implements PostOfficeService {

    PostOfficeRepository postOfficeRepository;

    @Override
    public PostOffice create(PostOffice postOffice) {
        return postOfficeRepository.save(postOffice);
    }

    @Override
    public String deleteById(Long id) {
        String message;
        Optional<PostOffice> office = postOfficeRepository.findById(id);
        if (office.isPresent()){
            postOfficeRepository.deleteById(id);
            message = "Успешно удалено!";
        }
        else throw new RuntimeException("такого отделения не существует!");
        return message;
    }

    @Override
    public PostOffice update(PostOffice postOffice) {
        return postOfficeRepository.save(postOffice);
    }

    @Override
    public List<PostOffice> getAll() {
        return postOfficeRepository.findAll();
    }

    @Override
    public PostOffice getById(Long id) {
        Optional<PostOffice> office = postOfficeRepository.findById(id);
        if (office.isPresent()){
            return office.get();
        }
        else throw new RuntimeException("такого отделения не существует!");
    }
}
