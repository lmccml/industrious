package tools;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

public class JModelAndView extends ModelAndView {
    /**
     * 
     * @param viewName
     *            用户自定义的视图，可以添加任意路径
     * @param request
     */
    public JModelAndView(String viewName, HttpServletRequest request) {
        String webPath = CommonUtil.getURL(request);
        if(viewName!=null){
            super.setViewName(viewName);
        }
        super.addObject("webPath", webPath);
        super.addObject("css_version",System.currentTimeMillis());
        String platform = request.getParameter("platform");
        super.addObject("reqSource", StringUtils.isNotBlank(platform) ? "APP" : "BS");
        super.addObject("merchant_no", request.getParameter("merchant_no"));
        super.addObject("mer_no", request.getParameter("mer_no"));
        super.addObject("getCash",request.getParameter("getCash"));
    }

}

