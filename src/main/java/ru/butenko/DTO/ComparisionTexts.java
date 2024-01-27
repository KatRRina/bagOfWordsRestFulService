package ru.butenko.DTO;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ComparisionTexts {
    private String textFirst;
    private String textSecond;
}
