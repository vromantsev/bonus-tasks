package ua.hillel.servlet;


import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/cookie-servlet")
public class CookieServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Отримуємо значення cookie з запиту
        Cookie[] cookies = request.getCookies();

        // Перевіряємо, чи ми отримали cookies
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                String name = cookie.getName();
                String value = cookie.getValue();
                // Виводимо значення кожного cookie
                System.out.println(name + " = " + value);
            }
        } else {
            // Якщо cookies не існують, створюємо новий cookie
            Cookie cookie = new Cookie("username", "guest");
            cookie.setMaxAge(24 * 60 * 60); // Встановлюємо термін дії cookie (1 доба)
            response.addCookie(cookie); // Додаємо cookie до відповіді
        }
    }
}

