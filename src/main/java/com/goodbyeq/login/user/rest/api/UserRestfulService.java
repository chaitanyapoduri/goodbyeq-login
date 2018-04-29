
package com.goodbyeq.login.user.rest.api;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.ModelMap;

import com.goodbyeq.exception.GBQUserException;
import com.goodbyeq.user.db.bo.UserVO;

public interface UserRestfulService {

	/**
	 * @param userVO
	 * @throws GBQUserException
	 */
	public String addUser(UserVO userVO, ModelMap model, HttpServletRequest request,  HttpServletResponse response) throws GBQUserException;

	/**
	 * @param userVO
	 * @return
	 * @throws GBQUserException
	 */
	public RestOperationStatusVOX updateUser(final UserVO userVO) throws GBQUserException;
	
	/**
	 * @param userID
	 * @return
	 * @throws GBQUserException
	 */
	public RestOperationStatusVOX updateUserPassword(final UserVO userVO) throws GBQUserException;

	/**
	 * @param userID
	 * @return
	 * @throws GBQUserException
	 */
	public RestOperationStatusVOX deleteUser(final String userID) throws GBQUserException;
	

	/**
	 * @param issueTokenType
	 * @param tokenReceivingID
	 * @return
	 * @throws GBQUserException
	 */
	public RestOperationStatusVOX getSingleUseToken(final String issueTokenType,final String tokenReceivingID) throws GBQUserException;

	/**
	 * @param userID
	 * @return
	 * @throws GBQUserException
	 */
	public RestOperationStatusVOX getUser(final String userID,final String userIDType) throws GBQUserException;

	public String verifyUser(ModelMap model, String verifyCode, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) throws GBQUserException;
}
