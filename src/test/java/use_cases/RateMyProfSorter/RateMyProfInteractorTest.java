package use_cases.RateMyProfSorter;

import entities.base.Meeting;
import entities.base.Session;
import entities.base.Timetable;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.List;

class RateMyProfInteractorTest {
    // Test that the RMP sorter correctly sorts the timetables based on the average rate my prof score
    @Test
    public void TestRateMyProfSorter() throws IOException {
        Session s1 = new Session(DayOfWeek.MONDAY,
                LocalTime.of(1, 0), LocalTime.of(2, 0));
        Meeting m1 = new Meeting("", Meeting.DefaultType.DEFAULT, "M Badr", 100, 90,0, s1);
        Meeting m2 = new Meeting("", Meeting.DefaultType.DEFAULT, "P Gries", 100, 90,0, s1);
        Meeting m3 = new Meeting("", Meeting.DefaultType.DEFAULT, "F Pitt", 100, 90,0, s1);
        Meeting m4 = new Meeting("", Meeting.DefaultType.DEFAULT, "K Huynh-Wong", 100, 90,0, s1);

        Timetable timetable1 = new Timetable(List.of(m1, m2, m3), new HashSet<>());
        Timetable timetable2 = new Timetable(List.of(m4), new HashSet<>());
        RateMyProfRequestModel requestModel = new RateMyProfRequestModel(List.of(timetable1, timetable2));
        RateMyProfInteractor interactor = new RateMyProfInteractor();
        RateMyProfResponseModel responseModel = interactor.sort(requestModel);
        Assertions.assertEquals(List.of(timetable1, timetable2), responseModel.getTimetableList());
    }

}