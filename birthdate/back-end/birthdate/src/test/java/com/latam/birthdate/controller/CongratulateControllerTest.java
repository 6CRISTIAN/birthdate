package com.latam.birthdate.controller;

import com.latam.birthdate.dto.UserInfoDto;
import com.latam.birthdate.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class CongratulateControllerTest {

    CongratulateController congratulateController = new CongratulateController();

    @Test
    void bad_request_httpStatus_when_names_is_null() {
        User user = new User(null, "firstSurName secondSurName", new Date());
        ResponseEntity<UserInfoDto> res = congratulateController.getUserAgeInfo(user);
        assertEquals(400, res.getStatusCodeValue());
    }

    @Test
    void bad_request_httpStatus_when_surnames_is_null() {
        User user = new User("firstName secondName", null, new Date());
        ResponseEntity<UserInfoDto> res = congratulateController.getUserAgeInfo(user);
        assertEquals(400, res.getStatusCodeValue());
    }

    @Test
    void bad_request_httpStatus_when_date_is_null() {
        User user = new User("firstName secondName", "firstSurName secondSurName", null);
        ResponseEntity<UserInfoDto> res = congratulateController.getUserAgeInfo(user);
        assertEquals(400, res.getStatusCodeValue());
    }

    @Test
    void ok_200_httpStatus_when_all_params_are_correct() {
        User user = new User("firstName secondName", "firstSurName secondSurName", new Date());
        ResponseEntity<UserInfoDto> res = congratulateController.getUserAgeInfo(user);
        assertEquals(200, res.getStatusCodeValue());
    }
}
