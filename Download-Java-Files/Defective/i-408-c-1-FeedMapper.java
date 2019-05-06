package net.octacomm.sample.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import net.octacomm.sample.dao.CRUDMapper;
import net.octacomm.sample.domain.DefaultParam;
import net.octacomm.sample.domain.Feed;

public interface FeedMapper extends CRUDMapper<Feed, DefaultParam, Integer>{

	public String INSERT_FIELDS = " ( id , animalType , tag, name , manufacturer, age, calorie, calculationCalories,  crudeProtein, crudeFat, carbohydrate, crudeAsh, crudeFiber, taurine, moisture, calcium, phosphorus, omega3, omega6, mainIngredient )";

	public String INSERT_VALUES = " ( null , #{animalType} , #{tag}, #{name} , #{manufacturer}, #{age}, #{calorie}, #{calculationCalories},  #{crudeProtein}, #{crudeFat}, #{carbohydrate}, #{crudeAsh}, #{crudeFiber}, #{taurine}, #{moisture}, #{calcium}, #{phosphorus}, #{omega3}, #{omega6} , #{mainIngredient} )";

	public String TABLE_NAME = " feed ";

	public String UPDATE_VALUES = " animalType = #{animalType} , tag = #{tag} , name = #{name} , manufacturer = #{manufacturer} , age = #{age} , calorie = #{calorie} , calculationCalories = #{calculationCalories} , crudeProtein = #{crudeProtein} , crudeFat = #{crudeFat} , carbohydrate = #{carbohydrate} , crudeAsh = #{crudeAsh} , crudeFiber = #{crudeFiber} , taurine = #{taurine} , moisture = #{moisture} , calcium = #{calcium} , phosphorus = #{phosphorus} , omega3 = #{omega3} , omega6 = #{omega6} , mainIngredient = #{mainIngredient} ";

	public String SELECT_FIELDS = " id , animalType , tag, name , manufacturer, age, calorie, calculationCalories,  crudeProtein, crudeFat, carbohydrate, crudeAsh, crudeFiber, taurine, moisture, calcium, phosphorus, omega3, omega6, mainIngredient ";

	@Insert("INSERT INTO " + TABLE_NAME + INSERT_FIELDS + " VALUES " + INSERT_VALUES)
	@Override
	public int insert(Feed feed);

	@Delete("DELETE FROM " + TABLE_NAME + " WHERE id =  #{id}")
	@Override
	public int delete(Integer id);

	@Update("UPDATE " + TABLE_NAME + " SET " + UPDATE_VALUES + " WHERE id =  #{id}")
	@Override
	public int update(Feed feed);

	@Select("SELECT * FROM " + TABLE_NAME + " WHERE id =  #{id}")
	@Override
	public Feed get(Integer id);

	@Select("SELECT * FROM " + TABLE_NAME)
	@Override
	List<Feed> getList();
	
	@Select("SELECT * FROM " + TABLE_NAME +  " WHERE NAME LIKE CONCAT('%', #{text}, '%') and language = #{language}")
	public List<Feed> getSearchList(@Param("text") String searchWord, @Param("language") String language);
	
	@Select("SELECT * FROM " + TABLE_NAME +  " WHERE NAME LIKE CONCAT('%', #{text}, '%')")
	public List<Feed> getFeedInfo(int id);
	
	/*value * (mear_history.calorie * 0.01)*/
	@Select("SELECT char_length(feed.crudeProtein) as id, sum(feed.crudeProtein) as crudeProtein, sum(feed.crudeFat) as crudeFat, sum(feed.crudeFiber) as crudeFiber, sum(feed.crudeAsh) as crudeAsh, sum(feed.carbohydrate) as carbohydrate FROM  mear_history  join feed on mear_history.feedIdx = feed.id WHERE petIdx =  #{petIdx} and substring(mear_history.createDate, 1,10) = #{date} and mear_history.isDel = 1")
	public Feed getRadarChartData(@Param("date") String date, @Param("petIdx") int petIdx);
	
}
