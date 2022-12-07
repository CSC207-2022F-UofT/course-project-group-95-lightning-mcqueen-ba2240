package use_cases;

import entities.Filterer;
import entities.base.Meeting;
import entities.base.Timetable;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertTrue;


class FiltererTest {
    private static List<Timetable> getTimetables() {
        List<Meeting> exampleMeetings = new ArrayList<>();

        Timetable timetable1 = new Timetable(exampleMeetings, new HashSet<>(Arrays.asList("Tag1", "Tag2", "Tag7")));
        Timetable timetable2 = new Timetable(exampleMeetings, new HashSet<>(Arrays.asList("Tag1", "Tag3", "Tag7")));
        Timetable timetable3 = new Timetable(exampleMeetings, new HashSet<>(Arrays.asList("Tag3", "Tag3", "Tag7")));

        return List.of(timetable1, timetable2, timetable3);
    }

    @Test
    // Test the filterer using 1 tag, that should return 2 timetables
    void TestFilterReturnTwo() {
        List<Timetable> exampleTimetables = getTimetables();
        HashSet<String> desiredTags = new HashSet<>();
        desiredTags.add("Tag1");
        List<Timetable> filtered = Filterer.filterAll(exampleTimetables, desiredTags);
        boolean res = filtered.equals(exampleTimetables.subList(0, 2));
        assertTrue(res);
    }

    @Test
    // Test the filterer using 1 tag, that should return 1 timetable
    void TestFilterReturnOne() {
        List<Timetable> exampleTimetables = getTimetables();
        Set<String> desiredTags = new HashSet<>();
        desiredTags.add("Tag2");
        List<Timetable> filtered = Filterer.filterAll(exampleTimetables, desiredTags);
        boolean res = filtered.equals(exampleTimetables.subList(0, 1));
        assertTrue(res);
    }

    @Test
    // Test the filterer using 1 tag, that should return 3 timetables
    void TestFilterReturnThree() {
        List<Timetable> exampleTimetables = getTimetables();
        Set<String> desiredTags = new HashSet<>();
        desiredTags.add("Tag7");
        List<Timetable> filtered = Filterer.filterAll(exampleTimetables, desiredTags);
        boolean res = filtered.equals(exampleTimetables);
        assertTrue(res);
    }

    @Test
    // Test the filterer using 2 tags, that should return 2 timetables
    void TestTwoTags() {
        List<Timetable> exampleTimetables = getTimetables();
        Set<String> desiredTags = new HashSet<>();
        desiredTags.add("Tag7");
        desiredTags.add("Tag1");
        List<Timetable> filtered = Filterer.filterAll(exampleTimetables, desiredTags);
        boolean res = filtered.equals(exampleTimetables.subList(0, 2));
        assertTrue(res);
    }


}