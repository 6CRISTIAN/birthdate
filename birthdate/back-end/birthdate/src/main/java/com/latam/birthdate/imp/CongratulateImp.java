package com.latam.birthdate.imp;

import com.latam.birthdate.dto.UserInfoDto;
import com.latam.birthdate.model.Poem;
import com.latam.birthdate.model.User;
import com.latam.birthdate.service.ICongratulate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static com.latam.birthdate.util.Constants.URI_POEMS;

@Service
public class CongratulateImp implements ICongratulate {

    private final Logger logger = LoggerFactory.getLogger(CongratulateImp.class);

    @Autowired
    private WebClient.Builder webClientBuilder;

    public UserInfoDto getPersonInfo(User user) throws Exception {
        try {
            LocalDate now = LocalDate.now();
            LocalDate birthdate = getLocalDateByDate(user.getBirthdate());
            UserInfoDto userInfo = new UserInfoDto(
                    getShortName(user.getName(), user.getSurname()),
                    calculateAge(birthdate, now),
                    getRemainingDays(birthdate, now)
            );
            if (isBirthday(birthdate, now)) choosePoem(userInfo);
            return userInfo;
        } catch (Exception e) {
            throw new Exception("Internal errors");
        }
    }

    @Override
    public String getShortName(String names, String surnames) {
        String[] _names = names.split(" ");
        String[] _surnames = surnames.split(" ");
        return _names[0] + " " + _surnames[0];
    }

    @Override
    public LocalDate getLocalDateByDate(Date birthdate) {
        return birthdate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    @Override
    public int calculateAge(LocalDate birthdate, LocalDate now) {
        return Period.between(birthdate, now).getYears();
    }

    @Override
    public long getRemainingDays(LocalDate birthdate, LocalDate now) {
        long days = ChronoUnit.DAYS.between(now, birthdate.withYear(now.getYear()));
        if (days < 0) return ChronoUnit.DAYS.between(now, birthdate.withYear(now.getYear()).plusYears(1));
        return days;
    }

    @Override
    public boolean isBirthday(LocalDate birthdate, LocalDate now) {
        return now.getMonthValue() == birthdate.getMonthValue() && now.getDayOfMonth() == birthdate.getDayOfMonth();
    }

    @Override
    public void choosePoem(UserInfoDto person) {
        List<Poem> poems = getPoems();
        int random = ThreadLocalRandom.current().nextInt(0, poems.size());
        person.setPoem(poems.get(random));
    }

    @Override
    public List<Poem> getPoems() {
        return webClientBuilder.build()
                .get()
                .uri(URI_POEMS)
                .retrieve()
                .bodyToFlux(Poem.class)
                .collectList()
                .block();
    }
}
