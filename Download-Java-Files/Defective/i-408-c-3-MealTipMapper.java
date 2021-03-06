package net.octacomm.sample.dao.mapper;

import net.octacomm.sample.dao.CRUDMapper;
import net.octacomm.sample.domain.DefaultParam;
import net.octacomm.sample.domain.MealTip;
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
public interface MealTipMapper extends CRUDMapper<MealTip, DefaultParam, Integer> {

	public String INSERT_FIELDS = " ( tipIdx, language, title, content )";

	public String INSERT_VALUES = " ( null, #{language}, #{title} , #{content} )";

	public String TABLE_NAME = " weight_tip ";

	public String UPDATE_VALUES = " language = #{language} , title = #{title} , content = #{content}  ";

	public String SELECT_FIELDS = "  tipIdx, language, title, content ";

	int insert(User user);

	@Select("SELECT * FROM " + TABLE_NAME + " WHERE tipIdx =  #{tipIdx}")
	@Override
	MealTip get(Integer id);

	@Update("UPDATE " + TABLE_NAME + " SET " + UPDATE_VALUES + " WHERE tipIdx =  #{tipIdx}")
	@Override
	int update(MealTip mealTip);

	@Delete("DELETE FROM " + TABLE_NAME + " WHERE tipIdx =  #{tipIdx}")
	@Override
	int delete(Integer id);

	@Select("SELECT * FROM " + TABLE_NAME)
	@Override
	List<MealTip> getList();

	@Select("SELECT * FROM " + TABLE_NAME + " WHERE language = #{language} order by rand() limit 1")
	MealTip getCountryMessage(MealTip mealTip);

}
