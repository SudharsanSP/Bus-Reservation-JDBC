package JDBCdemo;

import java.sql.*;
public class JdbcDemo {
    public static void main(String[] args) throws Exception {
//        insertVariable();
//        insertPrepareSmt();
//        deleteRow();
//        deletePreparedSmt();
//        updatePreparedSmt();
//        readRecord();
//        storedProcedure();
//        storedProcedure2();
        storedProcedure3();
    }
    
    
    public static void readRecord() throws Exception{
        String url = "jdbc:mysql://localhost:3306/jdbcdemo";
        String username = "root";
        String password = "root";
        String query = "select id, name, salary from employee";
        Connection con = DriverManager.getConnection(url, username, password);
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(query);
        while(rs.next()){
            System.out.print(rs.getInt(1) +"    ");
            System.out.print(rs.getString(2)+"    ");
            System.out.print(rs.getFloat(3)+"    ");
            System.out.println();
        }
        con.close();
    }
    //insert using query
    public static void insertVariable() throws Exception{
        String url = "jdbc:mysql://localhost:3306/jdbcdemo";
        String username = "root";
        String password = "root";
        /*   insert using variables
        int id = 7;
        String name = "Darshan";
        double salary = 50000;
        String query = "insert into employee values (" + id + ",'" + name + "'," + salary + ")";
        
         */
        String query = "insert into employee values(4, 'Susa', 50000)";
        Connection con = DriverManager.getConnection(url, username, password);
        Statement st = con.createStatement();
        int rows = st.executeUpdate(query);
        System.out.println("no of rows affected = "+ rows);
        con.close();
    }
    //insert using prepared statement
    public static void insertPrepareSmt() throws Exception{
        String url = "jdbc:mysql://localhost:3306/jdbcdemo";
        String username = "root";
        String password = "root";
        int id = 8;
        String name = "Darshan";
        double salary = 80000;
        String query = "insert into employee values (?,?,?)";
        Connection con = DriverManager.getConnection(url, username, password);
        PreparedStatement pst = con.prepareStatement(query);
        pst.setInt(1, id);
        pst.setString(2, name);
        pst.setDouble(3, salary);
        int rows = pst.executeUpdate();
        System.out.println("no of rows affected = "+ rows);
        con.close();
    }
    //delete using query
    public static void deleteRow() throws Exception{
        String url = "jdbc:mysql://localhost:3306/jdbcdemo";
        String username = "root";
        String password = "root";
        
        String name = "Susa";
        String query = "delete from employee where name = '" + name +"'";
        Connection con = DriverManager.getConnection(url, username, password);
        Statement st = con.createStatement();
        int  rows = st.executeUpdate(query);
        
        System.out.println("no. of rows affected = "+ rows);
        con.close();
    }
    //delete using prepared statement
    public static void deletePreparedSmt() throws Exception{
        String url = "jdbc:mysql://localhost:3306/jdbcdemo";
        String username = "root";
        String password = "root";
        int id = 8;
        String query = "delete from employee where id = ?";
        Connection con = DriverManager.getConnection(url, username, password);
        PreparedStatement pst = con.prepareStatement(query);
        pst.setInt(1, id);
        int rows = pst.executeUpdate();
        
        System.out.println("no. of rows affected = "+ rows);
        con.close();
    }
    // update using prepared statement
    public static void updatePreparedSmt() throws Exception{
        String url = "jdbc:mysql://localhost:3306/jdbcdemo";
        String username = "root";
        String password = "root";
        int sal = 90000;
        int id = 5;
        String query = "update employee set salary = ? where id = ?";
        Connection con = DriverManager.getConnection(url, username, password);
        PreparedStatement pst = con.prepareStatement(query);
        pst.setInt(1, sal);
        pst.setInt(2, id);
        int rows = pst.executeUpdate();
        
        System.out.println("no. of rows affected = "+ rows);
        con.close();
    }
    public static void storedProcedure() throws Exception{
        String url = "jdbc:mysql://localhost:3306/jdbcdemo";
        String username = "root";
        String password = "root";
        Connection con = DriverManager.getConnection(url, username, password);
        CallableStatement cst = con.prepareCall("{call getDetails()}");
        ResultSet rs = cst.executeQuery();
        
        while(rs.next()){
            
            System.out.print(rs.getInt(1)+"    ");
            System.out.print(rs.getString(2)+"    ");
            System.out.println();
        }
        con.close();
    }
    public static void storedProcedure2() throws Exception{
        String url = "jdbc:mysql://localhost:3306/jdbcdemo";
        String username = "root";
        String password = "root";
        int id = 2;
        Connection con = DriverManager.getConnection(url, username, password);
        CallableStatement cst = con.prepareCall("{call getById(?)}");
        cst.setInt(1, id);
        ResultSet rs = cst.executeQuery();
        
        while(rs.next()){
            
            System.out.print(rs.getInt(1)+"    ");
            System.out.print(rs.getString(2)+"    ");
            System.out.print(rs.getInt(3)+"    ");
            System.out.println();
        }
        con.close();
    }
    public static void storedProcedure3() throws Exception{
        String url = "jdbc:mysql://localhost:3306/jdbcdemo";
        String username = "root";
        String password = "root";
        Connection con = DriverManager.getConnection(url, username, password);
        CallableStatement cst = con.prepareCall("{call getNameById(?,?)}");
        int id = 2;
        cst.setInt(1,id);
        cst.registerOutParameter(2, Types.VARCHAR);
        cst.executeUpdate();
        System.out.println(cst.getString(2));
        con.close();
    }
}
