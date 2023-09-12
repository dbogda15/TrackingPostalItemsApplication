package me.dbogda.trackingpostalitemsapplication.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

/**
 * Почтовое отправление
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class PostalItem {

    public enum Type {
        LETTER,
        PACKAGE,
        PARCEL,
        POSTCARD
    }

    public enum Status {
        NEW,
        ON_THE_WAY,
        DELIVERED
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Type type;
    private Integer recipientsIndex;
    private String recipientsAddress;
    private String recipientsName;
    private Status status;

    @Column(name = "post_office_id")
    private Long postOfficeId;

    @OneToMany(mappedBy = "postalItemId", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Report> trackingStory;

    public PostalItem(Type type, Integer recipientsIndex, String recipientsAddress, String recipientsName) {
        this.type = type;
        this.recipientsIndex = recipientsIndex;
        this.recipientsAddress = recipientsAddress;
        this.recipientsName = recipientsName;
    }

    public PostalItem(Long id, Type type, Integer recipientsIndex, String recipientsAddress, String recipientsName, Status status, List<Report> trackingStory) {
        this.id = id;
        this.type = type;
        this.recipientsIndex = recipientsIndex;
        this.recipientsAddress = recipientsAddress;
        this.recipientsName = recipientsName;
        this.status = status;
        this.trackingStory = trackingStory;
    }

    public PostalItem(Type type, Integer recipientsIndex, String recipientsAddress, String recipientsName, Status status, Long postOfficeId) {
        this.type = type;
        this.recipientsIndex = recipientsIndex;
        this.recipientsAddress = recipientsAddress;
        this.recipientsName = recipientsName;
        this.status = status;
        this.postOfficeId = postOfficeId;
    }


}
