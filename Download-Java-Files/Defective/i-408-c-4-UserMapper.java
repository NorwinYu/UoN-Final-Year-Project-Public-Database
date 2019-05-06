package net.octacomm.sample.dao.mapper;


import net.octacomm.sample.dao.CRUDMapper;
import net.octacomm.sample.domain.DefaultParam;
import net.octacomm.sample.domain.User;

import java.util.List;

import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * The MyBatis Mapper of "user" table  
 * 
 * @author tykim.
 * 
 */

@CacheNamespace
public interface UserMapper extends CRUDMapper<User, DefaultParam, Integer>{
	
	public String INSERT_FIELDS = " ( userIdx, email, password, nickname, sex, user.country , groupId,  createDate )";
	
	public String INSERT_VALUES = " ( null, #{email}, #{password} , #{nickname} , #{sex} , #{country} , #{groupId}, now() )";
	
	public String TABLE_NAME = " user ";
	
	public String UPDATE_VALUES = " email = #{email} , password = #{password} , nickname = #{nickname} , sex = #{sex} , country = #{country} , groupId = #{groupId} , createDate = now() ";
	
	public String BASIC_INFO_UPDATE_VALUES = "  nickname = #{nickname} , USER.country = #{country} , USER.sex = #{sex}";
	
	public String SELECT_FIELDS = "  user.userIdx , user.email, user.password, user.nickname, user.sex, user.country , user.pushToken, user.userCode, user_group_mapping.groupCode , user_group_mapping.accessType, DATE_FORMAT(user.createDate, \"%Y-%l-%d\") AS createDate ";
	
	int insert(User user);
	
	@Select("SELECT * FROM " + TABLE_NAME + " WHERE userIdx =  #{userIdx}")
	@Override
	User get(Integer id);
	
	@Update("UPDATE " + TABLE_NAME + " SET " + UPDATE_VALUES + " WHERE userIdx =  #{userIdx}")
	@Override
	int update(User user);
	
	@Delete("DELETE FROM " + TABLE_NAME + " WHERE userIdx =  #{userIdx}")
	@Override
	int delete(Integer id);
	
	@Select("SELECT * FROM " + TABLE_NAME)
	@Override
	List<User> getList();
	
	@Select("SELECT * FROM " + TABLE_NAME + " WHERE email = #{email} ")
	User getUser(User user);
	
	@Select("SELECT * FROM " + TABLE_NAME + " WHERE email = #{email} ")
	List<User> getUserList(User user);

	@Select("SELECT " + SELECT_FIELDS + " FROM " + TABLE_NAME + " join user_group_mapping on user_group_mapping.userIdx = user.userIdx where user.email =  #{email} AND user.password = #{password} ")
	User getUserForAuth(User user);
	
	@Select("SELECT * FROM " + TABLE_NAME + " join user_group_mapping on user_group_mapping.userIdx = user.userIdx where user.email =  #{email} AND user.password = #{password} ")
	User login(User user);
	
	/* 2019.02.07 순서 정렬을 위한 조인 및 서브쿼리 */
	@Select("SELECT * FROM user JOIN (SELECT m.*, r.accessDate FROM user_group_mapping m LEFT JOIN (SELECT * FROM invitation_request WHERE toUserIdx IN (SELECT userIdx FROM user_group_mapping WHERE groupCode = #{groupCode})) r ON r.fromUserIdx = m.userIdx WHERE m.groupCode = #{groupCode} GROUP BY m.userIdx) AS user_group_mapping ON user_group_mapping.groupCode = #{groupCode} AND user.userIdx = user_group_mapping.userIdx ORDER BY (CASE user_group_mapping.accessType WHEN 1 THEN 0 ELSE 1 END), user_group_mapping.accessDate ASC")
	List<User> getGroupMemner(@Param("groupCode") String groupCode);
	
	@Update("UPDATE " + TABLE_NAME + " SET " + BASIC_INFO_UPDATE_VALUES + " WHERE userIdx =  #{userIdx} ")
	Integer updateBasic(User user);
	
	@Update("UPDATE " + TABLE_NAME + " SET password = #{password}  WHERE userIdx =  #{userIdx} ")
	int updateUsetPassowrd(User user);
	
	@Select("SELECT * FROM " + TABLE_NAME + " WHERE  email = #{email}")
	User getByUserEmail(String email);
	
	@Update("UPDATE " + TABLE_NAME + " SET userCode = #{userCode}  WHERE userIdx =  #{userIdx} ")
	int updateForUsercode(User user);
	
	@Update("UPDATE " + TABLE_NAME + " SET pushToken = #{pushToken} WHERE userIdx =  #{userIdx} ")
	int saveFCMToken ( User user );
	
	@Update("UPDATE " + TABLE_NAME + " SET groupCode = ( SELECT groupCode FROM (SELECT * FROM " + TABLE_NAME +  ") AS map WHERE userIdx = #{toUserIdx} )  WHERE userIdx = #{fromUserIdx}")
	int invitationApproval(@Param("toUserIdx") int toUserIdx, @Param("fromUserIdx") int fromUserIdx);
	
	@Select("SELECT COUNT(*) FROM device WHERE GroupCode = (SELECT m.groupCode FROM " + TABLE_NAME + " u JOIN user_group_mapping m ON u.userIdx = m.userIdx WHERE u.userIdx = #{userIdx})")
	int getDeviceCount( @Param("userIdx") int userIdx );
	
	@Select("SELECT count(pushToken = (SELECT pushToken FROM " + TABLE_NAME + " WHERE userIdx = #{userIdx})) FROM " + TABLE_NAME + " WHERE pushToken = #{pushToken}")
	int getFCMTokenOverlapCheck( User user);
	
	@Update("UPDATE " + TABLE_NAME + " SET pushToken = NULL WHERE pushToken = #{pushToken }")
	int removeFCMToken( @Param("pushToken") String pushToken );
	
	@Select("SELECT m.groupCode FROM " + TABLE_NAME + " AS u JOIN user_group_mapping m ON u.userIdx = m.userIdx WHERE u.userIdx = #{toUserIdx }")
	String getGroupCode( @Param("toUserIdx") int toUserIdx );
}
