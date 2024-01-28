package ru.butenko;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import ru.butenko.dto.ComparisonTextsDto;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ComponentScan("ru.butenko")
@EntityScan("ru.butenko.model")
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@AutoConfigureMockMvc
public class ProcessingControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void processTextPositiveAnswerTest() throws Exception {
        ComparisonTextsDto input = ComparisonTextsDto.builder()
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
        ComparisonTextsDto input = ComparisonTextsDto.builder()
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
        ComparisonTextsDto input = ComparisonTextsDto.builder()
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
