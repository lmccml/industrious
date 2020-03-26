package tools;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.alipay.api.response.AlipayTradeQueryResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

@Slf4j
@RestController
public class PayTools extends BaseController {
    @RequestMapping("/topay")
    public ModelAndView toPay(HttpServletRequest request, @RequestParam Map<String,String> params){
        ModelAndView mv = new JModelAndView("/weix/pay",request);
        String orderNo = params.get("order_no");
       /* Map<String,Object> orderInfo = payService.queryOrderInfo(orderNo);
        String status = StringUtil.filterNull(orderInfo.get("trans_status"));
        if("0".equals(status)) {
            payService.updateOrderPay(params);
        }*/
        mv.addAllObjects(params);
        return mv;
    }

    /**
     * 下单
     */
    @RequestMapping(value = "/payOrderByWx", produces = "application/json;charset=utf-8")
    @ResponseBody
    public CommonResult payOrderByWx(@RequestParam Map<String, String> params) {
        String order_no = params.get("order_no");
        String trans_channel = "";
        //Map<String, Object> orderMap = payService.queryOrderInfo(order_no);
        Map<String, Object> orderMap = new HashMap<>();
//        trans_channel = StringUtil.filterNull(params.get("trans_channel"));
//        if (orderMap == null || orderMap.isEmpty()) {
//            return CommonResult.build(403, "无此订单", null);
//        } else {
//            String orderMap_result_code = String.valueOf(orderMap.get("trans_status"));
//            if ("1".equals(orderMap_result_code)) {
//                return CommonResult.build(200, "该笔订单支付成功，请勿重复下单");
//            }
//            if ("2".equals(orderMap_result_code)) {
//                return CommonResult.build(200, "该笔订单支付失败，请勿重复下单");
//            }
//        }
        String spbill_create_ip = params.get("spbill_create_ip");
        if (StringUtils.isEmpty(spbill_create_ip)) {
            return CommonResult.build(403, "客户端ip不能为空");
        }
//        int res = payService.updateOrderPay(params);
//        if(res <= 0){
//            return CommonResult.build(403, "请返回应用查看订单状态！");
//        }
//        orderMap = payService.queryOrderInfo(order_no);
        //支付宝则走支付宝支付
        if ("zfb".equals(trans_channel)) {
            return alipayOrder(orderMap);
        }else if("wx".equals(trans_channel)){
            orderMap.put("spbill_create_ip",spbill_create_ip);
            return wxCreateOrder(orderMap);
        }else {
            return CommonResult.build(403,"支付失败",null);
        }
    }

