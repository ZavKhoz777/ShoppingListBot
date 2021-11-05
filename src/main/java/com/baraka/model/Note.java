package com.baraka.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

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
    @ManyToOne(fetch = FetchType.LAZY)
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
                ", author=" + author.firstName +
                ", creationDate=" + creationDate +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Note note1 = (Note) o;
        return id.equals(note1.id) &&
                Objects.equals(note, note1.note) &&
                author.equals(note1.author) &&
                creationDate.equals(note1.creationDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, note, creationDate);
    }
}

