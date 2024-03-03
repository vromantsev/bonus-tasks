package ua.hillel.servlet.hw.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ua.hillel.servlet.hw.entity.Order;
import ua.hillel.servlet.hw.repository.OrderRepository;
import ua.hillel.servlet.hw.repository.OrderRepositoryImpl;

import java.io.IOException;

@WebServlet("/api/orders/*")
public class OrderServlet extends HttpServlet {

    private OrderRepository orderRepository;
    private ObjectMapper objectMapper;

    @Override
    public void init(ServletConfig config) throws ServletException {
        this.orderRepository = new OrderRepositoryImpl();
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        super.init(config);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uri = req.getRequestURI();
        String[] uriParts = uri.split("/");
        String pathValue = uriParts[uriParts.length - 1];
        if (pathValue != null && !pathValue.isEmpty()) {
            Order order = this.orderRepository.findById(Long.parseLong(pathValue));
            String json = this.objectMapper.writeValueAsString(order);
            resp.setStatus(HttpServletResponse.SC_OK);
            resp.setContentType("application/json");
            resp.setContentLength(json.length());
            try (var out = resp.getOutputStream()) {
                out.println(json);
                out.flush();
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try (var in = req.getInputStream()) {
            Order order = this.objectMapper.readValue(in, Order.class);
            String json = this.objectMapper.writeValueAsString(this.orderRepository.create(order));
            resp.setStatus(HttpServletResponse.SC_OK);
            resp.setContentType("application/json");
            resp.setContentLength(json.length());
            try (var out = resp.getOutputStream()) {
                out.println(json);
                out.flush();
            }
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try (var in = req.getInputStream()) {
            Order order = this.objectMapper.readValue(in, Order.class);
            String json = this.objectMapper.writeValueAsString(this.orderRepository.update(order));
            resp.setStatus(HttpServletResponse.SC_OK);
            resp.setContentType("application/json");
            resp.setContentLength(json.length());
            try (var out = resp.getOutputStream()) {
                out.println(json);
                out.flush();
            }
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uri = req.getRequestURI();
        String[] uriParts = uri.split("/");
        String orderId = uriParts[uriParts.length - 1];
        boolean isOrderDeleted = this.orderRepository.deleteById(Long.parseLong(orderId));
        if (isOrderDeleted) {
            resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
        } else {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
