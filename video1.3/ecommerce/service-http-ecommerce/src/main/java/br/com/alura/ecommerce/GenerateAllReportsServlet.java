package br.com.alura.ecommerce;

import org.eclipse.jetty.servlet.Source;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

public class GenerateAllReportsServlet extends HttpServlet {

    // different approach to show the optionality and issues
    // when you dont have simple constructor+destructor lifecycle
    private Optional<KafkaDispatcher<String>> dispatcher;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        this.dispatcher = Optional.of(new KafkaDispatcher<>());
    }

    @Override
    public void destroy() {
        super.destroy();
        dispatcher.ifPresent(KafkaDispatcher::close);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if(dispatcher.isEmpty()) {
            throw new ServletException("Servlet was not initialized correctly");
        }
        try {
            // you could check for the presence with ifPresent and invoke another method
            dispatcher.get().send("ECOMMERCE_SEND_MESSAGE_FOR_EVERY_USER", Math.random() + "", "ECOMMERCE_USER_GENERATE_READING_REPORT");
            System.out.println("I just sent a message to ECOMMERCE_SEND_MESSAGE_FOR_EVERY_USER");

            resp.setStatus(HttpServletResponse.SC_OK);
            resp.getWriter().println("Reports being generated");

        } catch (ExecutionException | InterruptedException e) {
            throw new ServletException(e);
        }
    }
}
