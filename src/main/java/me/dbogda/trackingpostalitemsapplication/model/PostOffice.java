package me.dbogda.trackingpostalitemsapplication.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

/**
 * Почтовое отделение
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class PostOffice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer index;
    private String name;
    private String recipientsAddress;

    @OneToMany(mappedBy = "postOfficeId", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<PostalItem> postalItems;
    public PostOffice(Integer index, String name, String recipientsAddress) {
        this.index = index;
        this.name = name;
        this.recipientsAddress = recipientsAddress;
    }
}