    /**
     * 微信通道
     */
    public CommonResult wxCreateOrder(Map<String,Object> orderMap){
        //单位分
        String total_amount = orderMap.get("total_amount").toString();
        BigDecimal totalAmount = new BigDecimal(total_amount).multiply(new BigDecimal("100"));
        String  total_fee = totalAmount.intValue()+"";
        String order_no = StringUtil.filterNull(orderMap.get("order_no"));
        String user_code = StringUtil.filterNull(orderMap.get("user_code"));
        String spbill_create_ip = StringUtil.filterNull(orderMap.get("spbill_create_ip"));
        String trans_channel = StringUtil.filterNull(orderMap.get("trans_channel"));

        //获取配置参数
        String appid = WebUtil.getSysConfigValue("wx_pay_app_id");
        String mch_id = WebUtil.getSysConfigValue("wx_pay_mer_id");
        String sub_mch_id = WebUtil.getSysConfigValue("wx_pay_sub_mer_id");
        String nonce_str = Md5.md5Str(StringUtil.filterNull(Math.random()));
        String body = WebUtil.getSysConfigValue("pay_desc");
        String notify_url = WebUtil.getSysConfigValue("wx_pay_notify_url");
        String pay_sign_key = WebUtil.getSysConfigValue("wx_pay_sign_key");

        //组装报文
        SortedMap<String, String> sortedMap = new TreeMap();
        String subMode = WebUtil.getSysConfigValue("wx_pay_mode");

        sortedMap.put("appid", appid);
        sortedMap.put("mch_id", mch_id);
        if("1".equals(subMode)) {
            sortedMap.put("sub_mch_id", sub_mch_id);
        }
        sortedMap.put("device_info", user_code);
        sortedMap.put("nonce_str", nonce_str);
        sortedMap.put("body", body);
        sortedMap.put("out_trade_no", order_no);
        sortedMap.put("total_fee", total_fee);
        sortedMap.put("spbill_create_ip", spbill_create_ip);
        sortedMap.put("notify_url", notify_url);
        sortedMap.put("trade_type", "MWEB");
        sortedMap.put("sign", signStr(sortedMap, pay_sign_key));
        try {
            String xmlStr = XmlUtil.map2Xmlstring(sortedMap);
            String respMsg = HttpClientUtil.doPost("wxPayUrl", xmlStr, 60, "UTF-8", "text/xml");
            log.info("======微信下单返回数据===" + order_no + respMsg);
            Map<String, String> resMap = XmlUtil.parseXmlStrList(respMsg);
            if (!"SUCCESS".equalsIgnoreCase(resMap.get("return_code"))) {
                return CommonResult.build(403, "下单失败", null);
            }
            String mweb_url = resMap.get("mweb_url");
            Map<String, String> retMap = new HashMap<>();
            retMap.put("mweb_url", mweb_url);
            retMap.put("payMethod", trans_channel);
            return CommonResult.build(200, "微信下单成功", retMap);
        } catch (Exception e) {
            log.error("微信下单异常", e);
            return CommonResult.build(403, "微信下单异常", null);
        }
    }

