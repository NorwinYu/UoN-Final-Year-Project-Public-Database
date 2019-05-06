package net.octacomm.sample.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import net.octacomm.sample.dao.CRUDMapper;
import net.octacomm.sample.domain.DefaultParam;
import net.octacomm.sample.domain.InvitationRequest;
import net.octacomm.sample.domain.User;

public interface FirebaseMapper extends CRUDMapper<InvitationRequest, DefaultParam, Integer> {
	
	public String TABLE_NAME = "invitation_request";

	@Insert("INSERT INTO " + TABLE_NAME + " (fromUserIdx, toUserIdx) VALUE (#{fromUserIdx}, #{toUserIdx})")
	@Override
	int insert(InvitationRequest domain);

	@Override
	InvitationRequest get(Integer id);

	@Override
	int update(InvitationRequest domain);

	@Override
	int delete(Integer id);

	@Override
	int getCountByParam(DefaultParam param);

	@Override
	List<InvitationRequest> getListByParam(int startRow, int pageSize, DefaultParam param);

	@Override
	List<InvitationRequest> getList();
	
	/* 2019.02.07 상태에 따른 정렬 */
	@Select("SELECT i.id, i.toUserIdx, i.fromUserIdx, i.state, DATE_FORMAT(i.created, '%Y-%m-%d %H:%i:%s') AS created, u.nickname  FROM " + TABLE_NAME + " i JOIN user u ON u.userIdx = i.fromUserIdx WHERE i.toUserIdx IN (SELECT userIdx FROM user_group_mapping WHERE groupCode = (SELECT groupCode FROM user_group_mapping WHERE userIdx = #{userIdx})) GROUP BY i.fromUserIdx ORDER BY ( CASE i.state WHEN 0 THEN 0 ELSE 1 END ), i.created DESC")
	List<InvitationRequest> getInvitationList(@Param("userIdx") int userIdx );
	
	@Select("SELECT COUNT(*) FROM " + TABLE_NAME + " r JOIN user_group_mapping m ON r.toUserIdx = m.userIdx  WHERE r.fromUserIdx = #{fromUserIdx} AND m.groupCode = ( SELECT groupCode FROM user_group_mapping WHERE userIdx = #{toUserIdx} ) AND r.state = 1")
	int getAcceptCount(InvitationRequest domain);
	
	@Select("SELECT COUNT(*) FROM " + TABLE_NAME + " r JOIN user_group_mapping m ON r.toUserIdx = m.userIdx  WHERE r.fromUserIdx = #{fromUserIdx} AND m.groupCode = ( SELECT groupCode FROM user_group_mapping WHERE userIdx = #{toUserIdx} ) ")
	int getCount(InvitationRequest domain);
	
	@Select("select * from " + TABLE_NAME + " where toUserIdx = #{toUserIdx} and fromUserIdx = #{fromUserIdx}")
	InvitationRequest getInvitationUser(InvitationRequest domain);
	
	@Select("SELECT * FROM " + TABLE_NAME + " r JOIN user_group_mapping m ON r.toUserIdx = m.userIdx  WHERE r.fromUserIdx = #{fromUserIdx} AND m.groupCode = ( SELECT groupCode FROM user_group_mapping WHERE userIdx = #{toUserIdx} ) ")
	InvitationRequest getInvitationUserFrom(InvitationRequest domain);
	
	@Update("UPDATE " + TABLE_NAME + " SET created = STR_TO_DATE(#{created}, '%Y-%m-%d %H:%i:%s'), state = 0  WHERE id = #{id }")
	int updateUser(InvitationRequest domain);
	
	@Update("update invitation_request r join user_group_mapping m on r.fromUserIdx = m.userIdx set accessDate = STR_TO_DATE(#{created}, '%Y-%m-%d %H:%i:%s') WHERE r.toUserIdx = #{toUserIdx} and fromUserIdx = #{fromUserIdx}")
	int updateInvitationDate(@Param("toUserIdx") int toUserIdx, @Param("fromUserIdx") int fromUserIdx, @Param("created") String created);
	
	@Delete("DELETE FROM r USING " + TABLE_NAME + " r JOIN user_group_mapping m ON r.toUserIdx = m.userIdx WHERE r.fromUserIdx = #{fromUserIdx} AND m.groupCode = (SELECT groupCode FROM user_group_mapping WHERE userIdx = #{toUserIdx})")
	int invitationRefusal(@Param("toUserIdx") int toUserIdx, @Param("fromUserIdx") int fromUserIdx);
	
	@Update("UPDATE " + TABLE_NAME + " SET state = #{type } WHERE toUserIdx = #{toUserIdx } AND fromUserIdx = #{fromUserIdx }")
	int setInvitationType( @Param("type") int type, @Param("toUserIdx") int toUserIdx, @Param("fromUserIdx") int fromUserIdx);
	
	@Select("select count(*) from " + TABLE_NAME + " where fromUserIdx = #{userIdx} and state = 0")
	int checkInvitationState( @Param("userIdx") int userIdx );
}
