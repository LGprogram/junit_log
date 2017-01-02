package com.kaishengit.web.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * Created by liu on 2016/12/19.
 */
public class LoginFilter extends AbstractFilter {
    private List<String> urlList = null;
    private List<String> urlAdminList = null;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        String validateUrl = filterConfig.getInitParameter("validateUrl");
        urlList = Arrays.asList(validateUrl.split(","));
        String validateAdminUrl = filterConfig.getInitParameter("validateAdminUrl");
        urlAdminList = Arrays.asList(validateAdminUrl.split(","));
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        //获取用户要访问的URL
        String requestUrl = request.getRequestURI();
        if (requestUrl.startsWith("/admin")) {
            if (urlAdminList != null && urlAdminList.contains(requestUrl)) {
                if (request.getSession().getAttribute("curr_admin") == null) {

                    //去管理员登录页面
                    response.sendRedirect("/admin/login?redirect=" + requestUrl);

                } else {
                    filterChain.doFilter(request, response);
                }
            } else {
                filterChain.doFilter(request, response);
            }
        } else {
            if (urlList != null && urlList.contains(requestUrl)) {
                if (request.getSession().getAttribute("curr_user") == null) {
//request.getParameterMap()中将包含你表单里面所有input标签的数据，以其name为key，以其value为值，如果你是以ajax提交的话，就是你自己组织的所有参数了,此时得到的Map是只读的，要用可写的需要使用putAll方法复制生成新的Map
                    Map<String, String[]> map = request.getParameterMap();
                    //Map接口没有自己的遍历方法，只能通过他的Set视图来遍历，
                    Set paramSet = map.entrySet();
                    Iterator<Map.Entry<String, String[]>> it = paramSet.iterator();
                    if (it.hasNext()) {
                        requestUrl += "?";
                        while (it.hasNext()) {
                            Map.Entry<String, String[]> me = it.next();
                            String key = me.getKey();
                            String[] values = me.getValue();
                            String param = "";
                            for (int i = 0; i < values.length; i++) {
                                param = key + "=" + values[i] + "&";
                                requestUrl += param;
                            }

                        }
                        requestUrl = requestUrl.substring(0, requestUrl.length() - 1);
                    }
                    //去登录页面
                    response.sendRedirect("/login?redirect=" + requestUrl);
                } else {
                    filterChain.doFilter(request, response);
                }
            } else {
                filterChain.doFilter(request, response);
            }
        }
    }
}
