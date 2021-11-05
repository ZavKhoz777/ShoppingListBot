package com.baraka.repository;

import com.baraka.model.Message;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MessageRepository extends CrudRepository<Message, Integer> {

    List<Message> findAllByAuthor_TgId(Integer tg_id);

    List<Message> findAllByAuthor_LastName(String lastName);

}
