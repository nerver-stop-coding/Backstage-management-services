import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Stream;

public class Try {

    public static <T, R> Function<T, R> of(UncheckedFunction<T, R> mapper, Stream<T> empty) {
        Objects.requireNonNull(mapper);
        return t -> {
            try {
                return mapper.apply(t);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        };
    }


    @FunctionalInterface
    public static interface UncheckedFunction<T, R> {

        R apply(T t) throws Exception;
    }
}

