package thairice.mvc.t14my_region;

import java.math.BigInteger;

import org.apache.log4j.Logger;

import com.jfinal.aop.Enhancer;

import com.platform.mvc.base.BaseService;

public class t14my_regionService extends BaseService {

	@SuppressWarnings("unused")
	private static Logger log = Logger.getLogger(t14my_regionService.class);
	
	public static final t14my_regionService service = Enhancer.enhance(t14my_regionService.class);
	
	public t14my_region SelectById(Integer id){
		
		t14my_region mdl = t14my_region.dao.findFirst("select * from t14my_region where id=?", id);
		return mdl;
	}
	public t14my_region SelectById(BigInteger userId){
		
		t14my_region mdl = t14my_region.dao.findFirst("select * from t14my_region where userId=?", userId);
		return mdl;
	}
	public t14my_region SelectByCodes(BigInteger userId,Integer provinceId,Integer cityId,Integer areaId){
		
		t14my_region mdl = t14my_region.dao.findFirst("select * from t14my_region where userId=? and provinceId=? and cityId=? and areaId=?", userId,provinceId,cityId,areaId);
		return mdl;
	}
}
