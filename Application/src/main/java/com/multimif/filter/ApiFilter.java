package com.multimif.filter;

import javax.servlet.*;
import javax.servlet.http.*;


// Implements Filter class
public class ApiFilter implements Filter  {

    FilterConfig config;

    public void setFilterConfig(FilterConfig config) {
        this.config = config;
    }

    public FilterConfig getFilterConfig() {
        return config;
    }

    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws java.io.IOException, ServletException {

        //ServletContext context = getFilterConfig().getServletContext();

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response;

        System.out.println("[Application] [FILTER] url: " + request.getRequestURI());

/*
        final String urlWrapped = "/auth/connect"; //req.getRequestURI().replace("App","api");
        ServletRequest requestModified =
                new HttpServletRequestWrapper((HttpServletRequest) request) {
                    @Override
                    public String getRequestURI(){
                        return urlWrapped;
                    }
                };

        ServletContext forwardContext = config.getServletContext().getContext("/API");
        System.out.println(forwardContext.getContextPath());
        System.out.println(forwardContext.getServletContextName());
        System.out.println(forwardContext.getServerInfo());
        System.out.println(forwardContext.getServletRegistration("/api"));
        forwardContext.getRequestDispatcher("/api").forward(requestModified,res);

        //((HttpServletResponse) res).sendRedirect(request.getRequestURI().replace("/App","/api"));



        // Vérifié si l'utilsiateur est connecté





        //chain.doFilter(requestModified,response);*/
        chain.doFilter(req,res);
    }

    public void init(FilterConfig config) throws ServletException {
        setFilterConfig(config);
    }

    public void destroy() {
    }


}