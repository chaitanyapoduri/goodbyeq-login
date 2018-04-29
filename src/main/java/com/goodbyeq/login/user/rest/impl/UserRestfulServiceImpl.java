package com.goodbyeq.login.user.rest.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.goodbyeq.authorization.bo.GBQRequestTokenVO;
import com.goodbyeq.exception.GBQUserException;
import com.goodbyeq.login.rest.api.BaseRestController;
import com.goodbyeq.login.user.rest.api.RestOperationStatusVOX;
import com.goodbyeq.login.user.rest.api.UserRestfulService;
import com.goodbyeq.user.db.bo.UserVO;
import com.goodbyeq.user.service.api.GBQUserService;
import com.goodbyeq.user.service.api.UserService;
import com.goodbyeq.util.AuthorizationServiceConstants;

@Controller
public class UserRestfulServiceImpl extends BaseRestController implements UserRestfulService {

	private static final Logger logger = LoggerFactory.getLogger(UserRestfulServiceImpl.class);

	@Autowired
	private UserService userService;

	@Autowired
	private GBQUserService gbqUserService;

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public GBQUserService getGbqUserService() {
		return gbqUserService;
	}

	public void setGbqUserService(GBQUserService gbqUserService) {
		this.gbqUserService = gbqUserService;
	}

	@Override
	@RequestMapping(value = "/user/add", method = RequestMethod.POST)
	public String addUser(UserVO userVO, ModelMap model, HttpServletRequest httpServletRequest,  HttpServletResponse httpServletResponse) throws GBQUserException {
		/*
		 * final @@HttpServletRequest request = ((ServletRequestAttributes)
		 * RequestContextHolder.getRequestAttributes()) .getRequest();
		 */
		String response = null;
		logger.debug("addUser()::userVO:- " + userVO.getEmailID());
		response = getUserService().addUser(userVO, model, httpServletRequest, httpServletResponse);
		GBQRequestTokenVO tokenVO = new GBQRequestTokenVO();
		tokenVO.setUserID(userVO.getEmailID());
		tokenVO.setUserIDType(AuthorizationServiceConstants.USER_ID_TYPE_EMAIL);
		//GoodByeQToken token = getGbqUserService().getSingleUseToken(tokenVO);
		/*try {
			//httpServletResponse.addHeader("Set-Cookie", token + "=" + BaseJsonHelper.getJsonString(token) + "; Path=/" + ";" + "Secure");
			//httpServletResponse.sendRedirect("https://www.google.com");
		} catch (final Exception e) {
			logger.error("Exception while redirecting user", e);
		}*/
		return response;
	}
	
	@Override
	@RequestMapping(value = "/user/verify", method = RequestMethod.POST)
	public String verifyUser(ModelMap model, String verifyCode, HttpServletRequest httpServletRequest,  HttpServletResponse httpServletResponse) throws GBQUserException {
		/*
		 * final HttpServletRequest request = ((ServletRequestAttributes)
		 * RequestContextHolder.getRequestAttributes()) .getRequest();
		 */
		String response = null;
		String email = (String) model.get("emailId");
		logger.debug("verifyUser()::userVO(email):- " + email);
		response = getUserService().verifyUser(email, verifyCode, model, httpServletRequest);
		GBQRequestTokenVO tokenVO = new GBQRequestTokenVO();
		tokenVO.setUserID(email);
		tokenVO.setUserIDType(AuthorizationServiceConstants.USER_ID_TYPE_EMAIL);
		//GoodByeQToken token = getGbqUserService().getSingleUseToken(tokenVO);
		/*try {
			//httpServletResponse.addHeader("Set-Cookie", token + "=" + BaseJsonHelper.getJsonString(token) + "; Path=/" + ";" + "Secure");
			//httpServletResponse.sendRedirect("https://www.google.com");
		} catch (final Exception e) {
			logger.error("Exception while redirecting user", e);
		}*/
		return response;
	}

	@Override
	@RequestMapping(value = "/user/profile", method = RequestMethod.GET)
	public RestOperationStatusVOX getUser(@RequestParam(value = "userID", required = true) final String userID,
			@RequestParam(value = "userIDType", required = true) final String userIDType) throws GBQUserException {
		UserVO userVO = getUserService().getUser(userID, userIDType);
		if (null == userVO) {
			getSuccessRestOperationStatusVOX("No users found in system");
		}
		return getSuccessRestOperationStatusVOX(userVO);
	}

	@Override
	@RequestMapping(value = "/user/update", method = RequestMethod.POST)
	public RestOperationStatusVOX updateUser(@RequestBody final UserVO userVO) throws GBQUserException {
		getUserService().updateUser(userVO);
		return getSuccessRestOperationStatusVOX("User Update succesfull");
	}

	@Override
	@RequestMapping(value = "/user/update-password", method = RequestMethod.POST)
	public RestOperationStatusVOX updateUserPassword(@RequestBody final UserVO userVO) throws GBQUserException {
		getUserService().updateUser(userVO);
		return getSuccessRestOperationStatusVOX("User password update succesfull");
	}

	@Override
	@RequestMapping(value = "/user/delete", method = RequestMethod.DELETE)
	public RestOperationStatusVOX deleteUser(@RequestParam(value = "userID", required = true) final String userID)
			throws GBQUserException {
		getUserService().deleteUser(userID);
		return getSuccessRestOperationStatusVOX("User deletion succesfull");
	}

	@Override
	@RequestMapping(value = "/user/token", method = RequestMethod.GET)
	public RestOperationStatusVOX getSingleUseToken(final String issueTokenType, final String tokenReceivingID)
			throws GBQUserException {
		// getUserService().
		return getSuccessRestOperationStatusVOX("Token sent succesfully");
	}
	
	/*@Override
	@RequestMapping(value = "/user/login", method = RequestMethod.POST)
	public String userLogin(UserVO userVO, ModelMap model, HttpServletRequest httpServletRequest,  HttpServletResponse httpServletResponse) throws GBQUserException {
		
		 * final @@HttpServletRequest request = ((ServletRequestAttributes)
		 * RequestContextHolder.getRequestAttributes()) .getRequest();
		 
		String response = null;
		logger.debug("addUser()::userVO:- " + userVO.getEmailID());
		response = getUserService().addUser(userVO, model, httpServletRequest, httpServletResponse);
		GBQRequestTokenVO tokenVO = new GBQRequestTokenVO();
		tokenVO.setUserID(userVO.getEmailID());
		tokenVO.setUserIDType(AuthorizationServiceConstants.USER_ID_TYPE_EMAIL);
		//GoodByeQToken token = getGbqUserService().getSingleUseToken(tokenVO);
		try {
			//httpServletResponse.addHeader("Set-Cookie", token + "=" + BaseJsonHelper.getJsonString(token) + "; Path=/" + ";" + "Secure");
			//httpServletResponse.sendRedirect("https://www.google.com");
		} catch (final Exception e) {
			logger.error("Exception while redirecting user", e);
		}
		return response;
	}*/
}