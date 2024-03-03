package ua.hillel.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/session-example")
public class SessionExample extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        try (PrintWriter out = response.getWriter()) {
            // Отримання або створення сесії
            HttpSession session = request.getSession();

            // Перевірка, чи вже є атрибут у сесії
            Integer visitCount = (Integer) session.getAttribute("visitCount");
            if (visitCount == null) {
                visitCount = 1;
            } else {
                visitCount++;
            }

            // Збереження атрибутів у сесії
            session.setAttribute("visitCount", visitCount);

            // Виведення HTML-відповіді
            out.println("<html><head><title>Session Example</title></head><body>");
            out.println("<h2>Session Example</h2>");
            out.println("<p>Number of visits: " + visitCount + "</p>");
            out.println("</body></html>");
        }
    }
}
