package com.linq;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public interface IOrderedEnumerable<T> extends Iterable<T> {
    IOrderedEnumerable<T> thenBy(Comparator<T> keySelector);
    IOrderedEnumerable<T> thenByDescending(Comparator<T> keySelector);

}
