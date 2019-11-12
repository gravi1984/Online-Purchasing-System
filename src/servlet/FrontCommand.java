package servlet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @program: CoffeeWeb
 * @description: front command
 * @author: DennyLee
 * @create: 2019-09-05 23:55
 **/
public abstract class FrontCommand {
    protected ServletContext context;
    protected HttpServletRequest request;
    protected HttpServletResponse response;

    public void init(ServletContext context, HttpServletRequest request, HttpServletResponse response){
        this.context = context;
        this.request = request;
        this.response=  response;
    }

    abstract public void process() throws ServletException, IOException;

    /**
     * forward to a jsp page
     * @param target jsp url
     * @throws ServletException servlet exception
     * @throws IOException ioexeption
     */
    protected void forward(String target) throws  ServletException,IOException{
        RequestDispatcher dispatcher = context.getRequestDispatcher(target);
        dispatcher.forward(request,response);
    }

    /**
     * redirect to a request
     * @param target target url
     * @throws ServletException servlet exception
     * @throws IOException ioexeption
     */
    protected void redirect(String target) throws ServletException,IOException{
        response.sendRedirect(target);
    }
}
