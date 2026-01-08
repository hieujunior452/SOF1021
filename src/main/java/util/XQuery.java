package util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
//import poly.cafe.entity.User;

/**
 * Lớp tiện ích hỗ trợ truy vấn và chuyển đổi sang đối tượng
 *
 * @author NghiemN
 * @version 1.0
 */
public class XQuery {

    /**
     * Truy vấn 1 đối tượng
     *
     * @param <B> kiểu của đối tượng cần chuyển đổi
     * @param beanClass lớp của đối tượng kết quả
     * @param sql câu lệnh truy vấn
     * @param values các giá trị cung cấp cho các tham số của SQL
     * @return kết quả truy vấn
     * @throws RuntimeException lỗi truy vấn
     */
    public static <B> B getSingleBean(Class<B> beanClass, String sql, Object... values) {
        List<B> list = XQuery.getBeanList(beanClass, sql, values);
        if (!list.isEmpty()) {
            return list.get(0);
        }
        return null;
    }

    /**
     * Truy vấn nhiều đối tượng
     *
     * @param <B> kiểu của đối tượng cần chuyển đổi
     * @param beanClass lớp của đối tượng kết quả
     * @param sql câu lệnh truy vấn
     * @param values các giá trị cung cấp cho các tham số của SQL
     * @return kết quả truy vấn
     * @throws RuntimeException lỗi truy vấn
     */
    public static <B> List<B> getBeanList(Class<B> beanClass, String sql, Object... values) {
        List<B> list = new ArrayList<>();
        try {
            ResultSet resultSet = XJdbc.executeQuery(sql, values);
            while (resultSet.next()) {
                list.add(XQuery.readBean(resultSet, beanClass));
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        return list;
    }

    /**
     * Tạo bean với dữ liệu đọc từ bản ghi hiện tại
     *
     * @param <B> kiểu của đối tượng cần chuyển đổi
     * @param resultSet tập bản ghi cung cấp dữ liệu
     * @param beanClass lớp của đối tượng kết quả
     * @return kết quả truy vấn
     * @throws RuntimeException lỗi truy vấn
     */
//    private static <B> B readBean(ResultSet resultSet, Class<B> beanClass) throws Exception {
//        B bean = beanClass.getDeclaredConstructor().newInstance();
//        Method[] methods = beanClass.getDeclaredMethods();
//        for(Method method: methods){
//            String name = method.getName();
//            if (name.startsWith("set") && method.getParameterCount() == 1) {
//                try {
//                    Object value = resultSet.getObject(name.substring(3));
//                    method.invoke(bean, value);
//                } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | SQLException e) {
//                    System.out.printf("+ Column '%s' not found!\r\n", name.substring(3));
//                }
//            }
//        }
//        return bean;
//    }
    private static <B> B readBean(ResultSet rs, Class<B> beanClass) throws Exception {
    B bean = beanClass.getDeclaredConstructor().newInstance();
    Method[] methods = beanClass.getDeclaredMethods();

    for (Method method : methods) {
        String name = method.getName();

        // Setter
        if (name.startsWith("set") && method.getParameterCount() == 1) {

            String column = name.substring(3);
            // cột DB viết thường hết
            String dbColumn = column.substring(0,1).toLowerCase() + column.substring(1);

            try {
                Class<?> paramType = method.getParameterTypes()[0];

                Object value;

                if (paramType == LocalDateTime.class) {
                    Timestamp ts = rs.getTimestamp(dbColumn);
                    value = (ts != null ? ts.toLocalDateTime() : null);
                } else {
                    value = rs.getObject(dbColumn);
                }

                method.invoke(bean, value);

            } catch (Exception e) {
                System.out.println("Column '" + column + "' not found or type mismatch.");
            }
        }
    }
    return bean;
}

}