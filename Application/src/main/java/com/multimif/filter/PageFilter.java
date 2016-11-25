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
        if(request.getRequestURI().contains("/JSP/")){
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