    /**
     * 微信回调
     *
     * @return
     */
    @RequestMapping("/wxPayCallBack")
    @ResponseBody
    public void payCallBack(HttpServletRequest request, HttpServletResponse response) {
        log.info("---------微信支付回调方法---------");
        BufferedReader bis = null;
        Map<String, String> retMap = new HashMap<>();
        retMap.put("return_code", "SUCCESS");
        retMap.put("return_msg", "OK");
        try {
            bis = new BufferedReader(new InputStreamReader(request.getInputStream()));
            String line = null;
            String result = "";
            while ((line = bis.readLine()) != null) {
                result += line + "\r\n";
            }
            log.info("result=" + result);
            Map<String, String> resultMap = XmlUtil.parseXmlStrList(result);
            String return_code = resultMap.get("return_code");
            if ("SUCCESS".equals(return_code)) {
                String result_code = resultMap.get("result_code");
                String time_end = resultMap.get("time_end");//支付完成时间，格式为yyyyMMddHHmmss
                // 校验是否已经存在订单8
                // 修改订单相关信息
                //Map<String, Object> orderMap = payService.queryOrderInfo(out_trade_no);
                Map<String, Object> orderMap = new HashMap<>();
                if (orderMap == null || orderMap.isEmpty()) {
                    log.info("-----无此订单------");
                    //将Object转换成XML格式
                    String respContentXml = XmlUtil.map2Xmlstring(retMap);
                    respContentXml = respContentXml.replaceAll("__", "_");
                    System.out.println("respContentXml=" + respContentXml);
                    outXml(respContentXml, response);
                    return;
                } else {
                    String orderMap_result_code = String.valueOf(orderMap.get("trans_status"));
                    if ("0".equals(orderMap_result_code)||"2".equals(orderMap_result_code)) {
                        String trans_status = "0";
                        //待付款
                        String order_status = "1";
                        if ("FAIL".equalsIgnoreCase(result_code)) {
                            //2：交易失败
                            trans_status = "2";
                        } else {
                            //1：交易成功
                            trans_status = "1";
                            //待发货
                            order_status = "0";
                        }
                        int num = 1;
                        //int num = payService.updateOrderInfo(out_trade_notrans_status);
                        //更新失败
                        if (num <= 0) {
                            //如果处理订单失败，则返给微信商户处理失败
                            retMap.put("return_code", "FAIL");
                            //将Object转换成XML格式
                            String respContentXml = XmlUtil.map2Xmlstring(retMap);
                            respContentXml = respContentXml.replaceAll("__", "_");
                            System.out.println("respContentXml=" + respContentXml);
                            outXml(respContentXml, response);
                            return;
                        }
                        //将Object转换成XML格式
                        String respContentXml = XmlUtil.map2Xmlstring(retMap);
                        respContentXml = respContentXml.replaceAll("__", "_");
                        System.out.println("respContentXml=" + respContentXml);
                        outXml(respContentXml, response);
                        return;
                    }
                    if ("1".equals(orderMap_result_code)) {
                        log.info("-----订单已支付成功，请勿重复------");
                        //将Object转换成XML格式
                        String respContentXml = XmlUtil.map2Xmlstring(retMap);
                        respContentXml = respContentXml.replaceAll("__", "_");
                        System.out.println("respContentXml=" + respContentXml);
                        outXml(respContentXml, response);
                        return;
                    }
                   /* if ("2".equals(orderMap_result_code)) {
                        if("1".equals())
                        log.info("-----订单已交易失败，请勿重复------");
                        //将Object转换成XML格式
                        String respContentXml = XmlUtil.map2Xmlstring(retMap);
                        respContentXml = respContentXml.replaceAll("__", "_");
                        System.out.println("respContentXml=" + respContentXml);
                        outXml(respContentXml, response);
                        return;
                    }*/

                }
            } else {
                String return_msg = resultMap.get("return_msg");
                log.info("-----微信回调方法通信失败----msg=" + return_msg);
            }
            //将Object转换成XML格式
            String respContentXml = XmlUtil.map2Xmlstring(retMap);
            respContentXml = respContentXml.replaceAll("__", "_");
            System.out.println("respContentXml=" + respContentXml);
            outXml(respContentXml, response);
        } catch (Exception e) {
            log.error("", e);
            //将Object转换成XML格式
            String respContentXml = XmlUtil.map2Xmlstring(retMap);
            respContentXml = respContentXml.replaceAll("__", "_");
            System.out.println("respContentXml=" + respContentXml);
            outXml(respContentXml, response);
        } finally {
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    log.error("======微信回调方法异常==", e);
                }
            }
        }
    }

    /**
     * 查询订单状态
     *
     * @return
     */
    @RequestMapping(value = "/wxPayQueryOrder", produces = "application/json;charset=utf-8")
    @ResponseBody
    public CommonResult wxPayQueryOrder(@RequestParam Map<String, String> params) {
        log.info("---------查询订单状态---------");

        String order_no = params.get("order_no");
        if (StringUtils.isEmpty(order_no)) {
            return CommonResult.build(403, "订单号不能为空");
        }
        try {
            // 首先查询本地订单情况
            Map<String, Object> orderMap = new HashMap<>();
            //Map<String, Object> orderMap = payService.queryOrderInfo(order_no);
            if (orderMap == null || orderMap.isEmpty()) {
                return CommonResult.build(403, "订单不存在", null);
            } else {
                String orderMap_result_code = String.valueOf(orderMap.get("trans_status"));
                String need_num = String.valueOf(orderMap.get("num"));
                String one_user_code = String.valueOf(orderMap.get("one_user_code"));
                Map<String, Object> return_map = new HashMap<>();
                return_map.put("create_time", String.valueOf(orderMap.get("order_time")));
                //交易中状态需要去查结果
                if ("0".equals(orderMap_result_code)) {
                    String trans_channel = String.valueOf(orderMap.get("trans_channel"));

                    //微信查询
                    if ("wx".equals(trans_channel)) {
                        //获取配置参数
                        String appid = WebUtil.getSysConfigValue("wx_pay_app_id");
                        String mch_id = WebUtil.getSysConfigValue("wx_pay_mer_id");
                        String nonce_str = Md5.md5Str(StringUtil.filterNull(Math.random()));
                        String pay_sign_key = WebUtil.getSysConfigValue("wx_pay_sign_key");

                        //组装报文
                        SortedMap<String, String> sortedMap = new TreeMap();
                        sortedMap.put("appid", appid);
                        sortedMap.put("mch_id", mch_id);
                        sortedMap.put("nonce_str", nonce_str);
                        sortedMap.put("out_trade_no", order_no);
                        sortedMap.put("sign", signStr(sortedMap, pay_sign_key));
                        String xmlStr = XmlUtil.map2Xmlstring(sortedMap);
                        String respMsg = HttpClientUtil.doPost("wxPayQueryUrl", xmlStr, 60, "UTF-8", "text/xml");
                        log.info("======微信查询订单状态返回数据===" + order_no + respMsg);
                        Map<String, String> resMap = XmlUtil.parseXmlStrList(respMsg);
                        if (!"SUCCESS".equalsIgnoreCase(resMap.get("return_code")) && !"SUCCESS".equalsIgnoreCase(resMap.get("result_code"))) {
                            return_map.put("result", "待支付");
                            return_map.put("trans_status", "0");
                            return CommonResult.build(200,"查询成功！",return_map);
                        }
                        String trade_state = resMap.get("trade_state");
                        String trans_status = "0";
                        //待付款
                        String order_status = "1";
//                    SUCCESS—支付成功
//                    REFUND—转入退款
//                    NOTPAY—未支付
//                    CLOSED—已关闭
//                    REVOKED—已撤销（刷卡支付）
//                    USERPAYING--用户支付中
//                    PAYERROR--支付失败(其他原因，如银行返回失败)
                        if ("USERPAYING".equalsIgnoreCase(trade_state) || "NOTPAY".equalsIgnoreCase(trade_state)) {
                            return_map.put("result", "待支付");
                            return_map.put("trans_status", "0");
                        }
                        if ("PAYERROR".equalsIgnoreCase(trade_state) || "CLOSED".equalsIgnoreCase(trade_state)) {
                            trans_status = "2";
                            return_map.put("total_amount", String.valueOf(orderMap.get("total_amount")));
                            return_map.put("result", "支付失败");
                            return_map.put("trans_status", trans_status);

                        }
                        if ("SUCCESS".equalsIgnoreCase(trade_state)) {
                            trans_status = "1";
                            //待发货
                            order_status = "0";
                            return_map.put("total_amount", String.valueOf(orderMap.get("total_amount")));
                            return_map.put("result", "支付成功");
                            return_map.put("trans_status", trans_status);
                            orderMap_result_code = trans_status;
                        }
                        int num = 1;
                        //int num = payService.updateOrderInfo(order_no, trans_status);
                        if (num <= 0) {
                            //如果处理订单失败,客户端还是返回查询结果
                            log.info("-----查询结果时处理系统订单状态失败，请查看后台日志------" + order_no);
                            return CommonResult.build(200, "查询成功", return_map);
                        } else {
                            if (trans_status.equals("1")) {
                                //更新成功且为支付成功即可操作
                            }
                        }
                        return CommonResult.build(200, "查询成功", return_map);
                    }

                    //支付宝查询
                    if ("zfb".equals(trans_channel)) {
                        String appid = WebUtil.getSysConfigValue("ali_pay_app_id");
                        String out_trade_no = order_no;
                        String pay_sign_key = WebUtil.getSysConfigValue("ali_pay_sign_key");
                        String ali_pay_public_key = WebUtil.getSysConfigValue("alipay_query_key");
                        //发送请求
                        AlipayClient alipayClient = new DefaultAlipayClient("aliPayUrl", appid,pay_sign_key, "json","UTF-8", ali_pay_public_key, "RSA2");
                        AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();
                        JSONObject jsonObject_no = new JSONObject();
                        jsonObject_no.put("out_trade_no",out_trade_no);
                        request.setBizContent(jsonObject_no.toJSONString());
                        request.setApiVersion("1.0");
                        AlipayTradeQueryResponse response = alipayClient.execute(request);
                        if(!response.isSuccess()){
                            return_map.put("result", "待支付");
                            return_map.put("trans_status", "0");
                            return CommonResult.build(200,"查询成功",return_map);
                        }
                        String trade_state = response.getTradeStatus();
                        String trans_status = "0";
                        //订单状态待付款
                        String order_status = "1";
//                        WAIT_BUYER_PAY（交易创建，等待买家付款）
//                        TRADE_CLOSED（未付款交易超时关闭，或支付完成后全额退款）
//                        TRADE_SUCCESS（交易支付成功）
//                        TRADE_FINISHED（交易结束，不可退款）
                        if ("WAIT_BUYER_PAY".equalsIgnoreCase(trade_state)) {
                            return_map.put("result", "待付款");
                            return_map.put("trans_status", "0");
                        }
                        if ("TRADE_CLOSED".equalsIgnoreCase(trade_state)) {
                            trans_status = "2";
                            return_map.put("total_amount", String.valueOf(orderMap.get("total_amount")));
                            return_map.put("result", "支付失败");
                            return_map.put("trans_status", trans_status);
                        }
                        if ("TRADE_SUCCESS".equalsIgnoreCase(trade_state) || "TRADE_FINISHED".equalsIgnoreCase(trade_state)) {
                            trans_status = "1";
                            //待发货
                            order_status = "0";
                            return_map.put("total_amount", String.valueOf(orderMap.get("total_amount")));
                            return_map.put("result", "支付成功");
                            return_map.put("trans_status", trans_status);
                            orderMap_result_code = trans_status;

                        }
                        int num = 1;
                        //int num = payService.updateOrderInfo(order_no, trans_status);
                        if (num <= 0) {
                            //如果处理订单失败,客户端还是返回查询结果
                            log.info("-----查询结果时处理系统订单状态失败，请查看后台日志------" + order_no);
                            return CommonResult.build(200, "查询成功", return_map);
                        } else {
                            if (trans_status.equals("1")) {
                                //更新成功且为支付成功即可操作
                            }
                        }
                        return CommonResult.build(200, "查询成功", return_map);
                    }
                    return_map.put("result", "待支付");
                    return_map.put("trans_status", "0");
                    return CommonResult.build(200, "查询成功", return_map );
                }
                if ("1".equals(orderMap_result_code)) {
                    return_map.put("trans_status", "1");
                    return_map.put("result", "支付成功");
                    return_map.put("total_amount", String.valueOf(orderMap.get("total_amount")));
                    return CommonResult.build(200, "查询成功", return_map);
                }
                if ("2".equals(orderMap_result_code)) {
                    return_map.put("trans_status", "2");
                    return_map.put("result", "支付失败");
                    return_map.put("total_amount", String.valueOf(orderMap.get("total_amount")));
                    return CommonResult.build(200, "查询成功", return_map);
                }
            }
        } catch (Exception e) {
            log.error("查询支付结果异常", e);
            return CommonResult.build(403, "查询支付结果异常", null);
        }
        return CommonResult.build(200, "查询成功");
    }

    /**
     * 支付宝通道
     * @param orderInfo
     * @return
     */
    public CommonResult alipayOrder(Map<String,Object> orderInfo) {
        String appid = WebUtil.getSysConfigValue("ali_pay_app_id");
        String out_trade_no = StringUtil.filterNull(orderInfo.get("order_no"));
        String notify_url = WebUtil.getSysConfigValue("ali_pay_notify_url");
        String pay_sign_key = WebUtil.getSysConfigValue("ali_pay_sign_key");
        String pay_desc = WebUtil.getSysConfigValue("pay_desc");
        String ali_pay_public_key = WebUtil.getSysConfigValue("ali_pay_public_key");
        SortedMap<String, String> sortedMap = new TreeMap();

        //单位元
        String total_amount = orderInfo.get("total_amount").toString();
        sortedMap.put("out_trade_no", out_trade_no);
        sortedMap.put("subject", pay_desc);
        sortedMap.put("total_amount",total_amount);
        sortedMap.put("product_code", "QUICK_WAP_WAY");

        AlipayClient alipayClient = new DefaultAlipayClient("aliPayUrl", appid,pay_sign_key, "json","UTF-8", ali_pay_public_key, "RSA2");
        AlipayTradeWapPayRequest request = new AlipayTradeWapPayRequest();
        request.setApiVersion("1.0");
        request.setNotifyUrl(notify_url);
        request.setBizContent(JSONObject.toJSONString(sortedMap));
        String form = null; //调用SDK生成表单
        try {
            form = alipayClient.pageExecute(request).getBody();
          /*  orderDao.updateOrder(out_trade_no,"SUB","0");
            orderDao.updateSendOrderStatus(orderNo,out_trade_no,"SUB",null,form,"");*/
            log.info(form);
        } catch (Exception e) {
            log.error("====支付异常======",e);
            return CommonResult.build(403,"支付异常");
        }
        orderInfo.put("form",form);
        orderInfo.put("payMethod",orderInfo.get("trans_channel"));
        return CommonResult.ok(orderInfo);

    }

    @RequestMapping("/alipayCallBack")
    @ResponseBody
    public void alipayCallBack(@RequestParam Map<String,String> params, HttpServletResponse response) throws Exception {
        String out_trade_no = params.get("out_trade_no");
        String trade_status = params.get("trade_status");
        String trade_no = params.get("trade_no");
        String notify_time = params.get("notify_time");

        Map<String, Object> orderMap = new HashMap<>();
        //Map<String, Object> orderMap = payService.queryOrderInfo(out_trade_no);
        log.info("查询订单{}",orderMap);
        if (orderMap == null || orderMap.isEmpty()) {
            log.info("-----无此订单------"+out_trade_no);
            return;
        } else {
            String orderMap_result_code = String.valueOf(orderMap.get("trans_status"));
            String need_num = String.valueOf(orderMap.get("num"));
            String one_user_code = String.valueOf(orderMap.get("one_user_code"));
            if ("0".equals(orderMap_result_code)||"2".equals(orderMap_result_code)) {
                String trans_status = "0";
                //待付款
                String order_status = "1";
                if ("WAIT_BUYER_PAY".equals(trade_status)) {
                    outText("success",response);
                    log.info("支付宝返回的交易状态不正确（trade_status=" + trade_status + "）"+"----------订单号为"+out_trade_no);
                    return;
                }
                if ("TRADE_CLOSED".equalsIgnoreCase(trade_status)) {
                    //2：交易失败
                    trans_status = "2";
                } else {
                    //1：交易成功
                    trans_status = "1";
                    //待发货
                    order_status = "0";
                }
                int num = 1;
                //int num = payService.updateOrderInfo(out_trade_no, trans_status);
                //更新失败
                if (num <= 0) {
                    outText("success",response);
                    return;
                }
                //更新成功即可操作
                outText("success",response);
                return;
            }
            if ("1".equals(orderMap_result_code)) {
                log.info("-----订单已支付成功，请勿重复------");
                //将Object转换成XML格式
                outText("success",response);
                return;
            }
            if ("2".equals(orderMap_result_code)) {
                log.info("-----订单已交易失败，请勿重复------");
                outText("success",response);
                return;
            }
        }
    }
}
