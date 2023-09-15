package net.reduck.jpa;

import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.lang.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

/**
 * @author Gin
 * @since 2023/5/19 09:32
 */
@RequiredArgsConstructor
public class InsertSqlBuilder<T> {

    private StringBuilder sb = new StringBuilder();

    private final String insertColumns;
    private final Class<T> DomainClass;

    private final List<T> persistDataList = new ArrayList<>();

    private final int batchSize;

    public InsertSqlBuilder<T> append(T entity) {
        persistDataList.add(entity);
        return this;
    }

    public InsertSqlBuilder<T> append(Collection<T> collection) {
        persistDataList.addAll(collection);
        return this;
    }

    @Value(staticConstructor = "of")
    static class Escaper {
        static Escaper DEFAULT =  Escaper.of('\\');
        private static final List<String> TO_REPLACE = Arrays.asList("_", "%");

        char escapeCharacter;

        /**
         * Escapes all special like characters ({@code _}, {@code %}) using the configured escape character.
         *
         * @param value may be {@literal null}.
         * @return
         */
        @Nullable
        public String escape(@Nullable String value) {

            return value == null //
                    ? null //
                    : Stream.concat(Stream.of(String.valueOf(escapeCharacter)), TO_REPLACE.stream()) //
                    .reduce(value, (it, character) -> it.replace(character, this.escapeCharacter + character));
        }
    }
}
