package me.dbogda.trackingpostalitemsapplication.repository;

import me.dbogda.trackingpostalitemsapplication.model.PostalItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.LinkedList;
import java.util.List;

public interface PostalItemRepository extends JpaRepository <PostalItem, Long> {
    List<PostalItem> getAllByPostOfficeId(Long id);
}
