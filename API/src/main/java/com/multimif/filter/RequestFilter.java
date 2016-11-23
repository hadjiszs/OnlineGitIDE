package com.multimif.filter;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * @author Alexis
 * @version 1.0
 * @since 1.0 16/11/2016.
 */

public class RequestFilter implements Filter{

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

        Cookie[] cookies = request.getCookies();

        String hashkey = null;

        if (cookies != null) {
            for (int i = 0; i < cookies.length; i++) {
                if(cookies[i].getName().equals("password")){
                    hashkey = cookies[i].getValue();
                }

                String name = cookies[i].getName();
                String value = cookies[i].getValue();
                System.out.println(name + " " + value);
            }
        }

        System.out.println("[API] [FILTER] hashkey: " + hashkey);
        System.out.println("[API] [FILTER] url: " + request.getRequestURI());

        chain.doFilter(req,res);
        /*if(request.getRequestURI().contains("/auth/connect")){
            chain.doFilter(req,res);
        }
        // On vérifie que l'utilisateur est connecté
        if(request.getSession() != null){
            chain.doFilter(req,res);
        }*/



        /*final String urlWrapped = ((HttpServletResponse) res)
        ServletRequest requestModified =
                new HttpServletRequestWrapper((HttpServletRequest) request) {
                    @Override
                    public String getRequestURI(){
                        return urlWrapped;
                    }
                };
*/

        /*System.out.println(((HttpServletRequest) req).getRequestURI());
        chain.doFilter(req,res);*/
    }

    public void init(FilterConfig config) throws ServletException {
        this.config = config;
    }

    public void destroy() {
    }
}
