package Filter;

import Service.UserGrantService;

import javax.servlet.*;
import javax.servlet.http.*;

/**
 * Created by Alexis on 16/11/2016.
 */

public class RequestFilter implements Filter{

    FilterConfig config;
    private UserGrantService userGrantService;

    public void setFilterConfig(FilterConfig config) {
        this.config = config;
    }

    public FilterConfig getFilterConfig() {
        return config;
    }

    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws java.io.IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) req;
        System.out.println("[API] [FILTER] url: " + request.getRequestURI());


        Cookie[] cookies = request.getCookies();

        if(cookies != null){
            for (int i = 0; i < cookies.length; i++) {
                String name = cookies[i].getName();
                String value = cookies[i].getValue();
                System.out.println(name + " " + value);
            }
        }

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
