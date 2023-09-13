package parametrizacao;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

class ParameterizationTest {
    public static void main(String[] args) {
        testar(8, 0, 0, 8, 2, 0);
        System.out.println("\n--------------------------\n");
        testar(8, 2, 0, 8, 7, 0);
        System.out.println("\n--------------------------\n");
        testar(8, 19, 10, 8, 23, 50);
        System.out.println("\n--------------------------\n");
        testar(8, 18, 0, 9, 0, 0);
        System.out.println("\n--------------------------\n");
        testar(8, 21, 0, 9, 7, 0);
        System.out.println("\n--------------------------\n");
        testar(8, 15, 0, 8, 18, 0);
        System.out.println("\n--------------------------\n");
        testar(8, 15, 0, 8, 17, 0);
        System.out.println("\n--------------------------\n");
        testar(8, 15, 0, 8, 16, 0);
        System.out.println("\n--------------------------\n");
        testar(8, 5, 0, 8, 10, 0);
    }

    public static void testar(int startDay, int startHr,  int startMin, int endDay, int endHr, int endMin){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm", new Locale("PT", "BR"));
        LocalDateTime startDate = LocalDateTime.of(2023, Month.SEPTEMBER, startDay, startHr, startMin);
        LocalDateTime endDate = LocalDateTime.of(2023, Month.SEPTEMBER, endDay, endHr, endMin);
        Parameterization parameterization = new Parameterization(startDate, endDate);

        System.out.println("Data inicial: " + parameterization.getStartDate().format(formatter));
        System.out.println("Data Final: " + parameterization.getEndDate().format(formatter));

        System.out.println("Minutos diurnos: " + parameterization.getMinutesDayTime());
        System.out.println("Minutos noturnos: " + parameterization.getMinutesNightTime());
        System.out.println("v1601: " + parameterization.getV1601());
        System.out.println("v1602: " + parameterization.getV1602());
        System.out.println("v3000: " + parameterization.getV3000());
        System.out.println("v3001: " + parameterization.getV3001());
        System.out.println("v1809: " + parameterization.getV1809());
    }
}
