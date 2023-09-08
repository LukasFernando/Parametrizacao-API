package parametrizacao;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
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
        splitMinutes();
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

        // Horas diurnas
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
    }

    private void splitMinutes(){

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

    public void setStartWorkTime(int startWorkTime) {
        this.startWorkTime = startWorkTime;
    }

    public void setEndWorkTime(int endWorkTime) {
        this.endWorkTime = endWorkTime;
    }
}
