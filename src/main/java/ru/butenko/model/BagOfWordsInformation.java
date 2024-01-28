package ru.butenko.model;

import lombok.*;

import jakarta.persistence.*;
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
    @Column
    private String user_login;
    @Column
    private String text_first;
    @Column
    private String text_second;
    @Column
    private int outer_value;
    @Column
    private LocalDateTime create_;

}
