

package com.krm.dbaudit.web.sys.service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.krm.dbaudit.common.base.ServiceMybatis;
import com.krm.dbaudit.common.constant.Constant;
import com.krm.dbaudit.common.utils.CacheUtils;
import com.krm.dbaudit.common.utils.PasswordEncoder;
import com.krm.dbaudit.web.sys.mapper.SysOfficeMapper;
import com.krm.dbaudit.web.sys.mapper.SysRoleMapper;
import com.krm.dbaudit.web.sys.mapper.SysUserMapper;
import com.krm.dbaudit.web.sys.model.SysOffice;
import com.krm.dbaudit.web.sys.model.SysUser;
import com.krm.dbaudit.web.util.SysUserUtils;

/**
 * 
 * @author 
 */

@Service("sysUserService")
public class SysUserService extends ServiceMybatis<SysUser>{

	@Resource
	private SysUserMapper sysUserMapper;
	
	@Resource
	private SysRoleMapper sysRoleMapper;
	
	@Resource
	private SysOfficeMapper sysOfficeMapper;
	
	/**
	 * 添加或更新用户
	* @param sysUser
	* @return
	 */
	public int saveSysUser(SysUser sysUser){
		int count = 0;
		SysOffice sysOffice = sysOfficeMapper.findOfficeCompanyIdByDepId(sysUser.getOfficeId());
		Long companyId = sysUser.getOfficeId();
		if(sysOffice != null){
			companyId = sysOffice.getId();
		}
		sysUser.setCompanyId(companyId);
		if(StringUtils.isNotBlank(sysUser.getPassword())){
			String encryptPwd = PasswordEncoder.encrypt(sysUser.getPassword(), sysUser.getUsername());
			sysUser.setPassword(encryptPwd);
		}else{
			sysUser.remove("password");
		}
		if(null == sysUser.getId()){
			Long id = sysUserMapper.generateId();
			sysUser.setId(id);
			sysUser.setDelFlag("0");
			sysUser.set("status", 0);
			sysUser.set("createDate",new Date() );
			sysUser.set("createBy", SysUserUtils.getCacheLoginUser().getId()+","+
					SysUserUtils.getCacheLoginUser().getName());
			sysUser.set("delFlag", Constant.DEL_FLAG_NORMAL);
			sysUser.setUserType("2");
			count = sysUserMapper.saveUser(sysUser);
		}else{
			sysRoleMapper.deleteUserRoleByUserId(sysUser.getId());
			count = this.updateByPrimaryKeySelective(sysUser);
			
			//清除缓存
			SysUserUtils.clearAllCachedAuthorizationInfo(Arrays.asList(sysUser.getId()));
			if(CacheUtils.isCacheByKey(Constant.CACHE_SYS_USER, sysUser.getId().toString())){
				String userType = this.selectByPrimaryKey(sysUser.getId()).getUserType();
				sysUser.setUserType(userType);
				SysUserUtils.cacheLoginUser(sysUser);
			}
		}
		if(sysUser.getRoleIds()!=null) {
			sysRoleMapper.insertUserRoleByUserId(sysUser);
		}
		return count;
	}
	
	/**
	 * 删除用户
	* @param userId
	* @return
	 */
	public int deleteUser(Long userId){
		sysRoleMapper.deleteUserRoleByUserId(userId);
		SysUserUtils.clearAllCachedAuthorizationInfo(Arrays.asList(userId));
		SysUserUtils.clearCacheUser(userId);
		return this.updateDelFlagToDelStatusById(SysUser.class, userId);
	}
	
	/**
	 * 用户列表
	* @param params
	* @return
	 */
	public PageInfo<SysUser> findPageInfo(Map<String, Object> params) {
		params.put(Constant.CACHE_USER_DATASCOPE, SysUserUtils.dataScopeFilterString("so", null));
		params.put("userType", SysUserUtils.getCacheLoginUser().getUserType());
		PageHelper.startPage(params);
		List<SysUser> list = sysUserMapper.findPageInfo(params);
		return new PageInfo<SysUser>(list);
	}
	
	/**
	 * 验证用户
	* @param username 用户名
	* @param password 密码
	* @return user
	 */
	public SysUser checkUser(String username,String password){
		SysUser sysUser = new SysUser();
		String secPwd = PasswordEncoder.encrypt(password, username);
		sysUser.setUsername(username);
		sysUser.setPassword(secPwd);
		List<SysUser> users = this.select(sysUser);
		if(users != null && users.size() == 1 && users.get(0) != null) {
			return users.get(0);
		}
		return null;
	}
	
}
