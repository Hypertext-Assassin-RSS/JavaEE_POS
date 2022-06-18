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
 * @created : 2022 June 17
 **/

@WebServlet(urlPatterns = "/order")
public class OrderServlet extends HttpServlet {

    @Resource(name = "java:comp/env/jdbc/pool")
    DataSource dataSource;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String object = req.getParameter("object");
        PrintWriter writer = resp.getWriter();
        try {
        switch (object){
            case "customer" :
                    String cusID = req.getParameter("cusID");
                    resp.setContentType("application/json");
                    Connection connection = dataSource.getConnection();
                    PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM customer WHERE id = ?");
                    preparedStatement.setObject(1,cusID);
                    ResultSet resultSet = preparedStatement.executeQuery();
                    JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
                    while (resultSet.next()){
                        String id = resultSet.getString(1);
                        String name = resultSet.getString(2);
                        String address = resultSet.getString(3);
                        String salary = resultSet.getString(4);

                        System.out.println(id+":"+name+":"+address+":"+salary);

                        JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
                        objectBuilder.add("id",id);
                        objectBuilder.add("name",name);
                        objectBuilder.add("address",address);
                        objectBuilder.add("salary",salary);
                        arrayBuilder.add(objectBuilder.build());
                    }
                    JsonObjectBuilder response = Json.createObjectBuilder();
                    response.add("status",201);
                    response.add("message","Done");
                    response.add("data",arrayBuilder.build());
                    writer.print(response.build());

                    connection.close();

                break;
            case "item" :

                break;
        }

    } catch (SQLException e) {
        JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
        objectBuilder.add("status", 500);
        objectBuilder.add("message", "Update Failed");
        objectBuilder.add("data", e.getLocalizedMessage());
        writer.print(objectBuilder.build());
    }
        
    }


}
