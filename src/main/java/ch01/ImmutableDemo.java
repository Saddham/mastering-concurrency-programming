package ch01;

import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ImmutableDemo {
    public static void main(String[] args) {
        /*
        List<String> list = Collections.unmodifiableList(new ArrayList<>(10));
        // Boom!
        list.add("why not?");
        */

        List<String> list2 = ImmutableList.of("a", "b");
        // Boom !
        list2.add("good");



    }
}
