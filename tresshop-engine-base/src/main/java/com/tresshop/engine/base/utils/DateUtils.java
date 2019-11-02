package com.tresshop.engine.base.utils;

import com.tresshop.engine.base.exception.GenericException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class DateUtils {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat(
            "yyyy-MM-dd hh:mm:ss:SSS");

    public Timestamp convertDateToTimestamp(String date) {
        try {
            Date parsedTimeStamp = dateFormat.parse(date);
            Timestamp timestamp = new Timestamp(parsedTimeStamp.getTime());
            return timestamp;
        } catch (ParseException e) {
            log.error("Exception occurred while converting date: {} with exception: {}", date, e.getMessage());
            throw new GenericException(HttpStatus.INTERNAL_SERVER_ERROR, "Wrong Date format!");
        }
    }
}
