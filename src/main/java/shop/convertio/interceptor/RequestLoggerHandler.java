package shop.convertio.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import shop.convertio.request.RepeatReadServletRequestWrapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RequestLoggerHandler extends HandlerInterceptorAdapter{


    private static Logger LOGGER = LoggerFactory.getLogger("request-log");

    private ThreadLocal<Long> startTime = new ThreadLocal<>();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        startTime.set(System.currentTimeMillis());

        RepeatReadServletRequestWrapper requestWrapper = new RepeatReadServletRequestWrapper(request);
        return super.preHandle(requestWrapper, response, handler);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response,
                           Object handler, ModelAndView modelAndView) throws Exception {

        Long causeTime = System.currentTimeMillis() - startTime.get();

    }
}
