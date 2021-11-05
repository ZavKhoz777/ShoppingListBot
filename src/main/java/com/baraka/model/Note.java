package com.baraka.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Table(name = "note")
@Data
@Entity
public class Note {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer id;

    @Column(name = "thing")
    String note;

    Integer count;

    @Column(name = "status")
    boolean done;

    @JoinColumn(name = "customer_id")
    @ManyToOne(fetch = FetchType.EAGER)
    Customer author;

    @Column(name = "creation_date", updatable = false)
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date creationDate = new Date();

    @Override
    public String toString() {
        return "Note{" +
                "id=" + id +
                ", note='" + note + '\'' +
                ", count=" + count +
                ", done=" + done +
                ", author=" + author +
                ", creationDate=" + creationDate +
                '}';
    }
}

