package io.mesoneer.interview_challenges;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.util.function.Function;

import static org.assertj.core.api.Assertions.*;

public class RangeTest {

  @Test
  public void should_create_range() {
    Range range = Range.of(5, 50);
    assertThat(range.lowerbound()).isEqualTo(5);
    assertThat(range.upperbound()).isEqualTo(50);
  }

  @Test
  public void should_throw_error__when_create_with_lowerBound_bigger_than_upperbound() {
    try {

      Range.of(500, 1);
      fail("Should not allow creating a invalid Range");
    } catch(IllegalArgumentException e) {
      // pass
    }
  }

  @Test
  public void closed_range_should_contain_both_bounds_and_all_elements_in_between() {
    Range closedRange = Range.of(5, 50);

    assertThat(closedRange.contains(Integer.MIN_VALUE)).isEqualTo( false);
    assertThat(closedRange.contains(4)).isEqualTo( false);

    assertThat(closedRange.contains(5)).isEqualTo( true);

    assertThat(closedRange.contains(42)).isEqualTo( true);

    assertThat(closedRange.contains(50)).isEqualTo( true);

    assertThat(closedRange.contains(10000)).isEqualTo( false);
    assertThat(closedRange.contains(Integer.MAX_VALUE)).isEqualTo( false);
  }

  @Test
  public void range_should_be_state_independent() {
    Range range1 = Range.of(5, 10);
    Range range2 = Range.of(11, 20);

    assertThat(range1.contains(10)).isEqualTo( true);
    assertThat(range2.contains(10)).isEqualTo( false);
  }

  @Test
  public void open_range_exclude_both_bounds(){
    Range openRange = Range.open(5, 7);

    assertThat(openRange.contains(5)).isEqualTo(false);
    assertThat(openRange.contains(7)).isEqualTo(false);

    assertThat(openRange.contains(6)).isEqualTo(true);
  }
  @Test
  public void closed_range_should_include_both_bounds(){
    Range closedRange = Range.closed(5, 7);

    assertThat(closedRange.contains(5)).isEqualTo(true);
    assertThat(closedRange.contains(7)).isEqualTo(true);

    assertThat(closedRange.contains(6)).isEqualTo(true);
  }

  @Test
  public void open_closed_range_should_exclude_lowerBound_but_include_upperBound(){
    Range openClosedRange = Range.openClosed(5, 7);

    assertThat(openClosedRange.contains(5)).isEqualTo(false);
    assertThat(openClosedRange.contains(7)).isEqualTo(true);

    assertThat(openClosedRange.contains(6)).isEqualTo(true);
  }

  @Test
  public void closed_open_range_should_include_lowerBound_but_exclude_upperBound(){
    Range closedOpenRange = Range.closedOpen(5, 7);

    assertThat(closedOpenRange.contains(5)).isEqualTo(true);
    assertThat(closedOpenRange.contains(7)).isEqualTo(false);

    assertThat(closedOpenRange.contains(6)).isEqualTo(true);
  }

  @Test
  public void range_should_work_on_String_type(){
    Range<String> text = Range.open("abc", "xyz");

    assertThat(text.contains("abc")).isEqualTo(false);
    assertThat(text.contains("xyz")).isEqualTo(false);

    assertThat(text.contains("abd")).isEqualTo(true);
  }

  @Test
  public void range_should_work_on_big_decimal_type(){
    Range<BigDecimal> decimals = Range.open(
            new BigDecimal("1.32432"),
            new BigDecimal("1.324323423423423423423"));

    assertThat(decimals.contains(new BigDecimal("1.32432"))).isEqualTo(false);
    assertThat(decimals.contains(new BigDecimal("1.324323423423423423423"))).isEqualTo(false);

    assertThat(decimals.contains(new BigDecimal("1.324321"))).isEqualTo(true);
  }

