package factory;

import constants.testData.EptTestData;
import model.dto.timelog.TimeLogCreateRequest;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static constants.testData.CommonTestData.DEFAULT_NOTE;
import static constants.testData.CommonTestData.FULL_WORKING_DAY;

public final class TimeLogDtoFactory {

    private static final DateTimeFormatter DATE = DateTimeFormatter.ISO_LOCAL_DATE;

    private TimeLogDtoFactory() {}

    public static TimeLogCreateRequest create(EptTestData ept) {
        return new TimeLogCreateRequest(
                FULL_WORKING_DAY,
                LocalDate.now().format(DATE),
                DEFAULT_NOTE,
                ept.getId(),
                null
        );
    }

    public static TimeLogCreateRequest create(EptTestData ept, LocalDate date) {
        return new TimeLogCreateRequest(
                FULL_WORKING_DAY,
                date.format(DATE),
                DEFAULT_NOTE,
                ept.getId(),
                null
        );
    }
}