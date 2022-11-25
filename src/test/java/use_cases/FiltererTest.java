package use_cases;

import static org.junit.jupiter.api.Assertions.*;

import entities.Meeting;
import entities.Timetable;
import org.junit.jupiter.api.Test;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;


class FiltererTest {
    private static ArrayList<Timetable> getTimetables() {

        ArrayList<Meeting> example_meetings = new ArrayList<>();

        Timetable timetable1 = new Timetable(example_meetings, new HashSet<>(Arrays.asList("Tag1", "Tag2", "Tag7")));
        Timetable timetable2 = new Timetable(example_meetings, new HashSet<>(Arrays.asList("Tag1", "Tag3", "Tag7")));
        Timetable timetable3 = new Timetable(example_meetings, new HashSet<>(Arrays.asList("Tag3", "Tag3", "Tag7")));

        return new ArrayList<>(Arrays.asList(timetable1, timetable2, timetable3));
    }

    @Test
    // Test the filterer using 1 tag, that should return 2 timetables
    public void TestFilterReturnTwo() {
        ArrayList<Timetable> example_timetables = getTimetables();
        HashSet<String> desired_tags = new HashSet<>();
        desired_tags.add("Tag1");
        ArrayList<Timetable> filtered = Filterer.filterAll(example_timetables, desired_tags);
        boolean res = filtered.equals(example_timetables.subList(0, 2));
        assertTrue(res);
    }

    @Test
    // Test the filterer using 1 tag, that should return 1 timetable
    public void TestFilterReturnOne() {
        ArrayList<Timetable> example_timetables = getTimetables();
        HashSet<String> desired_tags = new HashSet<>();
        desired_tags.add("Tag2");
        ArrayList<Timetable> filtered = Filterer.filterAll(example_timetables, desired_tags);
        boolean res = filtered.equals(example_timetables.subList(0, 1));
        assertTrue(res);
    }

    @Test
    // Test the filterer using 1 tag, that should return 3 timetables
    public void TestFilterReturnThree() {
        ArrayList<Timetable> example_timetables = getTimetables();
        HashSet<String> desired_tags = new HashSet<>();
        desired_tags.add("Tag7");
        ArrayList<Timetable> filtered = Filterer.filterAll(example_timetables, desired_tags);
        boolean res = filtered.equals(example_timetables);
        assertTrue(res);
    }

    @Test
    // Test the filterer using 2 tags, that should return 2 timetables
    public void TestTwoTags() {
        ArrayList<Timetable> example_timetables = getTimetables();
        HashSet<String> desired_tags = new HashSet<>();
        desired_tags.add("Tag7");
        desired_tags.add("Tag1");
        ArrayList<Timetable> filtered = Filterer.filterAll(example_timetables, desired_tags);
        boolean res = filtered.equals(example_timetables.subList(0, 2));
        assertTrue(res);
    }


}