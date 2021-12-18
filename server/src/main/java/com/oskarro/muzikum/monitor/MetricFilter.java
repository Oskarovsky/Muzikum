package com.oskarro.muzikum.monitor;

import org.springframework.stereotype.Component;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@WebFilter
public class MetricFilter implements Filter {

    MetricService metricService;

    public MetricFilter(MetricServiceImpl metricService) {
        this.metricService = metricService;
    }

    @Override
    public void init(FilterConfig filterConfig) {
        metricService = WebApplicationContextUtils
                .getRequiredWebApplicationContext(filterConfig.getServletContext())
                .getBean(MetricServiceImpl.class);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = ((HttpServletRequest) request);
        String req = String.format("%s %s", httpServletRequest.getMethod(), httpServletRequest.getRequestURI());
        chain.doFilter(request, response);
        int status = ((HttpServletResponse) response).getStatus();
        metricService.increaseCount(req, status);
    }
}
