package org.kloud.common.datatypes;

import lombok.EqualsAndHashCode;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

/**
 * Custom datatype that is a wrapper over a list of longs (typically for storing foreign key references/ids)
 */
@EqualsAndHashCode(callSuper = true, doNotUseGetters = true)
public class IdList extends CustomDatatype {

    // TODO: This should not be public
    public final List<Long> backingList = new ArrayList<>();

    public IdList() {

    }

    public IdList(@NotNull Collection<Long> list) {
        backingList.addAll(list);
    }

    public IdList(@NotNull IdList list) {
        backingList.addAll(list.backingList);
    }

    public IdList(Long... values) {
        backingList.addAll(List.of(values));
    }

    @Override
    public String serializeToString() {
        if (backingList.isEmpty()) {
            return "";
        }
        return backingList.stream().reduce("",
                (s, aLong) -> s + "," + aLong,
                (s, s2) -> s + "," + s2).substring(1);
    }

    @Override
    public void deserializeFromString(@Nullable String data) {
        if (data == null || data.isEmpty()) {
            return;
        }
        backingList.addAll(Arrays.stream(data.split(",")).map(Long::valueOf).collect(ArrayList::new, ArrayList::add, ArrayList::addAll));
    }

    public void clear() {
        backingList.clear();
    }

    public boolean add(long id) {
        return backingList.add(id);
    }

    public boolean remove(long id) {
        return backingList.remove(id);
    }

    public boolean contains(long id) {
        return backingList.contains(id);
    }

    public boolean isEmpty() {
        return backingList.isEmpty();
    }

    public int size() {
        return backingList.size();
    }

    @Override
    public String toString() {
        return backingList.toString();
    }
}
