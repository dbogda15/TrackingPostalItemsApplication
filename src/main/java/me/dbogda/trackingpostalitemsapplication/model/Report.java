package me.dbogda.trackingpostalitemsapplication.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * Отчёт о перемещении почтового отправления
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String message;

    @Column(name = "postal_item_id")
    private Long postalItemId;

    public Report(String message, Long postalItemId) {
        this.message = message;
        this.postalItemId = postalItemId;
    }
}
