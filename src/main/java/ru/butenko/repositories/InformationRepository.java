package ru.butenko.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.butenko.model.BagOfWordsInformation;
@Repository
public interface InformationRepository extends JpaRepository<BagOfWordsInformation, Long> {

}
