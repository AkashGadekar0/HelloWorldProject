package org.example;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;

public class HelloServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        out.println("<h1>Hello, Akash!</h1>");

        int maxRetries = 10;
        int attempt = 0;
        boolean connected = false;

        while (attempt < maxRetries && !connected) {
            try {
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                Connection conn = DriverManager.getConnection(
                        "jdbc:sqlserver://java-servlet-app-mssql:1433;databaseName=master;encrypt=true;trustServerCertificate=true",
                        "sa",
                        "YourStrong!Passw0rd"
                );
                out.println("<h2>Connected to MSSQL successfully!</h2>");
                conn.close();
                connected = true;
            } catch (Exception e) {
                out.println("<p>Attempt " + (attempt + 1) + " failed: " + e.getMessage() + "</p>");
                attempt++;
                try {
                    Thread.sleep(5000); // Wait 5 seconds before retrying
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                }
            }
        }

        if (!connected) {
            out.println("<h2> Failed to connect to MSSQL after " + maxRetries + " attempts.</h2>");
        }


    }
}
