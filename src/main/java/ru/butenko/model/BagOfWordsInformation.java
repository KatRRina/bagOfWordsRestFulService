package ru.butenko.model;

import lombok.*;

import javax.persistence.*;
import java.sql.Date;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class BagOfWordsInformation {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String user_login;

    private String text_first;

    private String text_second;

    private int outer_value;

    private LocalDateTime create_;

}
