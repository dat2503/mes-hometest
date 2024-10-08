package io.mesoneer.interview_challenges;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;

public class RangeServiceTest {
    private RangeService rangeService;

    @BeforeEach
    public void setUp(){
        rangeService = new RangeService();
    }

    @Test
    public void check_for_a_value_in_range_should_success() {
        // given - precondition or setup
        RangeRequest rangeRequest = new RangeRequest("[5, 7]", "6", "Integer.class");
        // when - action or the behavior being tested
        Boolean isInRange = rangeService.inRange(rangeRequest);
        //then - verify the output
        assertThat(isInRange).isEqualTo(true);
    }
}
