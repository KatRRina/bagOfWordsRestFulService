package ru.butenko;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import ru.butenko.dto.ComparisionTextsDto;
import ru.butenko.dto.ResponseDataDto;
import ru.butenko.dto.UserDto;
import ru.butenko.enums.Roles;
import ru.butenko.model.StopWord;
import ru.butenko.model.User;
import ru.butenko.repositories.InformationRepository;
import ru.butenko.repositories.StopWordRepository;
import ru.butenko.repositories.UserRepository;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ComponentScan("ru.butenko")
@EntityScan("ru.butenko.model")
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@AutoConfigureMockMvc
public class ProcessinControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void processTextPositiveAnswerTest() throws Exception {
        ComparisionTextsDto input = ComparisionTextsDto.builder()
                .textSecond("первый у текст")
                .textFirst("второй текст")
                .build();

        mockMvc.perform(
                        post("/bagOfWords")
                                .content(objectMapper.writeValueAsString(input))
                                .contentType(MediaType.APPLICATION_JSON)
                                .with(httpBasic("admin", "asdf2024"))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.percentageOfCompliance").value(50));


    }
    @Test
    public void processTextWithLongLengthTextTest() throws Exception {
        ComparisionTextsDto input = ComparisionTextsDto.builder()
                .textSecond("первый у текст воаптавотилоевкачсмтидловаебчсмт илдовабчсьмтидлобьеватчсм вачбсмтрилобчсмтилобчастмиб табмит батмил обьатсмлиоб тчбасмирт лабсмтило брчсаимло б")
                .textFirst("второй текст")
                .build();

        mockMvc.perform(
                        post("/bagOfWords")
                                .content(objectMapper.writeValueAsString(input))
                                .contentType(MediaType.APPLICATION_JSON)
                                .with(httpBasic("admin", "asdf2024"))
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.exceptionMessage").value("The second text exceeds the allowed length value"));


    }
    @Test
    public void processTextWithIncorrectMeaningTextTest() throws Exception {
        ComparisionTextsDto input = ComparisionTextsDto.builder()
                .textSecond("текст dkjgne 34 дань")
                .textFirst("второй текст")
                .build();

        mockMvc.perform(
                        post("/bagOfWords")
                                .content(objectMapper.writeValueAsString(input))
                                .contentType(MediaType.APPLICATION_JSON)
                                .with(httpBasic("admin", "asdf2024"))
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.exceptionMessage").value("The wrong meaning, the text must consist of Russian letters, punctuation marks and numbers."));


    }
}
