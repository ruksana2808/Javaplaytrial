import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws SQLException {
        Main object = new Main();
        Connection connection= object.sqlConnect();
        object.addEmployee(connection);
        object.getDetails(connection);
//
    }


    private void addEmployee(Connection connection) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("INSERT INTO student_details " +
                        "(id,name,address)" +
                        "VALUES (?, ?, ?)",
                new String[]{"empId"});
        ps.setInt(1, 6);
        ps.setString(2, "Deepu");
        ps.setString(3, "Address");
        System.out.println(ps);
        ps.executeUpdate();
        System.out.println("Success");
//        ps.close();
    }

    private void getDetails(Connection connection) throws SQLException {
        List<Object> result=new ArrayList<>();
        PreparedStatement ps = connection.prepareStatement("select * from student_details where id=?");
        ps.setInt(1,2);
        ResultSet rs = ps.executeQuery();

        while(rs.next()){
            String column1 = rs.getString("name");
            String column2 = rs.getString("address");
            result.add(column1);
            result.add(column2);
            System.out.println(result);
        }
        rs.close();
    }

    private Connection sqlConnect() throws SQLException {

        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/Student", "root", "password");
        return connection;

    }

}