package com.baraka.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.engine.internal.Cascade;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.Instant;
import java.util.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="customers")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",unique = true, nullable = false)
    Integer id;

    @Column(name = "tg_id")
    Integer tgId;

    @Column(name = "chat_id")
    Integer chat_id;

    @Column(name="first_name")
    String firstName;

    @Column(name="last_name")
    String lastName;

    @Column(name = "language_code")
    String languageCode;

    @Column(name = "creation_date", updatable = false)
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date creationDate = new Date();

    @Column(name = "update_date", updatable = true)
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date recordDate = new Date();

    @Column(name = "messages")
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "author",cascade = CascadeType.ALL, orphanRemoval = true)
    Set<Message> messages = new HashSet<>();

    @Column(name = "notes")
    @OneToMany(fetch = FetchType.EAGER,mappedBy = "author", cascade = CascadeType.ALL, orphanRemoval = true)
    Set<Note> notes = new HashSet<>();
//
////    @Column(name = "notes")
////    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "customers", cascade = CascadeType.ALL)
////    List<Note> notes = new LinkedList<>();

    public Customer(Integer tg_id, Long chat_id) {
        this.tgId = tg_id;
        this.chat_id = chat_id.intValue();
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", tgId=" + tgId +
                ", chat_id=" + chat_id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", languageCode='" + languageCode + '\'' +
                ", creationDate=" + creationDate +
                ", recordDate=" + recordDate +
                ", messages=" + messages +
                ", notes=" + notes +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return id.equals(customer.id) &&
                tgId.equals(customer.tgId) &&
                chat_id.equals(customer.chat_id) &&
                Objects.equals(firstName, customer.firstName) &&
                Objects.equals(lastName, customer.lastName) &&
                creationDate.equals(customer.creationDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, tgId, chat_id, firstName, lastName, creationDate);
    }
}
