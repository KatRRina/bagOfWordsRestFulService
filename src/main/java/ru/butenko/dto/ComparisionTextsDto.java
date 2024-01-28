package ru.butenko.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ComparisionTextsDto {
    private String textFirst;
    private String textSecond;
}
