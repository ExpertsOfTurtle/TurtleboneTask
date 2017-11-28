package com.turtlebone.task.filter;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.alibaba.fastjson.JSON;
import com.turtlebone.task.bean.ResultVO;
import com.turtlebone.task.util.StringUtil;

import lombok.Data;

@Data
public class SecurityFilter implements Filter {
	private static Logger logger = LoggerFactory.getLogger(SecurityFilter.class);
	
	private String excludeRegex;
	private String[] excludePaths;
	
	private Environment env;
	
	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse rsp, FilterChain filterChain)
			throws IOException, ServletException {
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		String path = request.getRequestURI();
		logger.info("RequestURI={}", path);
		
		if (checkExclude(path)) {
			filterChain.doFilter(req, rsp);	
			return;
		}
		
		String tokenId = request.getParameter("tokenId");
		request.setAttribute("username", tokenId);
		logger.info("tokenId={}", tokenId);
		
		if (StringUtil.isEmpty(tokenId)) {
			rsp.setContentType("application/json");
			rsp.getWriter().print(JSON.toJSONString(new ResultVO<String>(ResultVO.PARAMERROR, "tokenId is empty", "")));
			return;
		}
		
		filterChain.doFilter(req, rsp);
	}
	
	private boolean checkExclude(String path) {
		if (excludePaths == null) {
			excludePaths = excludeRegex.split("\\|");
		}
		for (String ep : excludePaths) {
            if (ep == null || ep.equals("")) {
                continue;
            }
            Pattern p = Pattern.compile(ep);
            Matcher m = p.matcher(path);
            if (m.find()) {
                return true;
            }
        }
        return false;
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		setExcludeRegex(getPropertyFromInitParams(filterConfig, "excludeRegex", null));
	}
	protected final String getPropertyFromInitParams(final FilterConfig filterConfig, final String propertyName, final String defaultValue)  {
        final String value = filterConfig.getInitParameter(propertyName);

        if (StringUtils.isNotBlank(value)) {
            logger.info("Property [" + propertyName + "] loaded from FilterConfig.getInitParameter with value [" + value + "]");
            return value;
        }

        final String value2 = filterConfig.getServletContext().getInitParameter(propertyName);

        if (StringUtils.isNotBlank(value2)) {
        	logger.info("Property [" + propertyName + "] loaded from ServletContext.getInitParameter with value [" + value2 + "]");
            return value2;
        }
        
        logger.info("Property [" + propertyName + "] not found.  Using default value [" + defaultValue + "]");
        return defaultValue;
    }
}
