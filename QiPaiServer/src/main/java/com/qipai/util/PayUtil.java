package com.qipai.util;

import java.net.URLEncoder;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.andy.util.FileUtil;
import com.andy.util.UtilDatetime;
import com.itrus.util.sign.RSAWithHardware;
import com.itrus.util.sign.RSAWithSoftware;
import com.jfinal.kit.PathKit;
import com.jfinal.kit.PropKit;
import com.qipai.common.game.comp.PayVO;

public class PayUtil {

	private static Logger logger = Logger.getLogger(PayUtil.class);
	static String RSA_S_PublicKey = null;
	static String RSA_S_PrivateKey = null;
	// 商家的pfx证书位置文件(公私钥合一)
	static String merPfxPath = null;
	static String pfxPass = PropKit.get("pfxPass");

	public static String[] signDinpay(PayVO payVO) throws Exception {
		
		String merchant_code = payVO.getMerchant_code();
		String service_type = payVO.getService_type();
		String interface_version = payVO.getInterface_version();
		String sign_type = payVO.getSign_type();
		String pay_type = payVO.getPay_type();
		String input_charset = payVO.getInput_charset();
		String notify_url = payVO.getNotify_url();
		String order_no = payVO.getOrder_no();
		String order_time = payVO.getOrder_time();
		String order_amount = payVO.getOrder_amount();
		String product_name = payVO.getProduct_name();
		String product_code = payVO.getProduct_code();
		String product_desc = payVO.getProduct_desc();
		String product_num = payVO.getProduct_num();
		String show_url = payVO.getShow_url();
		String client_ip = payVO.getClient_ip();
		String bank_code = payVO.getBank_code();
		String redo_flag = payVO.getRedo_flag();
		String extend_param = payVO.getExtend_param();
		String extra_return_param = payVO.getExtra_return_param();
		String return_url = payVO.getReturn_url();

		StringBuffer signStr = new StringBuffer();
		if (StringUtils.isNotBlank(extra_return_param)) {
			signStr.append("extra_return_param=").append(extra_return_param).append("&");
		}
//		signStr.append("input_charset=").append(input_charset).append("&");//app不能传
		signStr.append("interface_version=").append(interface_version).append("&");
		signStr.append("merchant_code=").append(merchant_code).append("&");
		signStr.append("notify_url=").append(notify_url).append("&");
		signStr.append("order_amount=").append(order_amount).append("&");
		signStr.append("order_no=").append(order_no).append("&");
		signStr.append("order_time=").append(order_time).append("&");
		if (StringUtils.isNotBlank(pay_type)) {
			signStr.append("pay_type=").append(pay_type).append("&");
		}
		signStr.append("product_name=").append(product_name).append("&");
		
		if (StringUtils.isNotBlank(redo_flag)) {
			signStr.append("redo_flag=").append(redo_flag).append("&");
		}
		
		if(StringUtils.isNotBlank(service_type)){
			signStr.append("service_type=").append(service_type).append("&");
		}
		
		String signInfo = signStr.substring(0,signStr.length()-1);
		String sign = "";
		if ("RSA-S".equals(sign_type)) {
			sign = RSAWithSoftware.signByPrivateKey(signInfo, getPrivateKey());
		} else {
			// 不可能走这个方式
			RSAWithHardware mh = new RSAWithHardware();
			// 商家签名（签名后报文发往dinpay）
			mh.initSigner(getMerPfxPath(), pfxPass);
			sign = mh.signByPriKey(signInfo);
		}
		
		return new String[]{signInfo,sign};
	}

	public static boolean checkDinpaySign(PayVO payVO) throws Exception {

		StringBuilder signStr = new StringBuilder();
		if (StringUtils.isNotBlank(payVO.getBank_seq_no())) {
			signStr.append("bank_seq_no=").append(payVO.getBank_seq_no()).append("&");
		}

		if (StringUtils.isNotBlank(payVO.getExtra_return_param())) {
			signStr.append("extra_return_param=").append(payVO.getExtra_return_param()).append("&");
		}

		signStr.append("interface_version=V3.0").append("&");
		signStr.append("merchant_code=").append(payVO.getMerchant_code()).append("&");

		if (StringUtils.isNotBlank(payVO.getNotify_id())) {
			signStr.append("notify_id=").append(payVO.getNotify_id()).append("&notify_type=").append(payVO.getNotify_type()).append("&");
		}

		signStr.append("order_amount=").append(payVO.getOrder_amount()).append("&");
		signStr.append("order_no=").append(payVO.getOrder_no()).append("&");
		signStr.append("order_time=").append(payVO.getOrder_time()).append("&");
		signStr.append("trade_no=").append(payVO.getTrade_no()).append("&");
		signStr.append("trade_status=").append(payVO.getTrade_status()).append("&");
		if (StringUtils.isNotBlank(payVO.getTrade_time())) {
			signStr.append("trade_time=").append(payVO.getTrade_time()).append("&");
		}
		

		boolean result = false;
		logger.error("充值回调参数："+signStr.substring(0,signStr.length()-1));
		logger.error("充值回调签名："+payVO.getDinpaySign());
		if ("RSA-S".equals(payVO.getSign_type())) {
			result = RSAWithSoftware.validateSignByPublicKey(signStr.substring(0,signStr.length()-1), getPublicKey(), payVO.getDinpaySign());
		}
		return result;
	}

	public static String genOrderNo(String extra_return_param) {
		String result = "BLMM"+UtilDatetime.getDatetimeString()+extra_return_param;
		return result;
	}
	
	public static String getMerPfxPath(){
		if(merPfxPath == null){
			merPfxPath = FileUtil.readString(PathKit.getRootClassPath() + "/rsa_private_pub_key_pkcs8.pfx");
		}
		return merPfxPath;
	}
	
	public static String getPublicKey(){
		if(RSA_S_PublicKey == null){
			RSA_S_PublicKey = FileUtil.readString(PathKit.getRootClassPath() + "/rsa_s_public_key.txt");
		}
		return RSA_S_PublicKey;
	}
	
	public static String getPrivateKey(){
		if(RSA_S_PrivateKey == null){
			RSA_S_PrivateKey = FileUtil.readString(PathKit.getRootClassPath() + "/rsa_s_private_key_pkcs8.txt");
		}
		return RSA_S_PrivateKey;
	}
	
	
}
