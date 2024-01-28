package ru.butenko;

import org.junit.Test;
import ru.butenko.algorithms.BagOfWordsAlgorithms;
import ru.stachek66.nlp.mystem.holding.MyStemApplicationException;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

public class AlgorithmTest {
    @Test
    public void compareTextsMustReturnCorrectAnswer() throws MyStemApplicationException {
        assertEquals(75,
                BagOfWordsAlgorithms.compareTexts("Миша у Клары украл кораллы",
                        "А Клара у Миши украла кларнет",
                Arrays.asList("а", "на", "у", "по", "под", "над", "с"))

        );
    }
    @Test
    public void compareTextWithNumberAndPunctuationMustReturnCorrectAnswer() throws MyStemApplicationException {
        assertEquals(75,
                BagOfWordsAlgorithms.compareTexts("Миша, 43 у Клары украл кораллы!",
                        "А Клара! у Миши 6 украла кларнет",
                        Arrays.asList("а", "на", "у", "по", "под", "над", "с"))

        );
    }
    @Test
    public void compareTextWithMustReturnCorrectAnswer() throws MyStemApplicationException {
        assertEquals(75,
                BagOfWordsAlgorithms.compareTexts("Миша, 43 у Клары украл кораллы!",
                        "А Клара! у Миши 6 украла кларнет",
                        Arrays.asList("а", "на", "у", "по", "под", "над", "с"))

        );
    }
}
