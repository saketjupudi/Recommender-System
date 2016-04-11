import java.util.*;
import java.io.IOException;  
import java.io.PrintWriter;  
import javax.servlet.ServletException;  
import javax.servlet.http.Cookie;  
import javax.servlet.http.HttpServlet;  
import javax.servlet.http.HttpServletRequest;  
import javax.servlet.http.HttpServletResponse;
import java.sql.*;  

/*
    Input : dirty tilte
    output : clean title
*/

public class UserServlet extends HttpServlet { 
    protected void doGet(HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException 
    {
        response.setContentType("text/plain");

        String cType_list[]= new String[3];
        cType_list[]={"movie","music","book"};
        Cookie[] cookies= request.getCookies();
        String name=null;
        
        for (Cookie ck: cookies) {
            if("username".equals(ck.getName()))
                name=ck.getValue();
        }

        String JDBC_DRIVER="oracle.jdbc.driver.OracleDriver";  
        String DB_URL="jdbc:oracle:thin:@localhost:1521:XE"; 
        String USER = "system";
        String PASS = "2710";
        PreparedStatement ps = null;
        ResultSet rs = null;
        
    try {
            // Register JDBC driver
            Class.forName(JDBC_DRIVER);
            //Open the Connection
            Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            //Preparing Query
            if (conn != null)   System.out.println("Connected");
            else    System.out.println("Couldn't connect to Database");
            int i=-1;
            do
            {
                i++;

                ps = conn.prepareStatement("select title,rating from user_"+cType_list[i]+" natural join "+cType_list[i]+" where user_id="+name+"");
                rs = ps.executeQuery();
                int i=1;
                out.println("The results are :");
                while(rs.next() & ((i++)<=10))	out.println(rs.getString("title"));
                if (i==1) out.println("Sorry no results found.");
        

            }while(true);
        }
            catch(Exception e) {
                e.printStackTrace(); //lazy
            }

         finally {
                try {
                    if(rs!=null) rs.close();
                    if(ps!=null) ps.close();
                    }catch(Exception e){e.printStackTrace();}
                } 
    }
}