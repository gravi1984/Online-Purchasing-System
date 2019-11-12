package servlet;

import security.InterceptingValidator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @program: CoffeeWeb
 * @description: front controller to deal with all request
 * @author: DennyLee
 * @create: 2019-09-05 23:53
 **/
@WebServlet("/frontservlet")
public class FrontServlet extends HttpServlet {
    private static final long serialVersioinUID = 1L;

    public FrontServlet() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        if (!InterceptingValidator.SQLValidator(req.getRequestURI())){
            resp.sendError(400,"Invalid URI.");
            return;
        }
        try {
            FrontCommand command = getCommand(req);
            command.init(getServletContext(), req, resp);
            command.process();
        } catch (Exception e) {
            e.printStackTrace();
            resp.sendError(500,"Cannot parse url.");
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    /**
     * get command
     *
     * @param req request
     * @return frontcomman
     */
    private FrontCommand getCommand(HttpServletRequest req) {
        try {
            return (FrontCommand) getCommandClass(req).newInstance();
        } catch (Exception e) {
            System.out.println("Get command fail.");
            return null;
        }
    }

    private Class getCommandClass(HttpServletRequest req) {
        Class result;
        //parse name
        final String commandClassName = "servlet." + (String) req.getParameter("command") +
                "Command";

        try {
            result = Class.forName(commandClassName);
        } catch (ClassNotFoundException e) {
            System.out.println("Get Command Class Fail.");
            result = null;
        }
        return result;
    }
}
