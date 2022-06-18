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
        PrintWriter writer = resp.getWriter();
        resp.setContentType("application/json");
        try {
            String object = req.getParameter("object");
            Connection connection = dataSource.getConnection();
        switch (object){
            case "customer" :
                    String cusID = req.getParameter("cusID");
                    PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM customer WHERE id = ?");
                    preparedStatement.setObject(1,cusID);
                    JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
                    ResultSet resultSet = preparedStatement.executeQuery();
                    while (resultSet.next()){
                        String id = resultSet.getString(1);
                        String name = resultSet.getString(2);
                        String address = resultSet.getString(3);
                        String salary = resultSet.getString(4);

                        /*System.out.println(id+":"+name+":"+address+":"+salary);*/

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
                String itemCode = req.getParameter("itemCode");
                PreparedStatement preparedStatement1 = connection.prepareStatement("SELECT * FROM item WHERE id=?");
                preparedStatement1.setObject(1,itemCode);
                JsonArrayBuilder arrayBuilder1 = Json.createArrayBuilder();
                ResultSet resultSet1 = preparedStatement1.executeQuery();
                while (resultSet1.next()){
                    String id = resultSet1.getString(1);
                    String name = resultSet1.getString(2);
                    String QTY = resultSet1.getString(3);
                    String price = resultSet1.getString(4);

                    /*System.out.println(id+":"+name+":"+QTY+":"+price);*/


                    JsonObjectBuilder builder = Json.createObjectBuilder();
                    builder.add("id",id);
                    builder.add("name",name);
                    builder.add("QTY",QTY);
                    builder.add("price",price);
                    arrayBuilder1.add(builder.build());
                }
                JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
                objectBuilder.add("status",201);
                objectBuilder.add("message","Done");
                objectBuilder.add("data",arrayBuilder1.build());
                writer.print(objectBuilder.build());

                connection.close();

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
