package com.baraka.repository;

import com.baraka.model.Note;
import org.springframework.data.repository.CrudRepository;

public interface NotesRepository extends CrudRepository<Note, Long> {
}
