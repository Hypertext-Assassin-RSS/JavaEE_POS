package servlet;

import netscape.javascript.JSObject;

import javax.annotation.Resource;
import javax.json.*;
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
            switch (object) {
                case "customer":
                    String cusID = req.getParameter("cusID");
                    PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM customer WHERE id = ?");
                    preparedStatement.setObject(1, cusID);
                    JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
                    ResultSet resultSet = preparedStatement.executeQuery();
                    while (resultSet.next()) {
                        String id = resultSet.getString(1);
                        String name = resultSet.getString(2);
                        String address = resultSet.getString(3);
                        String salary = resultSet.getString(4);

                        /*System.out.println(id+":"+name+":"+address+":"+salary);*/

                        JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
                        objectBuilder.add("id", id);
                        objectBuilder.add("name", name);
                        objectBuilder.add("address", address);
                        objectBuilder.add("salary", salary);
                        arrayBuilder.add(objectBuilder.build());
                    }
                    JsonObjectBuilder response = Json.createObjectBuilder();
                    response.add("status", 201);
                    response.add("message", "Done");
                    response.add("data", arrayBuilder.build());
                    writer.print(response.build());

                    connection.close();

                    break;
                case "item":
                    String itemCode = req.getParameter("itemCode");
                    PreparedStatement preparedStatement1 = connection.prepareStatement("SELECT * FROM item WHERE id=?");
                    preparedStatement1.setObject(1, itemCode);
                    JsonArrayBuilder arrayBuilder1 = Json.createArrayBuilder();
                    ResultSet resultSet1 = preparedStatement1.executeQuery();
                    while (resultSet1.next()) {
                        String id = resultSet1.getString(1);
                        String name = resultSet1.getString(2);
                        String QTY = resultSet1.getString(3);
                        String price = resultSet1.getString(4);

                        /*System.out.println(id+":"+name+":"+QTY+":"+price);*/


                        JsonObjectBuilder builder = Json.createObjectBuilder();
                        builder.add("id", id);
                        builder.add("name", name);
                        builder.add("QTY", QTY);
                        builder.add("price", price);
                        arrayBuilder1.add(builder.build());
                    }
                    JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
                    objectBuilder.add("status", 201);
                    objectBuilder.add("message", "Done");
                    objectBuilder.add("data", arrayBuilder1.build());
                    writer.print(objectBuilder.build());

                    connection.close();

                    break;
                case "order":
                    PreparedStatement preparedStatement2 = connection.prepareStatement("SELECT * FROM `order` ORDER BY orderID DESC LIMIT 1");
                    ResultSet resultSet2 = preparedStatement2.executeQuery();
                    JsonArrayBuilder arrayBuilder2 = Json.createArrayBuilder();
                    JsonObjectBuilder objectBuilder1 = Json.createObjectBuilder();
                    while (resultSet2.next()) {
                        String orderID = resultSet2.getString(1);

                        String letter=orderID.replaceAll("[^A-Za-z]", "");
                        int number= Integer.parseInt(orderID.replaceAll("[^0-9]", ""));
                        String newOrderID = letter+"00" + (number +1);

                        objectBuilder1.add("orderID", newOrderID);
                        JsonObject build = objectBuilder1.build();
                        writer.print(build);
                    }

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

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JsonReader reader = Json.createReader(req.getReader());
        JsonObject jsonObject = reader.readObject();
        String orderID = jsonObject.getString("orderID");
        String customerID = jsonObject.getString("customerID");
        String orderDate = jsonObject.getString("orderDate");
        /*JsonValue item = jsonObject.get("item");*/
        String total = jsonObject.getString("total");

        PrintWriter writer = resp.getWriter();
        resp.setContentType("application/json");

        System.out.println(orderID + ":" + customerID + ":" + orderDate + ":" + total);

        try {
            Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO `order` VALUE (?,?,?,?)");
            preparedStatement.setObject(1, orderID);
            preparedStatement.setObject(2, orderDate);
            preparedStatement.setObject(3, customerID);
            preparedStatement.setObject(4, total);

            int i = preparedStatement.executeUpdate();

            if (i > 0) {
                JsonObjectBuilder response = Json.createObjectBuilder();
                response.add("status", "200");
                response.add("message", "Successfully Added");
                response.add("data", "");
                writer.print(response.build());
            }
            connection.close();

        } catch (SQLException e) {
            JsonObjectBuilder response = Json.createObjectBuilder();
            response.add("status", 400);
            response.add("message", e.getLocalizedMessage());
            response.add("data", "Error");
            writer.print(response.build());
            resp.setStatus(HttpServletResponse.SC_OK);
        }
    }
}
