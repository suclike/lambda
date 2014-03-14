package com.jnape.palatable.lambda.functions.builtin.dyadic;

import com.jnape.palatable.lambda.exceptions.EmptyIterableException;
import com.jnape.palatable.lambda.functions.DyadicFunction;
import com.jnape.palatable.lambda.functions.MonadicFunction;

import java.util.Iterator;

import static com.jnape.palatable.lambda.functions.builtin.triadic.FoldLeft.foldLeft;

public final class ReduceLeft<A> extends DyadicFunction<DyadicFunction<? super A, ? super A, ? extends A>, Iterable<A>, A> {

    @Override
    public final A apply(DyadicFunction<? super A, ? super A, ? extends A> function, Iterable<A> as) {
        final Iterator<A> iterator = as.iterator();

        if (!iterator.hasNext())
            throw new EmptyIterableException();

        return foldLeft(function, iterator.next(), new Iterable<A>() {
            @Override
            public Iterator<A> iterator() {
                return iterator;
            }
        });
    }

    public static <A> ReduceLeft<A> reduceLeft() {
        return new ReduceLeft<A>();
    }

    public static <A> MonadicFunction<Iterable<A>, A> reduceLeft(
            DyadicFunction<? super A, ? super A, ? extends A> function) {
        return ReduceLeft.<A>reduceLeft().partial(function);
    }

    public static <A> A reduceLeft(DyadicFunction<? super A, ? super A, ? extends A> function, Iterable<A> as) {
        return reduceLeft(function).apply(as);
    }
}