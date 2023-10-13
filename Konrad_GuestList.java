import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/Konrad_GuestList")
public class Konrad_GuestList extends HttpServlet {
   private static final long serialVersionUID = 1L;
   static String url = "jdbc:mysql://ec2-18-222-171-92.us-east-2.compute.amazonaws.com:3306/myDB";
   static String user = "kacey_remote";
   static String password = "password";
   static Connection connection = null;

   public Konrad_GuestList() {
      super();
   }

   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      response.setContentType("text/html;charset=UTF-8");
      response.getWriter().println("-------- MySQL JDBC Connection Testing ------------<br>");
      try {
         Class.forName("com.mysql.cj.jdbc.Driver"); //old:com.mysql.jdbc.Driver
      } catch (ClassNotFoundException e) {
         System.out.println("Where is your MySQL JDBC Driver?");
         e.printStackTrace();
         return;
      }
      response.getWriter().println("MySQL JDBC Driver Registered!<br>");
      connection = null;
      try {
         connection = DriverManager.getConnection(url, user, password);
      } catch (SQLException e) {
         System.out.println("Connection Failed! Check output console");
         e.printStackTrace();
         return;
      }
      if (connection != null) {
         response.getWriter().println("You made it, take control your database now!<br>");
      } else {
         System.out.println("Failed to make connection!");
      }
      try {
         String selectSQL = "SELECT * FROM Guest_List";
         //String theUserName = "%";
         response.getWriter().println(selectSQL + "<br>");
         response.getWriter().println("------------------------------------------<br>");
         PreparedStatement preparedStatement = connection.prepareStatement(selectSQL);
         //preparedStatement.setString(1, theUserName);
         ResultSet rs = preparedStatement.executeQuery();
         while (rs.next()) {
        	 //String id = rs.getString("ID");
        	 String first_name = rs.getString("FIRST_NAME");
        	 String last_name = rs.getString("LAST_NAME");
             String phone = rs.getString("PHONE");
             String email = rs.getString("EMAIL");
             String status = rs.getString("STATUS");
             String level = rs.getString("MEMBERSHIP");
             //response.getWriter().append("ID: " + id + ", ");
             //response.getWriter().append("USER NAME: " + username + ", ");
             response.getWriter().append("MEMBER: " + first_name + " ");
             response.getWriter().append(last_name + ", ");
             response.getWriter().append("CONTACT INFO: " + phone + ", ");
             response.getWriter().append(email + ", ");
             response.getWriter().append("STATUS: " + status + ", ");
             response.getWriter().append("VIP LEVEL: " + level + "<br>");
         }
      } catch (SQLException e) {
         e.printStackTrace();
      }
   }

   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      doGet(request, response);
   }
}
