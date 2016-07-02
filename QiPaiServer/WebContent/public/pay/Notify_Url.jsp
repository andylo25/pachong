<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.itrus.util.sign.*" %>
<%


 	//公钥   dinpay public key
	String RSA_S_PublicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCWOq5aHSTvdxGPDKZWSl6wrPpn"+
	"MHW+8lOgVU71jB2vFGuA6dwa/RpJKnz9zmoGryZlgUmfHANnN0uztkgwb+5mpgme"+
	"gBbNLuGqqHBpQHo2EsiAhgvgO3VRmWC8DARpzNxknsJTBhkUvZdy4GyrjnUrvsAR"+
	"g4VrFzKDWL0Yu3gunQIDAQAB";

	// 接收智付返回通知数据（To receive notification data from Dinpay）  

	String merchant_code = request.getParameter("merchant_code");

	String notify_type = request.getParameter("notify_type");

	String notify_id = request.getParameter("notify_id");

	String interface_version = request.getParameter("interface_version");

	String sign_type = request.getParameter("sign_type");

	String dinpaySign = request.getParameter("sign");

	String order_no = request.getParameter("order_no");

	String order_time = request.getParameter("order_time");

	String order_amount = request.getParameter("order_amount");

	String trade_no = request.getParameter("trade_no");

	String trade_time = request.getParameter("trade_time");

	String trade_status = request.getParameter("trade_status");

	String bank_seq_no = request.getParameter("bank_seq_no");

	String extra_return_param = request.getParameter("extra_return_param");


/**	数据签名
签名规则定义如下：
（1）参数列表中，除去sign_type、sign两个参数外，其它所有非空的参数都要参与签名，值为空的参数不用参与签名；
（2）签名顺序按照参数名a到z的顺序排序，若遇到相同首字母，则看第二个字母，以此类推，组成规则如下：
参数名1=参数值1&参数名2=参数值2&……&参数名n=参数值n
*/

/**	Data signature
The definition of signature rule is as follows : 
（1） In the list of parameters, except the two parameters of sign_type and sign, all the other parameters that are not in blank shall be signed, the parameter with value as blank doesn’t need to be signed; 
（2） The sequence of signature shall be in the sequence of parameter name from a to z, in case of same first letter,  so on so forth, meanwhile, the merchant payment key shall be put at last for signature, the composition rule is as follows : 
Parameter name 1 = parameter value 1& parameter name 2 = parameter value 2& ......& parameter name N = parameter value N 
*/

	StringBuilder signStr = new StringBuilder();

	if(null != bank_seq_no && !bank_seq_no.equals("")) {
	signStr.append("bank_seq_no=").append(bank_seq_no).append("&");
	}

	if(null != extra_return_param && !extra_return_param.equals("")) {
	signStr.append("extra_return_param=").append(extra_return_param).append("&");
	}

	signStr.append("interface_version=V3.0").append("&");

	signStr.append("merchant_code=").append(merchant_code).append("&");

	if(null != notify_id && !notify_id.equals("")) {
	signStr.append("notify_id=").append(notify_id).append("&notify_type=").append(notify_type).append("&");
	}

	signStr.append("order_amount=").append(order_amount).append("&");

	signStr.append("order_no=").append(order_no).append("&");

	signStr.append("order_time=").append(order_time).append("&");

	signStr.append("trade_no=").append(trade_no).append("&");

	signStr.append("trade_status=").append(trade_status).append("&");

	if(null != trade_time && !trade_time.equals("")) {
	signStr.append("trade_time=").append(trade_time).append("&");
	}
	
	String signInfo =signStr.toString();
	boolean result;
	if("RSA-S".equals(sign_type)) {			
		//验签  signInfo签名字符串，RSA_S_PublicKey智付公钥，dinpaySign智付返回的签名数据
		result=RSAWithSoftware.validateSignByPublicKey(signInfo, RSA_S_PublicKey, dinpaySign);
	}else{
		String merPfxPath = "D:/1111110166.pfx";// 商家的pfx证书位置文件(公私钥合一)
		String pfxPass = "87654321";
		RSAWithHardware mh = new RSAWithHardware();
		//初始化
		mh.initSigner(merPfxPath, pfxPass);
		//验签   merchantId为商户号，signInfo签名字符串，dinpaySign智付返回的签名数据
		result = mh.validateSignByPubKey(merchant_code, signInfo, dinpaySign);
	}
	

////////////////////////////////////异步通知必须响应“SUCCESS”///////////////////////////////
/**
When the notification method is service asynchronous notification, after receiving backstage notification and complete processing, the merchant system must printout the following seven characters "SUCCESS "which can't be change,including the space between the characters, otherwise, the Dinpay payment system will, during the subsequent period, send such notification for 5 times with increasing time interval.
*/

PrintWriter pw = response.getWriter();

if(result==true) {  //验签成功（Signature correct）
	pw.print("SUCCESS");  
/**
1.响应 SUCCESS
1.response to SUCCESS

2.判断商家号、商家订单号、订单金额、交易状态
2.To check parameter value including merchant_code、order_no、order_amount and trade_status 

3.校验是否重复通知
3.To check whether it's repeated notice.
*/

}else{	//验签失败，业务结束（End of the business）
	pw.print("Signature Error");
}
%>