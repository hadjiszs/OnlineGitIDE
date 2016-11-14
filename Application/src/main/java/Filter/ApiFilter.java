package main.java.Filter;

import javax.servlet.*;
import javax.servlet.http.*;


// Implements Filter class
public class ApiFilter implements Filter  {
    public void  init(FilterConfig config) throws ServletException{
        System.out.println("[Application] [FILTER] Init");
    }
    public void  doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws java.io.IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        System.out.println("[Application] [FILTER] url: " + req.getRequestURI());

        // Vérifié si l'utilsiateur est connecté



        res.sendRedirect("/api/" + req.getRequestURI().replace("/App",""));

        // Pass request back down the filter chain
        //chain.doFilter(request,response);
    }
    public void destroy( ){
      /* Called before the Filter instance is removed
      from service by the web container*/
    }
}