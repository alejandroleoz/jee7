package mago;

import com.google.gson.Gson;
import mago.model.Employee;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(
    urlPatterns = {
            "/employee"
    },
    initParams = {
            @WebInitParam(name="format", value = "JSON")
    }
)
public class EmployeeServlet extends HttpServlet {

    private List<Employee> employees;
    private String format;
    private Gson gson;

    @Override
    public void init(ServletConfig config) throws ServletException {
        employees = new ArrayList<>();
        employees.add(new Employee("Alejandro", "Leoz"));
        employees.add(new Employee("Leandro", "Romagnoli"));

        this.format = config.getInitParameter("format");
        this.gson = new Gson();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        if("JSON".equals(this.format)){
            String jsonData = gson.toJson(employees);
            resp.getWriter().write(jsonData);
        } else {
            resp.getWriter().write("Output format not supported");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        StringBuffer buffer = new StringBuffer();

        BufferedReader reader = req.getReader();
        String line = reader.readLine();
        while(line != null) {
            buffer.append(line);
            line= reader.readLine();
        }

        Employee newEmployee = gson.fromJson(buffer.toString(), Employee.class);
        this.employees.add(newEmployee);
    }
}
