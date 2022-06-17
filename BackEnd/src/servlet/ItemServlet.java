package servlet;

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

@WebServlet(urlPatterns = "/item")
public class ItemServlet extends HttpServlet {

    @Resource(name = "java:comp/env/jdbc/pool")
    DataSource dataSource;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        PrintWriter writer = resp.getWriter();
        try {
            Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM item");
            ResultSet resultSet = preparedStatement.executeQuery();
            JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();

            while (resultSet.next()){
                String id = resultSet.getString(1);
                String name = resultSet.getString(2);
                String QTY = resultSet.getString(3);
                String price = resultSet.getString(4);


                /*System.out.println(id+":"+name+":"+QTY+":"+price);*/

                JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
                objectBuilder.add("id",id);
                objectBuilder.add("name",name);
                objectBuilder.add("QTY",QTY);
                objectBuilder.add("price",price);
                arrayBuilder.add(objectBuilder.build());
            }
            JsonObjectBuilder response = Json.createObjectBuilder();
            response.add("status","200");
            response.add("message","Done");
            response.add("data",arrayBuilder.build());
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

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String itemCode = req.getParameter("itemCode");
        String itemName = req.getParameter("itemName");
        String itemQty = req.getParameter("itemQty");
        String itemPrice = req.getParameter("itemPrice");


        PrintWriter writer = resp.getWriter();
        resp.setContentType("application/json");

        try {
            Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO item VALUE (?,?,?,?)");
            preparedStatement.setObject(1,itemCode);
            preparedStatement.setObject(2,itemName);
            preparedStatement.setObject(3,itemQty);
            preparedStatement.setObject(4,itemPrice);

            if (preparedStatement.executeUpdate() > 0){
                JsonObjectBuilder response = Json.createObjectBuilder();
                response.add("status","200");
                response.add("message","Successfully Added");
                response.add("data","");
                writer.print(response.build());
            }

            connection.close();
        } catch (SQLException e) {
            JsonObjectBuilder response = Json.createObjectBuilder();
            response.add("status", 400);
            response.add("message",e.getLocalizedMessage() );
            response.add("data", "Error");
            writer.print(response.build());
            resp.setStatus(HttpServletResponse.SC_OK);
        }

    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String itemCode = req.getParameter("itemCode");
        PrintWriter writer = resp.getWriter();
        resp.setContentType("application/json");

        try {
            Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("Delete from item where id=?");
            preparedStatement.setObject(1,itemCode);
            int i = preparedStatement.executeUpdate();
            if ( i > 0){
                JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
                objectBuilder.add("status", 200);
                objectBuilder.add("data", "");
                objectBuilder.add("message", "Successfully Deleted");
                writer.print(objectBuilder.build());
            }else{
                JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
                objectBuilder.add("status", 400);
                objectBuilder.add("data", "Wrong Id Inserted");
                objectBuilder.add("message", "");
                writer.print(objectBuilder.build());
            }
            connection.close();

        } catch (SQLException e) {
            resp.setStatus(HttpServletResponse.SC_CREATED);
            JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
            objectBuilder.add("status", 500);
            objectBuilder.add("message", "Error");
            objectBuilder.add("data", e.getLocalizedMessage());
            writer.print(objectBuilder.build());
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JsonReader reader = Json.createReader(req.getReader());
        JsonObject jsonObject = reader.readObject();
        String itemCode = jsonObject.getString("itemCode");
        String itemName = jsonObject.getString("itemName");
        String itemQty = jsonObject.getString("itemQty");
        String itemPrice = jsonObject.getString("itemPrice");
        PrintWriter writer = resp.getWriter();
        resp.setContentType("application/json");

        System.out.println(itemCode+":"+itemName+":"+itemQty+":"+itemPrice);


        try {
            Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE item SET name=?,QTY=?,price=? where id=?");
            preparedStatement.setObject(4, itemCode);
            preparedStatement.setObject(1, itemName);
            preparedStatement.setObject(2, itemQty);
            preparedStatement.setObject(3, itemPrice);
            int i = preparedStatement.executeUpdate();

            if ( i > 0) {
                JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
                objectBuilder.add("status", 200);
                objectBuilder.add("message", "Successfully Updated");
                objectBuilder.add("data", "");
                writer.print(objectBuilder.build());
            } else {
                JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
                objectBuilder.add("status", 400);
                objectBuilder.add("message", "Update Failed");
                objectBuilder.add("data", "");
                writer.print(objectBuilder.build());
            }
            connection.close();
        } catch (SQLException throwables) {
            JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
            objectBuilder.add("status", 500);
            objectBuilder.add("message", "Update Failed");
            objectBuilder.add("data", throwables.getLocalizedMessage());
            writer.print(objectBuilder.build());
        }
    }
}
