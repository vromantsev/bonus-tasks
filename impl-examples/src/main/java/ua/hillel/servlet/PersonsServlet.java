package ua.hillel.servlet;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/api/persons/*")
public class PersonsServlet extends HttpServlet {

    private static final String CONTENT_TYPE = "application/json";

    private PersonRepository personRepository;

    @Override
    public void init(ServletConfig config) throws ServletException {
        this.personRepository = new PersonRepositoryImpl();
        super.init(config);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String requestURI = req.getRequestURI();
        String[] uriParts = requestURI.split("/");
        String pathValue = uriParts[uriParts.length - 1];
        String personJson = null;
        if (hasEmail(pathValue)) {
            personJson = getPersonByEmail(pathValue);
        } else {
            personJson = getPersonByEmailAndAge(req, resp);
        }
        if (personJson != null) {
            resp.setStatus(HttpServletResponse.SC_OK);
            resp.setContentType(CONTENT_TYPE);
            resp.setContentLength(personJson.length());
            try (var out = resp.getOutputStream()) {
                out.println(personJson);
                out.flush();
            }
        }
    }

    private boolean hasEmail(String pathValue) {
        return pathValue.contains("@");
    }

    private String getPersonByEmail(String email) {
        return this.personRepository.findByEmail(email).asJson();
    }

    private String getPersonByEmailAndAge(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String fullName = req.getParameter("fullName");
        String age = req.getParameter("age");
        if (fullName == null || age == null) {
            throw new IllegalArgumentException("Parameters [fullName] and [age] must not be null!");
        }
        return this.personRepository.findByFullNameAndAge(fullName, Integer.parseInt(age)).asJson();
    }
}
