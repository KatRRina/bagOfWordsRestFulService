package ru.butenko.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ComparisonTextsDto {
    private String textFirst;
    private String textSecond;
}
