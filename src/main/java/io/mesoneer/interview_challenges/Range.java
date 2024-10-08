package io.mesoneer.interview_challenges;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class Range <T extends Comparable<? super T>>{
  private final T lowerBound;
  private final T upperBound;
  private EnumType type;

  /**
   * for extension purpose, later on we might want to add other classes to parse method
   *
   */
  private static final  Map<Class<? extends Comparable<?>>, Function<String, ?>> PARSERS = new HashMap<>();

  static {
    PARSERS.put(BigDecimal.class, BigDecimal::new);
    PARSERS.put(Integer.class, Integer::valueOf);
    PARSERS.put(Long.class, Long::valueOf);
    PARSERS.put(Float.class, Float::valueOf);
    PARSERS.put(BigInteger.class, BigInteger::new);
    PARSERS.put(String.class, String::valueOf);
    PARSERS.put(Double.class, Double::new);
    PARSERS.put(LocalDate.class, LocalDateFormat::parseDate);

  }

  /**
   * Constructor is private BY DESIGN.
   *
   * TODO: Change the constructor to meet your requirements.
   */
  private Range(T lowerBound, T upperBound){
    checkValid(lowerBound, upperBound);
    this.lowerBound = lowerBound;
    this.upperBound = upperBound;
  }

  private Range(T lowerBound, T upperBound, EnumType type){
    checkValid(lowerBound, upperBound);
    this.lowerBound = lowerBound;
    this.upperBound = upperBound;
    this.type = type;
  }
  private void checkValid(T lowerBound, T upperBound) {
    if(lowerBound != null
            && upperBound != null
            && lowerBound.compareTo(upperBound) > 0){
      throw new IllegalArgumentException(EnumException.INVALID_BOUND_EXCEPTION.name());
    }
  }

  /**
   * Creates a new <b>closed</b> {@code Range} that includes both bounds.
   */
  public static <T extends Comparable<? super T>> Range<T> closed(T lowerBound, T upperBound){
    return new Range<T>(lowerBound, upperBound, EnumType.CLOSED);
  }public static <T extends Comparable<? super T>> Range<T> open(T lowerBound, T upperBound){
    return new Range<T>(lowerBound, upperBound, EnumType.OPEN);
  }public static <T extends Comparable<? super T>> Range<T> openClosed(T lowerBound, T upperBound){
    return new Range<T>(lowerBound, upperBound, EnumType.OPEN_CLOSED);
  }public static <T extends Comparable<? super T>> Range<T> closedOpen(T lowerBound, T upperBound){
    return new Range<T>(lowerBound, upperBound, EnumType.CLOSED_OPEN);
  }
  public static <T extends Comparable<? super T>> Range<T> of(T lowerBound, T upperBound){
    return  closed(lowerBound, upperBound);
  }

  public static <T extends Comparable<? super T>> Range<T> lessThan(T upperBound){
    return new Range<T>(null, upperBound, EnumType.LESS_THAN);
  }

  public static <T extends Comparable<? super T>> Range<T> atLeast(T lowerBound){
    return new Range<T>(lowerBound, null, EnumType.AT_LEAST);
  }

  public static <T extends Comparable<? super T>> Range<T> atMost(T upperBound){
    return new Range<T>(null, upperBound, EnumType.AT_MOST);
  }

  public static <T extends Comparable<? super T>> Range<T> greaterThan(T lowerBound){
    return new Range<T>(lowerBound,null, EnumType.GREATER_THAN);
  }

  public static <T extends Comparable<? super T>> Range<T> all(){
    return new Range<T>(null,null, EnumType.ALL);
  }


  /**
   * Returns {@code true} on if the given {@code value} is contained in this
   * {@code Range}.
   */
  public boolean contains(T value) {
    switch (this.type){
      case OPEN:
        return this.lowerBound.compareTo(value) < 0
              && 0 < this.upperBound.compareTo(value);

      case CLOSED:
        return this.lowerBound.compareTo(value) <= 0
              && 0 <= this.upperBound.compareTo(value);

      case OPEN_CLOSED:
        return this.lowerBound.compareTo(value) < 0
              && 0 <= this.upperBound.compareTo(value);

      case CLOSED_OPEN:
        return this.lowerBound.compareTo(value) <= 0
              && 0 < this.upperBound.compareTo(value);

      case LESS_THAN:
        return this.upperBound.compareTo(value) > 0;

      case AT_LEAST:
        return this.lowerBound.compareTo(value) <= 0;

      case AT_MOST:
        return this.upperBound.compareTo(value) >= 0;

      case GREATER_THAN:
        return this.lowerBound.compareTo(value) < 0;

      case ALL: return true;
    }
    return false;
  }

  /**
   * Returns the {@code lowerbound} of this {@code Range}.
   */
  public T lowerbound() {
    return this.lowerBound;
  }

  /**
   * Returns the {@code upperbound} of this {@code Range}.
   */
  public T upperbound() {
    return this.upperBound;
  }
  @Override
  public String toString(){
    String result;

    String infinitive = EnumType.EnumRange.INFINITIVE.getValue();

    switch (this.type){
      case OPEN:
        result = "(" + lowerBound.toString() + ", " + upperBound.toString() + ")";
        break;

      case CLOSED:
        result = "[" + lowerBound.toString() + ", " + upperBound.toString() + "]";
        break;

      case OPEN_CLOSED:
        result = "(" + lowerBound.toString() + ", " + upperBound.toString() + "]";
        break;

      case CLOSED_OPEN:
        result = "[" + lowerBound.toString() + ", " + upperBound.toString() + ")";
        break;

      case LESS_THAN:
        result = "[" + infinitive + ", " + upperBound.toString() + ")";
        break;

      case AT_LEAST:
        result = "[" + lowerBound.toString() + ", " + infinitive + "]";
        break;

      case AT_MOST:
        result = "[" + infinitive + ", " + upperBound.toString() + "]";
        break;

      case GREATER_THAN:
        result = "(" + lowerBound.toString() + ", " + infinitive + "]";
        break;

      case ALL:
        result = "[" + infinitive + ", " + infinitive + "]";
        break;

      default:
        result = "Range{" +
                "lowerBound=" + lowerBound +
                ", upperBound=" + upperBound +
                ", type='" + type + '\'' +
                '}';
    }
    return result;
  }

  /**
   * Parse bound by class name
   *
   * @param range
   * @param clazz
   * @return
   * @param <T>
   */
  public static <T extends Comparable<? super T>> Range<T> parse(String range, Class<?> clazz){
    Function<String, ?> parser = PARSERS.get(clazz);
    if( parser != null){
      return parse(range, (Function<String, T>) parser);
    }
    else{
      throw new IllegalArgumentException(EnumException.CLASS_NOT_FOUND_EXCEPTION.name());
    }
  }

  /**
   * Parse value to check if in range
   *
   * @param value
   * @param clazz
   * @return
   * @param <T>
   */
  public static <T extends Comparable<? super T>> T parseValue(String value, Class<? extends Comparable<?>> clazz){
    Function<String, ? extends Comparable<?>> parser = (Function<String, ? extends Comparable<?>>) PARSERS.get(clazz);
    return (T) parser.apply(value);
  }

  /**
   * Parse range by class function
   *
   * @param range
   * @param parseFunction
   * @return
   * @param <T>
   */
  public static <T extends Comparable<? super T>> Range<T> parse(String range, Function<String, T> parseFunction) throws IllegalArgumentException {
    EnumType type = getRange(range);
    if(type.equals(EnumType.INVALID_TYPE)){
      throw new IllegalArgumentException(EnumException.INVALID_BOUND_EXCEPTION.name());
    }

    String lowerBound = range.substring(1, range.indexOf(","));
    String upperBound = range.substring(range.indexOf(" ") + 1, range.length() - 1);

    try{
      return new Range<T>(parseBound(lowerBound, parseFunction),
              parseBound(upperBound, parseFunction),
              type);
    }catch (RuntimeException ex){
      throw new RuntimeException(EnumException.INVALID_BOUND_EXCEPTION.name());
    }
  }

  /**
   * Apply function to parse bound
   *
   * @param bound
   * @param parseFunction
   * @return
   * @param <T>
   */
  private static <T extends Comparable<? super T>> T parseBound(String bound, Function<String, T> parseFunction){
    if(EnumType.EnumRange.INFINITIVE.toString()
            .equalsIgnoreCase(bound)){
      return null;
    }
    return parseFunction.apply(bound);
  }

  /**
   * Get range by string
   *
   * @param range
   * @return
   */
  private static EnumType getRange(String range){
    if (range.startsWith("[Infinitive")
            && range.endsWith("Infinitive]"))
      return EnumType.ALL;

    else if (range.startsWith("[Infinitive")
            && range.endsWith("]"))
      return EnumType.AT_MOST;

    else if (range.startsWith("[Infinitive")
            && range.endsWith(")"))
      return EnumType.LESS_THAN;

    else if (range.startsWith("[")
              && range.endsWith("Infinitive]"))
      return EnumType.AT_LEAST;

    else if (range.startsWith("(")
              && range.endsWith("Infinitive]"))
      return EnumType.GREATER_THAN;

    else if (range.startsWith("[")
            && range.endsWith("]"))
      return EnumType.CLOSED;

    else if (range.startsWith("(")
            && range.endsWith(")"))
      return EnumType.OPEN;

    else if(range.startsWith("(")
            && range.endsWith("]"))
      return EnumType.OPEN_CLOSED;

    else if(range.startsWith("[")
            && range.endsWith(")"))
      return EnumType.CLOSED_OPEN;

    return EnumType.INVALID_TYPE;
  }
}
