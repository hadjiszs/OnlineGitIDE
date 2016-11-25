package com.multimif.filter;

import javax.servlet.*;
import javax.servlet.http.*;


// Implements Filter class
public class PageFilter implements Filter  {

    FilterConfig config;

    public void setFilterConfig(FilterConfig config) {
        this.config = config;
    }

    public FilterConfig getFilterConfig() {
        return config;
    }

    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws java.io.IOException, ServletException {


        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        Cookie[] cookies = request.getCookies();
        System.out.println("[Application] [FILTER] url: " + request.getRequestURI());
        if(request.getRequestURI().equals("/") || request.getRequestURI().contains("/ressources")){
            chain.doFilter(req,res);
        }else{
            if(cookies == null){
                unauthorized(response,"Vous n'êtes pas connecté.");
            } else{
                String password = null;
                String username = null;

                for (int i = 0; i < cookies.length; i++) {
                    if(cookies[i].getName().equals("password")){
                        password = cookies[i].getValue();
                    }else if(cookies[i].getName().equals("username")){
                        username = cookies[i].getValue();
                    }
                }

                if(password == null || username == null){
                    unauthorized(response,"Vous n'êtes pas connecté.");
                }else{
                    chain.doFilter(req,res);
                }
            }
        }









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
        //chain.doFilter(req,res);
    }

    public void init(FilterConfig config) throws ServletException {
        setFilterConfig(config);
    }

    public void destroy() {
    }

    // Renvoi une erreur si pas connecté
    private void unauthorized(HttpServletResponse response, String message) throws java.io.IOException {
        System.out.println("[APPLICATION] [FILTER] [ERROR] user not connected.");
        response.sendError(401, message);
    }


}