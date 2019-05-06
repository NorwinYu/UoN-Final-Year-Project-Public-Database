package net.octacomm.sample.dao.mapper;


import net.octacomm.sample.dao.CRUDMapper;
import net.octacomm.sample.domain.DefaultParam;
import net.octacomm.sample.domain.User;
import net.octacomm.sample.domain.WeightTip;

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
public interface WeightTipMapper extends CRUDMapper<WeightTip, DefaultParam, Integer>{
	
	public String INSERT_FIELDS = " ( tipIdx, language, bcs, title, content )";
	
	public String INSERT_VALUES = " ( null, #{language}, #{bcs} , #{title} , #{content} )";
	
	public String TABLE_NAME = " weight_tip ";
	
	public String UPDATE_VALUES = " language = #{language} , bcs = #{bcs} , title = #{title} , content = #{content}  ";
	
	public String SELECT_FIELDS = "  tipIdx, language, bcs, title, content ";
	
	int insert(User user);
	
	@Select("SELECT * FROM " + TABLE_NAME + " WHERE tipIdx =  #{tipIdx}")
	@Override
	WeightTip get(Integer id);
	
	@Update("UPDATE " + TABLE_NAME + " SET " + UPDATE_VALUES + " WHERE tipIdx =  #{tipIdx}")
	@Override
	int update(WeightTip weightTip);
	
	@Delete("DELETE FROM " + TABLE_NAME + " WHERE tipIdx =  #{tipIdx}")
	@Override
	int delete(Integer id);
	
	@Select("SELECT * FROM " + TABLE_NAME)
	@Override
	List<WeightTip> getList();
	
	@Select("SELECT * FROM " + TABLE_NAME + " WHERE language = #{language} and bcs = #{bcs} ")
	WeightTip getCountryMessage(WeightTip weightTip);

}
