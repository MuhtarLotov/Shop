package team.alabs.wso3.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PaginationUtil {

    public enum DataBaseType {POSTGRES, ORACLE}

    public static String addPagination(String source, int pageNumber, int pageSize, DataBaseType dataBaseType) {
        if (DataBaseType.POSTGRES == dataBaseType) {
            return postgres(source, pageNumber, pageSize);
        }
        return oracle(source, pageNumber, pageSize);

    }

    public static String oracle(String source, int pageNumber, int pageSize) {

        return " SELECT * " +
                " FROM ( " +
                "         SELECT a.*, rownum r__ " +
                "         FROM ( " + source + ") a  " +
                "         WHERE rownum < ((" + pageNumber + " * " + pageSize + ") + 1)  " +
                "     )  " +
                " WHERE r__ >= (((" + pageNumber + " - 1) * " + pageSize + ") + 1) ";

    }

    private static String postgres(String sql, int pageNumber, int pageSize) {
        return String.format("%s LIMIT %d OFFSET %d * %d  ", sql, pageSize, pageSize, pageNumber);
    }
}
