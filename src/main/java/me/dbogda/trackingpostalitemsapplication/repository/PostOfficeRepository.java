package me.dbogda.trackingpostalitemsapplication.repository;

import me.dbogda.trackingpostalitemsapplication.model.PostOffice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostOfficeRepository extends JpaRepository <PostOffice, Long> {
}
