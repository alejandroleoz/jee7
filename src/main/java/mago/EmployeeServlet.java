package mago;

import com.google.gson.Gson;
import mago.model.Employee;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(urlPatterns = {
        "/employee"
})
public class EmployeeServlet extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee("Alejandro", "Leoz"));
        employees.add(new Employee("Leandro", "Romagnoli"));

        Gson gson = new Gson();
        String jsonData = gson.toJson(employees);

        resp.getWriter().write(jsonData);
    }
}
