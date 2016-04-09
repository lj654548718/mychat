package com.mychat.web.fore;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.mychat.bean.UserBean;
import com.mychat.common.manager.UserManager;
import com.mychat.common.util.Constants;
import com.mychat.common.util.AppContextHelper;
import com.mychat.common.util.CommonHelper;

@Controller
public class RegisterController extends BaseController{
	
	private UserManager userManager = (UserManager) AppContextHelper.getInstance().getBean(UserManager.class);
	
	@RequestMapping("/register.html")
	public String execute(Model model) {
		return "/fore/register/index.html";
	}
	
	@RequestMapping(value="register.action",method= RequestMethod.POST)
	public String register(HttpServletRequest request,HttpServletResponse response) {
		String username = request.getParameter("username");
		String password = request.getParameter("password1");
		UserBean user= new UserBean();
		user.setName(username);
		user.setPassword(CommonHelper.md5(password));
		user.setIcon("default.png");
		userManager.saveUser(user);
		return "redirect:login.html";
	}
}
