package com.baraka.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "messages")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer id;

    @Column(name = "text")
    String text;

    @JoinColumn(name = "customer_id")
    @ManyToOne(fetch = FetchType.EAGER)
    Customer author;

    @Column(name = "creation_date", updatable = false)
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date creationDate = new Date();

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", creationDate=" + creationDate +
                '}';
    }
}
