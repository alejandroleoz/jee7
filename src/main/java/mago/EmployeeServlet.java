package mago;

import com.google.gson.Gson;
import mago.model.Employee;

import javax.annotation.security.DeclareRoles;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

// Configuration of the Servlet (endpoint, init params, etc)
@WebServlet(
        urlPatterns = {
                "/employee"
        },
        initParams = {
                @WebInitParam(name = "format", value = "JSON")
        }
)

// Declare some security roles
@DeclareRoles({"customRole1", "customRole2"})

// Enable security for all operations and specific for POST operation
@ServletSecurity(
        value = @HttpConstraint(rolesAllowed = {"customRole1", "customRole2"}),
        httpMethodConstraints = {
                @HttpMethodConstraint(value = "POST", rolesAllowed = {"customRole2"})
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

        if ("JSON".equals(this.format)) {
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
        while (line != null) {
            buffer.append(line);
            line = reader.readLine();
        }

        Employee newEmployee = gson.fromJson(buffer.toString(), Employee.class);
        this.employees.add(newEmployee);
    }
}
