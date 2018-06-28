package com.clw.phaapp.model.illness;

/**
 * 易源数据，取得疾病明细返回结果实体类
 *
 * @author chenliwu
 * @create 2018-03-13 23:32
 **/
public class YiYuanIllnessDetailResultEntity {

        //请求参数/ Request parameter
//    1、系统级参数（所有接入点都需要的参数）：收起
//    参数名称	             类型	    示例值	                            必须	                       描述
//    showapi_appid	        String	     100	                            是	                     易源应用id
//    showapi_sign	        String	    698d51a19d8a121ce581499d7b701668	是	            为了验证用户身份，以及确保参数不被中间人篡改，需要传递调用者的数字签名。
//    showapi_timestamp	    String	     20141114142239	否	客户端时间。
//
//     格式yyyyMMddHHmmss,如20141114142239
//    为了在一定程度上防止“重放攻击”，平台只接受在10分钟之内的请求。如果不传或传空串，则系统不再做此字段的检测。
//    showapi_sign_method	String	md5	否	签名生成方式，其值可选为"md5"或"hmac"。如果不传入则默认"md5"。
//    showapi_res_gzip	String	1或0	否	返回值是否用gzip方式压缩。此值为1时将压缩，其他值不压缩。


//    2、应用级参数（每个接入点有自己的参数）：
//    参数名称	类型	默认值	               示例值	             必须	              描述
//    id	     String            55bf04cdc5479fa8da1ea2d2       是      疾病id，此id从【关键字查询疾病】中获取
//


    /**
     * 0为成功，其他失败
     */
    private String ret_code;

    /**
     * 疾病数据结构
     */
    private String item;

}
