package com.latam.birthdate.service;

import com.latam.birthdate.dto.UserInfoDto;
import com.latam.birthdate.model.Poem;
import com.latam.birthdate.model.User;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface ICongratulate {
    UserInfoDto getUserInfo(User user) throws Exception;
    List<Poem> getPoems();
    void choosePoem(UserInfoDto person);
    boolean isBirthday(LocalDate bd, LocalDate now);
    long getRemainingDays(LocalDate bd, LocalDate now);
    int calculateAge(LocalDate birthdate, LocalDate now);
    String getShortName(String names, String surnames);
    LocalDate parseDateToLocalDate(Date birthdate) throws NullPointerException;
}
