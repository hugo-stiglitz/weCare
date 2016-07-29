package eu.ldob.wecare.entity.operation;

import android.content.PeriodicSync;

import java.util.Calendar;
import java.util.Date;

public class Patient {

    private String lastname;
    private String firstname;
    private Date birthdate;

    public Patient(String lastname, String firstname, Date birthdate) {
        this.lastname = lastname;
        this.firstname = firstname;
        this.birthdate = birthdate;
    }

    public String getLastname() {
        return lastname;
    }

    public String getFirstname() {
        return firstname;
    }

    public int getAge() {

        // TODO resolve with java8 Period.between(..) or Years.yearsBetween(..)
        Calendar now = Calendar.getInstance();
        Calendar dob = Calendar.getInstance();
        dob.setTime(birthdate);
        if (dob.after(now)) {
            throw new IllegalArgumentException("Can't be born in the future");
        }
        int year1 = now.get(Calendar.YEAR);
        int year2 = dob.get(Calendar.YEAR);
        int age = year1 - year2;
        int month1 = now.get(Calendar.MONTH);
        int month2 = dob.get(Calendar.MONTH);
        if (month2 > month1) {
            age--;
        } else if (month1 == month2) {
            int day1 = now.get(Calendar.DAY_OF_MONTH);
            int day2 = dob.get(Calendar.DAY_OF_MONTH);
            if (day2 > day1) {
                age--;
            }
        }

        return age;
    }
}