  @Test
  public void range_should_work_on_local_date_type(){
    Range<LocalDate> dates = Range.closed(
            LocalDate.of(2016, Month.SEPTEMBER, 11),
            LocalDate.of(2017, Month.JUNE, 30));

    assertThat(dates.contains(LocalDate.of(2016, Month.SEPTEMBER, 11)))
            .isEqualTo(true);
      assertThat(dates.contains(LocalDate.of(2017, Month.JUNE, 30)))
            .isEqualTo(true);
      assertThat(dates.contains(LocalDate.of(2016, Month.SEPTEMBER, 12)))
            .isEqualTo(true);
  }

  @Test
  public void less_than_range_should_exclude_upperBound(){
    Range<Integer> lessThanFive = Range.lessThan(5);

    assertThat(lessThanFive.contains(5)).isEqualTo(false);
    assertThat(lessThanFive.contains(-9000)).isEqualTo(true);
  }

  @Test
  public void at_least_range_should_include_lowerBound(){
    Range<Integer> atLeastFive = Range.atLeast(5);

    assertThat(atLeastFive.contains(5)).isEqualTo(true);
    assertThat(atLeastFive.contains(4)).isEqualTo(false);
  }

  @Test
  public void at_most_range_should_include_upperBound(){
    Range<Integer> atMostFive = Range.atMost(5);

    assertThat(atMostFive.contains(5)).isEqualTo(true);
    assertThat(atMostFive.contains(-234234)).isEqualTo(true);
    assertThat(atMostFive.contains(6)).isEqualTo(false);
  }

  @Test void greater_than_range_should_exclude_lowerBound(){
    Range<LocalDate> afterEpoch = Range.greaterThan(
            LocalDate.of(1900, Month.JANUARY, 1));

    assertThat(afterEpoch.contains(LocalDate.of(2016, Month.JULY, 28))).isEqualTo(true);
    assertThat(afterEpoch.contains(LocalDate.of(1750, Month.JANUARY, 1))).isEqualTo(false);
  }

  @Test
  void all_range_should_include_everything(){
    Range<String> all = Range.all();

    assertThat(all.contains("anything")).isEqualTo(true);
    assertThat(all.contains("")).isEqualTo(true);
    assertThat(all.contains(null)).isEqualTo(true);
  }

  @Test
  public void to_string_should_represent_type_and_bounds(){
    Range<Integer> lessThan100 = Range.lessThan(100);
    assertThat(lessThan100.toString()).isEqualTo("[Infinitive, 100)");

    Range<LocalDate> within2020 = Range.closed(
            LocalDate.of(2020, Month.JANUARY, 1),
            LocalDate.of(2020, Month.DECEMBER, 31));
    assertThat(within2020.toString()).isEqualTo("[2020-01-01, 2020-12-31]");
  }

  @DisplayName("Test for challenge 6")
  @Test
  public void parse_should_return_original_range_from_string_and_class_type(){
    Range<Integer> range = Range.closed(5,7);
    String rangeString = range.toString();
    Range<Integer> parsedRangeClass = Range.parse(rangeString, Integer.class);
    assertThat(parsedRangeClass.contains(5)).isEqualTo(true);
  }

  @DisplayName("Test for challenge 6")
  @Test
  public void parse_should_return_original_range_from_string_and_function(){
    Range<Integer> range = Range.closed(5,7);
    String rangeString = range.toString();
    Function<String, Integer> intFunction = Integer::valueOf;
    Range<Integer> parsedRangeFunction = Range.parse(rangeString, intFunction);
    assertThat(parsedRangeFunction.contains(5)).isEqualTo(true);
  }

  @DisplayName("Test for challenge 6")
  @Test
  public void parse_should_apply_to_value_on_class_type_to_check_for_range(){
    Range<Integer> range = Range.closed(5, 7);
    String value = "6";
    Integer parsedValue = Range.parseValue(value, Integer.class);
    assertThat(range.contains(parsedValue)).isEqualTo(true);
  }

}
