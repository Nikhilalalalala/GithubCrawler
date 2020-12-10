import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;

public class GithubRepoRecordTest {

    @Test
    public void parseNumOfStarsTest() {

        assertEquals(120, GithubRepoRecord.parseNumOfStars("120"));
        assertEquals(12000, GithubRepoRecord.parseNumOfStars("12k"));
        assertEquals(1200, GithubRepoRecord.parseNumOfStars("1.2k"));
        assertThrows( IllegalArgumentException.class , () -> GithubRepoRecord.parseNumOfStars("1.2"));

    }


}
