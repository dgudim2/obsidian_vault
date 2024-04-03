package org.kloud.common;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class IdList extends CustomDatatype {

    private final List<Long> backingList = new ArrayList<>();

    public IdList() {

    }

    public IdList(@NotNull Collection<Long> list) {
        backingList.addAll(list);
    }

    public IdList(Long... values) {
        backingList.addAll(List.of(values));
    }

    @Override
    public String serializeToString() {
        return backingList.stream().reduce("",
                (s, aLong) -> s + "," + aLong,
                (s, s2) -> s + "," + s2).substring(1);
    }

    @Override
    public void deserializeFromString(String data) {
        backingList.addAll(Arrays.stream(data.split(",")).map(Long::valueOf).collect(ArrayList::new, ArrayList::add, ArrayList::addAll));
    }

    public boolean contains(long id) {
        return backingList.contains(id);
    }
}
