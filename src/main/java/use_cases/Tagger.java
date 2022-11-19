package use_cases;

import entities.Meeting;
import entities.Timetable;

public class Tagger {
    public static Timetable main(Timetable timetable) {

    }

    public static Timetable morningCentered(Timetable timetable) {
        // only morning, nothing else
        for (Meeting meeting: timetable.getMeetings()) {
            
        }
    }

    public static Timetable afternoonCentered(Timetable timetable) {
        //
    }



    // balanced
    // spread out / not spread out
    // lunch break available
    // long weekend (relatively free friday/monday) or maybe just free day in general for any day of the week
}
