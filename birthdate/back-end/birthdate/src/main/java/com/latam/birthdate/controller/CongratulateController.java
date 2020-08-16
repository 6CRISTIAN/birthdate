package com.latam.birthdate.controller;

import com.latam.birthdate.dto.UserInfoDto;
import com.latam.birthdate.model.User;
import com.latam.birthdate.imp.CongratulateImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("api/v1/birthdate")
@CrossOrigin
public class CongratulateController {

    @Autowired
    CongratulateImp congratulateImp = new CongratulateImp();

    @PostMapping
    public ResponseEntity<UserInfoDto> getUserAgeInfo(@RequestBody User user) {
        if (null == user.getName() || null == user.getSurname() || null == user.getBirthdate())
            return new ResponseEntity("Bad fields, check them.", HttpStatus.BAD_REQUEST);
        try {
            return new ResponseEntity(congratulateImp.getUserInfo(user), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
