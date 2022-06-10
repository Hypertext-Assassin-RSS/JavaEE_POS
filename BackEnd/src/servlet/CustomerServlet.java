package servlet;

import javax.annotation.Resource;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author : Rajith Sanjaya
 * @project : JavaEE_POS
 * @created : 2022 June 10
 **/

@WebServlet(urlPatterns = "/customer")
public class CustomerServlet extends HttpServlet {

    @Resource(name = "java:comp/env/jdbc/pool")
    DataSource dataSource;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");//set response type
        PrintWriter writer = resp.getWriter();
        resp.addHeader("Access-Control-Allow-Origin","*");

        try {
            Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM customer");
            ResultSet resultSet = preparedStatement.executeQuery();
            JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();

            while (resultSet.next()){
                String id = resultSet.getString(1);
                String name = resultSet.getString(2);
                String address = resultSet.getString(3);
                double salary = resultSet.getDouble(4);

                JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
                objectBuilder.add("id",id);
                objectBuilder.add("name",name);
                objectBuilder.add("address",address);
                objectBuilder.add("salary",salary);
                arrayBuilder.add(objectBuilder.build());

                System.out.println(id+":"+name+":"+address+":"+salary);
            }

            JsonObjectBuilder response = Json.createObjectBuilder();
            response.add("status", 200);
            response.add("message", "Done");
            response.add("data", arrayBuilder.build());
            writer.print(response.build());

            connection.close();

        } catch (SQLException e) {
            resp.setStatus(HttpServletResponse.SC_CREATED);
            JsonObjectBuilder response = Json.createObjectBuilder();
            response.add("status","501");
            response.add("message","SQL Exception");
            response.add("data",e.getLocalizedMessage());
            writer.print(response.build());
        }

    }
}
