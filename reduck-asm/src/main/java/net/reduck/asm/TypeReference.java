package net.reduck.asm;

import net.reduck.asm.util.Reflections;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;


public abstract class TypeReference<T> implements Comparable<TypeReference<T>> {
    protected final Type _type;

    protected TypeReference() {

        _type = Reflections.getSuperclassTypeParameter(this.getClass());
    }

    public Type getType() {
        return _type;
    }

    /**
     * The only reason we define this method (and require implementation
     * of <code>Comparable</code>) is to prevent constructing a
     * reference without type information.
     */
    @Override
    public int compareTo(TypeReference<T> o) {
        return 0;
    }
    // just need an implementation, not a good one... hence ^^^

    public static void main(String[] args) {
        TypeReference<List<String>> typeReference = new TypeReference<List<String>>() {
        };

        System.out.println(typeReference._type);
    }
}

