package ru.butenko.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.butenko.model.StopWord;

public interface StopWordRepository extends JpaRepository<StopWord, Long> {
}
