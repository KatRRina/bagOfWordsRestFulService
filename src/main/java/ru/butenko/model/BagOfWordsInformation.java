package ru.butenko.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Table(name="information")
public class BagOfWordsInformation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String user_login;

    private String text_first;

    private String text_second;

    private int outer_value;

    private LocalDateTime create_;

}
