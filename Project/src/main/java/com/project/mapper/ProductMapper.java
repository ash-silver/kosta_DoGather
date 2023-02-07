package com.project.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.project.model.Discount;
import com.project.model.Img;
import com.project.model.Option;
import com.project.model.Order;
import com.project.model.Product;

@Mapper
public interface ProductMapper {

	void AddProduct(Product pro);

	void AddOption(Option opt);

	void AddImg(Img img);

	void AddDiscount(Discount discount);

	void UpdateDiscount(Discount discount);

	void UpdateImg(Img img);

	void UpdateProduct(Product pro);

	List<Img> img_length(Img i);

	List<Discount> Update_find(int p_id);

	Product FindProduct(int p_id);
	Product FindCalender(int p_id);
	
	Map<String,Object> All_SellCount(String p_nickname_m_fk);
	List<Integer> All_SellPrice(String p_nickname_m_fk);
	List<Integer> All_Sell(String p_nickname_m_fk);
	Option FindOption(int opt_pid_p_fk);
	
	List<Option> Option_List(int p_id);
	List<String> FindOption2(Map<String, Object> map);
	
	void CreateNewEvent(String value);

	void removeProduct(int p_id);
	
	void OptionRemove(Option opt);
	void OneOptionRemove(int opt_id);
	
	List<Product> WriterProductlist(Map<String, Object> map);

	int WriterProductlistCount(@Param("p_nickname_m_fk")String p_nickname_m_fk,@Param("keyword") String keyword);

	List<Product> SearchSeller(Map<String, Object> map);
	List<Order> BuyProduct(Map<String, Object> map);

	int SearchSellerCount(@Param("p_nickname_m_fk") String p_nickname_m_fk, @Param("search") String search,@Param("keyword") String keyword);
	int BuyProductCount(String p_nickname_m_fk);
}