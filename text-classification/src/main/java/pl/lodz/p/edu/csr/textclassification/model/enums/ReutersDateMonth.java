package pl.lodz.p.edu.csr.textclassification.model.enums;

import java.time.Month;

public enum ReutersDateMonth {

    JAN(1), FEB(2), MAR(3), APR(4),
    MAY(5), JUN(6), JUL(7), AUG(8),
    SEP(9), OCT(10), NOV(11), DEC(12);

    private Month month;

    ReutersDateMonth(String shortMonth) {
        this.month = Month.of(ReutersDateMonth.valueOf(shortMonth).month.getValue());
    }

    public static Integer parseMonth(String shortMonth) {
        return ReutersDateMonth.valueOf(shortMonth).month.getValue();
    }

    ReutersDateMonth(int i) {
        this.month = Month.of(i);
    }

}
