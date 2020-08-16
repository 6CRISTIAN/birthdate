package com.latam.birthdate.imp;

import com.latam.birthdate.dto.UserInfoDto;
import com.latam.birthdate.model.Poem;
import com.latam.birthdate.model.User;
import com.latam.birthdate.service.ICongratulate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private WebClient.Builder webClientBuilder = WebClient.builder();

    @Override
    public UserInfoDto getUserInfo(User user) throws Exception {
        try {
            LocalDate now = LocalDate.now();
            LocalDate birthdate = parseDateToLocalDate(user.getBirthdate());
            UserInfoDto userInfo = new UserInfoDto(
                    getShortName(user.getName(), user.getSurname()),
                    calculateAge(birthdate, now),
                    getRemainingDays(birthdate, now)
            );
            if (isBirthday(birthdate, now)) choosePoem(userInfo);
            return userInfo;
        } catch (Exception e) {
            throw new Exception("Internal error.");
        }
    }

    /**
     * @param names    of the user.
     * @param surnames of the user.
     * @return the firsts name and firsts surname by two strings.
     */
    @Override
    public String getShortName(String names, String surnames) {
        if (names.trim().length() < 1 || (surnames.trim().length() < 1)) return null;
        String[] _names = names.split(" ");
        String[] _surnames = surnames.split(" ");
        return _names[0] + " " + _surnames[0];
    }

    /**
     * this method covert one java Date to java LocalDate.
     *
     * @param birthdate Date
     * @return LocalDate
     */
    @Override
    public LocalDate parseDateToLocalDate(Date birthdate) throws NullPointerException {
        if (null == birthdate) throw new NullPointerException("Date is undefined.");
        return birthdate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    /**
     * this method calculate age by one date (birthdate) and current date.
     *
     * @param birthdate
     * @param now       current date.
     * @return
     */
    @Override
    public int calculateAge(LocalDate birthdate, LocalDate now) {
        return Period.between(birthdate, now).getYears();
    }

    /**
     * this method calculate difference days between tow dates.
     *
     * @param startDate
     * @param endDate
     * @return number
     */
    @Override
    public long getRemainingDays(LocalDate startDate, LocalDate endDate) {
        long days = ChronoUnit.DAYS.between(endDate, startDate.withYear(endDate.getYear()));
        if (days < 0) return ChronoUnit.DAYS.between(endDate, startDate.withYear(endDate.getYear()).plusYears(1));
        return days;
    }

    /**
     * this method return true if the mont and day of the birthdate param is equals
     * to month and day of the now param.
     *
     * @param birthdate
     * @param now
     * @return boolean
     */
    @Override
    public boolean isBirthday(LocalDate birthdate, LocalDate now) {
        return now.getMonthValue() == birthdate.getMonthValue() && now.getDayOfMonth() == birthdate.getDayOfMonth();
    }

    /**
     * this method picks a random poem when it receives the poem list
     * by the getPoems() method and assign that choice to the user.
     *
     * @param user
     */
    @Override
    public void choosePoem(UserInfoDto user) {
        List<Poem> poems = getPoems();
        int random = ThreadLocalRandom.current().nextInt(0, poems.size());
        user.setPoem(poems.get(random));
    }

    /**
     * this method get poem list calling external api.
     *
     * @return Poem list
     */
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
