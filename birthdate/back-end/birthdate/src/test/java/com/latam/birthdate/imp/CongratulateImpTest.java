package com.latam.birthdate.imp;

import com.latam.birthdate.dto.UserInfoDto;
import com.latam.birthdate.model.Poem;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CongratulateImpTest {

    CongratulateImp congratulateImp = new CongratulateImp();

    /**
     * TEST -> isBirthday() method
     */

    @Test
    public void false_when_month_is_different() {
        LocalDate now = LocalDate.now();
        int month = now.getMonthValue() > 1 ? now.getMonthValue() - 1 : now.getMonthValue() + 1;
        LocalDate birthdate = LocalDate.of(1998, month, now.getDayOfMonth());
        assertFalse(congratulateImp.isBirthday(birthdate, now));
    }

    @Test
    public void false_when_day_is_different() {
        LocalDate now = LocalDate.now();
        int day = now.getDayOfMonth() > 1 ? now.getDayOfMonth() - 1 : now.getDayOfMonth() + 1;
        LocalDate birthdate = LocalDate.of(1998, now.getMonthValue(), day);
        assertFalse(congratulateImp.isBirthday(birthdate, now));
    }

    @Test
    public void false_when_day_and_month_are_different() {
        LocalDate now = LocalDate.now();
        int month = now.getMonthValue() > 1 ? now.getMonthValue() - 1 : now.getMonthValue() + 1;
        int day = now.getDayOfMonth() > 1 ? now.getDayOfMonth() - 1 : now.getDayOfMonth() + 1;
        LocalDate birthdate = LocalDate.of(1998, month, day);
        assertFalse(congratulateImp.isBirthday(birthdate, now));
    }

    @Test
    public void true_when_day_and_month_are_same() {
        LocalDate now = LocalDate.now();
        LocalDate birthdate = LocalDate.of(1998, now.getMonthValue(), now.getDayOfMonth());
        assertTrue(congratulateImp.isBirthday(birthdate, now));
    }

    /**
     * TEST -> getShortName() method
     */

    @Test
    void null_when_names_input_is_one_space() {
        assertEquals(null, congratulateImp.getShortName(" ", "gomez escobar"));
    }

    @Test
    void null_when_surnames_input_is_one_space() {
        assertEquals(null, congratulateImp.getShortName("cristian alonso", " "));
    }

    @Test
    void null_when_names_and_surnames_input_is_one_space() {
        assertEquals(null, congratulateImp.getShortName(" ", " "));
    }

    @Test
    void cristian_gomez_when_input_is_cristian_alonso_gomez_escobar() {
        assertEquals("cristian gomez",
                congratulateImp.getShortName("cristian alonso", "gomez escobar"));
    }

    @Test
    void cristian_gomez_when_input_is_cristian_alonso_gomez() {
        assertEquals("cristian gomez",
                congratulateImp.getShortName("cristian alonso", "gomez"));
    }

    @Test
    void cristian_gomez_when_input_is_cristian_gomez_escobar() {
        assertEquals("cristian gomez",
                congratulateImp.getShortName("cristian", "gomez escobar"));
    }

    @Test
    void cristian_gomez_when_input_is_cristian_gomez() {
        assertEquals("cristian gomez",
                congratulateImp.getShortName("cristian", "gomez"));
    }

    /**
     * TEST -> calculateAge() method
     */

    @Test
    void _21_when_current_date_is_2020_08_15_and_birthdate_is_1999_08_14() {
        LocalDate birthdate = LocalDate.of(1999, 8, 14);
        LocalDate currentDate = LocalDate.of(2020, 8, 15);
        assertEquals(21, congratulateImp.calculateAge(birthdate, currentDate));
    }

    @Test
    void _0_when_current_date_is_2020_08_15_and_birthdate_is_2020_08_18() {
        LocalDate birthdate = LocalDate.of(2020, 8, 18);
        LocalDate currentDate = LocalDate.of(2020, 8, 15);
        assertEquals(0, congratulateImp.calculateAge(birthdate, currentDate));
    }

    @Test
    void _1_when_current_date_is_2020_08_15_and_birthdate_is_2019_08_15() {
        LocalDate birthdate = LocalDate.of(2019, 8, 15);
        LocalDate currentDate = LocalDate.of(2020, 8, 15);
        assertEquals(1, congratulateImp.calculateAge(birthdate, currentDate));
    }

    /**
     * TEST -> getRemainingDays() method
     */

    @Test
    void _3_when_current_date_is_2020_08_09_and_birthdate_is_2020_08_12() {
        LocalDate birthdate = LocalDate.of(2019, 8, 12);
        LocalDate currentDate = LocalDate.of(2020, 8, 9);
        assertEquals(3, congratulateImp.getRemainingDays(birthdate, currentDate));
    }

    @Test
    void _364_when_current_date_is_2020_08_16_and_birthdate_is_2020_08_15() {
        LocalDate birthdate = LocalDate.of(2019, 8, 15);
        LocalDate currentDate = LocalDate.of(2020, 8, 16);
        assertEquals(364, congratulateImp.getRemainingDays(birthdate, currentDate));
    }

    @Test
    void _1_when_current_date_is_2020_08_09_and_birthdate_is_2020_08_10() {
        LocalDate birthdate = LocalDate.of(2019, 8, 10);
        LocalDate currentDate = LocalDate.of(2020, 8, 9);
        assertEquals(1, congratulateImp.getRemainingDays(birthdate, currentDate));
    }

    /**
     * TEST -> parseDateToLocalDate() method
     */

    @Test()
    void null_pointer_exception_when_date_is_null() {
        try {
            congratulateImp.parseDateToLocalDate(null);
        } catch (Throwable ex) {
            assertTrue(ex instanceof NullPointerException);
        }
    }

    @Test
    void return_LocalDate_when_sends_date() {
        LocalDate response = congratulateImp.parseDateToLocalDate(new Date());
        assertTrue(response instanceof LocalDate);
    }

    /**
     * TEST -> choosePoem() method
     */

    @Test
    void set_random_poem_to_the_user_when_user_choosePoem_calling_external_api() {
        UserInfoDto user = new UserInfoDto();
        congratulateImp.choosePoem(user);
        assertEquals(Poem.class, user.getPoem().getClass());
    }

    /**
     * TEST -> getPoems() method
     */

    @Test
    void retrieve_poem_list_when_called_external_api() {
        List<Poem> poems = congratulateImp.getPoems();
        assertTrue(poems.size() > 0);
    }
}