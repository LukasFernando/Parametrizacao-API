package parametrizacao;

import java.time.*;
import java.util.Date;

public class Parameterization {
    private LocalDateTime startDate, endDate;
    private int minutesDayTime, minutesNightTime;
    private int startWorkTime = 8;
    private int endWorkTime = 17;
    private double v1601, v1602, v3000, v3001, v1809;

    public Parameterization(LocalDateTime startDate, LocalDateTime endDate) {
//        this.startDate = startDate;
//        this.endDate = endDate;

        this.startDate = takeWorkPeriod(startDate, true);
        this.endDate = takeWorkPeriod(endDate, false);

        calculateMinutes();
//        splitMinutes();
    }

    private void calculateMinutesTESTE(){
        LocalDateTime dayTime = LocalDateTime.of(startDate.getYear(), startDate.getMonth(), startDate.getDayOfMonth(), 6, 0);
        LocalDateTime nightTime = LocalDateTime.of(startDate.getYear(), startDate.getMonth(), startDate.getDayOfMonth(), 22, 0);

        // Horas diurnas
        if(isDayTime(startDate)){
            if(isDayTime(endDate) & startDate.getDayOfMonth() == endDate.getDayOfMonth()){
                this.minutesDayTime = getDifferenceInMinutes(startDate, endDate);
            }
            else{
                this.minutesDayTime = getDifferenceInMinutes(startDate, nightTime);
            }
            if(isDayTime(endDate)){
                this.minutesDayTime += getDifferenceInMinutes(dayTime.plusDays(1), endDate);
            }
        }
        else{
            this.minutesDayTime = getDifferenceInMinutes(dayTime, endDate);
        }

        // Horas noturnas
        if(isDayTime(startDate)){
            if(isDayTime(endDate) & startDate.getDayOfMonth()+1 == endDate.getDayOfMonth()){
                this.minutesNightTime = getDifferenceInMinutes(nightTime, isDayTime(endDate)?dayTime.plusDays(1):endDate);
            }
            else{
                this.minutesNightTime = getDifferenceInMinutes(nightTime, isDayTime(endDate)?dayTime:endDate);
            }
        }
        else{
            this.minutesNightTime = getDifferenceInMinutes(startDate, isDayTime(endDate)?dayTime:endDate);
        }
    }

    private void calculateMinutes(){
        LocalDateTime dayTime = LocalDateTime.of(startDate.getYear(), startDate.getMonth(), startDate.getDayOfMonth(), 6, 0);
        LocalDateTime nightTime = LocalDateTime.of(startDate.getYear(), startDate.getMonth(), startDate.getDayOfMonth(), 22, 0);

        if(isDayTime(startDate)){
            if(isDayTime(endDate) & startDate.getDayOfMonth() == endDate.getDayOfMonth()){
                this.minutesDayTime = getDifferenceInMinutes(startDate, endDate);
                this.minutesNightTime = getDifferenceInMinutes(nightTime, isDayTime(endDate)?dayTime:endDate);
            }
            else{
                this.minutesDayTime = getDifferenceInMinutes(startDate, nightTime);
                this.minutesNightTime = getDifferenceInMinutes(nightTime, isDayTime(endDate)?dayTime.plusDays(1):endDate);
            }
            if(isDayTime(endDate)){
                this.minutesDayTime += getDifferenceInMinutes(dayTime.plusDays(1), endDate);
            }
        }
        else{
            this.minutesDayTime = getDifferenceInMinutes(dayTime, endDate);
            this.minutesNightTime = getDifferenceInMinutes(startDate, isDayTime(endDate)?dayTime:endDate);
        }
        splitMinutes(true, this.minutesDayTime);
        splitMinutes(false, this.minutesNightTime);
    }

    private void splitMinutes(boolean isDayTime, int allTimeWorkingInMinutes){
        // 120 min = 2h
        int min = 120;
        double nightHour = 1.1429;
        double hours = (double) Math.min(allTimeWorkingInMinutes, min) / 60;
        double hoursLeft = (double) (allTimeWorkingInMinutes > min ? allTimeWorkingInMinutes - min : 0) / 60;

        if(isDayTime){
            this.v1601 = hours;
            this.v1602 = hoursLeft;
        }
        else{
            this.v3000 = hours * nightHour;
            this.v3001 = hoursLeft * nightHour;
            this.v1809 = (hours + hoursLeft) * nightHour;
        }
    }

    private boolean isDayTime(LocalDateTime date){
        return date.getHour() > 6 & date.getHour() <= 22;
    }

    private LocalDateTime takeWorkPeriod(LocalDateTime date, boolean isStartDate){
        int hr = date.getHour();
        int min = date.getMinute();
        if(isStartDate){
            if(hr > startWorkTime & hr < endWorkTime){
                hr = 17;
                min = 0;
            }
        }
        else{
            if(hr > startWorkTime & hr < endWorkTime){
                hr = 8;
                min = 0;
            }
        }
        date = LocalDateTime.of(date.getYear(), date.getMonth(), date.getDayOfMonth(), hr, min);
        return date;
    }

    private int getDifferenceInMinutes(LocalDateTime startDate, LocalDateTime endDate){
        int minutes = (int)Duration.between(startDate, endDate).toMinutes();
        return Math.max(minutes, 0);
    }

    public void setStartWorkTime(int startWorkTime) {
        this.startWorkTime = startWorkTime;
    }

    public void setEndWorkTime(int endWorkTime) {
        this.endWorkTime = endWorkTime;
    }


    // -------------------- //
    // Tirar depois do desenvolvimento //
    // -------------------- //
    public double getV1601() {
        return v1601;
    }

    public double getV1602() {
        return v1602;
    }

    public double getV3000() {
        return v3000;
    }

    public double getV3001() {
        return v3001;
    }

    public double getV1809() {
        return v1809;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public int getMinutesDayTime() {
        return minutesDayTime;
    }

    public int getMinutesNightTime() {
        return minutesNightTime;
    }
}
