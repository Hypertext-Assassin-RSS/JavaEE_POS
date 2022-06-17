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


                System.out.println(id+":"+name+":"+QTY+":"+price);

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
}
