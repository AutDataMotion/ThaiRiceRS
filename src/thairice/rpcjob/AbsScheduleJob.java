/**
 * ThaiRiceRS
 * create by zw at 2018年6月13日
 * version: v1.0
 */
package thairice.rpcjob;

import java.math.BigInteger;
import java.util.Map;
import java.util.Objects;

import com.alibaba.fastjson.JSON;
import com.jfinal.log.Logger;

import RPCRice.ClassifyA;
import RPCRice.ClassifyB;
import RPCRice.Drought;
import RPCRice.Growth;
import RPCRice.InfRicePrx;
import RPCRice.PreProcess;
import RPCRice.Yield;
import csuduc.platform.util.tuple.Tuple2;
import thairice.constant.EnumStatus;
import zeroc.util.IceClientUtil;

/**
 * @author zw
 *
 */
public abstract class AbsScheduleJob {
	private static Logger log = Logger.getLogger(AbsScheduleJob.class);

	public final static char splitChar = '|';
	public final static String serverAddr = "thairice:default -h 10.2.29.64 -p 8888";
	private volatile static RPCRice.InfRicePrx proxy;

	protected static BigInteger userId = new BigInteger("0");
	
	protected static String userName = "rpc job";
	
	protected static RPCRice.InfRicePrx getRpcProxy() {
		if (Objects.isNull(proxy)) {
			synchronized (serverAddr) {
				final RPCRice.InfRicePrx tmp = (InfRicePrx) IceClientUtil.getServicePrx(RPCRice.InfRicePrx.class,
						serverAddr);
				proxy = tmp;
			}
		}
		if (Objects.isNull(proxy)) {
			throw new IllegalArgumentException("getRpcProxy  proxy is null");
		}
		return proxy;
	}

	@FunctionalInterface
	private static interface FunRpc<A> {
		String doRpc(A a, Map<String, String> mapArgs);
	}

	public static <T> EnumStatus commRpc(FunRpc<T> function, T t, Map<String, String> mapArgs) {
		String resPreProcess = null;
		try {
			log.info(JSON.toJSONString(t));
			resPreProcess = function.doRpc(t, mapArgs);
			log.info(resPreProcess);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			log.error(e.getMessage());
			return EnumStatus.Failed;
		}
		return EnumStatus.Success;
	}

	// ===============预处理
	public static EnumStatus preProcessing(PreProcess argPreProcess, Map<String, String> mapArgs) {
		return commRpc((a, b) -> getRpcProxy().PreProcessing(a, b), argPreProcess, mapArgs);
	}

	// ==============干旱监测
	public static EnumStatus landDrought(Drought argDrought, Map<String, String> mapArgs) {
		return commRpc((a, b) -> getRpcProxy().landdrought(a, b), argDrought, mapArgs);
	}

	// ==============面积监测
	public static EnumStatus classifyA(ClassifyA argClassifyA, Map<String, String> mapArgs) {
		return commRpc((a, b) -> getRpcProxy().maxlikehood(a, b), argClassifyA, mapArgs);
	}

	public static EnumStatus classifyB(ClassifyB argClassifyB, Map<String, String> mapArgs) {
		return commRpc((a, b) -> getRpcProxy().split(a, b), argClassifyB, mapArgs);
	}

	// ==============长势监测
	public static EnumStatus growthMonitor(Growth argGrowth, Map<String, String> mapArgs) {
		return commRpc((a, b) -> getRpcProxy().ricegrowth(a, b), argGrowth, mapArgs);
	}

	// ==============水稻估产
	public static EnumStatus landYield(Yield argYield, Map<String, String> mapArgs) {
		return commRpc((a, b) -> getRpcProxy().landyield(a, b), argYield, mapArgs);
	}

	public static String sqlStr_DownLoadStatus(EnumDataStatus type) {
		return String.format(" status_ ='%s' ", type.getIdStr());
	}

	public static String sqlStr_ProcessStatus(EnumDataStatus dataType, EnumDataStatus statusType) {
		
		String dataTypeStr = "emptt";
		switch (dataType) {
		case PDT_TYPE_Drought:
			dataTypeStr = "drought_st";
			break;
		case PDT_TYPE_Yield:
			dataTypeStr = "estimate_st";
			break;
		case PDT_TYPE_Growth:
			dataTypeStr = "condition_st";
			break;
		default:
			break;
		}
		return String.format(" %s ='%s' ", dataTypeStr, statusType.getIdStr());
	}
	
	public static String addFilePathName(String path, String name){
		return path + name;
	}
}
