package io.mesoneer.interview_challenges;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;

@Service
public class RangeService {

    /**
     * dynamically add classes implementing Comparable interface
     *
     * @param className
     * @return
     */
    private Class<? extends Comparable<?>> getClassFromString(String className){
        switch (className.toLowerCase()){
            case "integer.class":
                return Integer.class;

            case "string.class":
                return String.class;

            case "double.class":
                return Double.class;

            case "bigdecimal.class":
                return BigDecimal.class;

            case "float.class":
                return Float.class;

            case "biginteger.class":
                return BigInteger.class;

            case "localdate.class":
                return LocalDate.class;

            default: throw new IllegalArgumentException(EnumException.CLASS_NOT_FOUND_EXCEPTION.name());
        }
    }

    public <T extends Comparable<? super T>> Boolean inRange(RangeRequest request) {
        String requestRange = request.getRange();
        String requestValue = request.getValue();
        String requestClazz = request.getClassDefinition();
        Class<T> clazz = (Class<T>) getClassFromString(requestClazz);
        Range<T> range = Range.parse(requestRange, clazz);
        T parsedValue = Range.parseValue(requestValue, clazz);
        return range.contains(parsedValue);
    }
}
