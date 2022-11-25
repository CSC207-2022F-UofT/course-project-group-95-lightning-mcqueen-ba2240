package gateways;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Map;

public class RateMyProfTest {
    @Test
    public void TestRMPgetNumberProfessors() throws IOException {
        // Test if the RMP getNumberProfessors returns a reasonable value (between 3000 and 5000)
        int number_professors = RateMyProf.getNumberProfessors();
        boolean res = number_professors > 3000 && number_professors < 5000;
        Assertions.assertTrue(res);

    }

    @Test
    public void TestRMPgetNumberPages() throws IOException {
        // Test if the RMP getNumberPages returns a value corresponding to the API implementation
        // Since each page can store a max of 20 professors, the number of pages should be
        // (numpages (floor division) 2) + 1

        int number_professors = RateMyProf.getNumberProfessors();
        int number_pages = RateMyProf.getNumberPages();
        Assertions.assertEquals(number_pages, (number_professors / 20) + 1);
    }

    @Test
    public void TestRMPfirstPageProfessors() throws IOException {
        // Test if the RMP getProfessors returns a HashMap with <= 20 professors and the
        // first professors last name should start with "A" (alphabetically sorted)

        Map<String, Double> mapping = RateMyProf.getProfessors(1);
        Object[] keys = mapping.keySet().toArray();
        String professor = (String) keys[0];
        String[] split_array = professor.split(" ");
        String last_name = split_array[1];

        // check name starts with "A"
        Assertions.assertTrue(last_name.startsWith("A"));
        Assertions.assertTrue(keys.length <= 20);

    }

}