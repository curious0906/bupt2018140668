package com.chenchao.pay;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayOpenPublicTemplateMessageIndustryModifyRequest;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.chenchao.model.BookDetails;
import com.chenchao.model.OrderForm;
import com.alipay.api.response.AlipayOpenPublicTemplateMessageIndustryModifyResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Service
public class Alipay extends AbstractPay{

    private static final String APP_PRIVATE_KEY = "MIIEvwIBADANBgkqhkiG9w0BAQEFAASCBKkwggSlAgEAAoIBAQDX1aL/RiIfYVV3ntrlI76nummfXMp5uzzclxrdW/izOgSrXTx/FxNtf61Nh3tL24sojLdmll+htwig1h+n2MKtf5Uq9CfgT86ZPZcgSHhY1hFkwLAtBB9ytKNt+5+KfKEFJWk8PTrxErHDC4zVvnhy3AyC4Gh3DUV81aYa/2u9sN0yJvU/J16h2sInw6mFJM4jbMzLaEBxK2ljgrvMkRZKw0cHceYjyyo/iJdwIe3uKEm9OWNqd8Oc+ombUukJMSj/ZcxjUX/Pr6uj+A3/327tWiJHAATP7QQuDeOMkvVYI1VtCRfWVJ6BQh4gFLmIaYgq1jphXz4Nt9T/5OmiPMFRAgMBAAECggEBAJeJIiiyV/DKSNR0juFESG7Avbmqz2nKYP/Lg4wZL7K+MFsUWg4s6HH/q3B8h7+Jw9lTesynFTuYI5Gw5IbW9FAV/GgtBfZCch47FOkQmJZerdWcR9VDSMUjOH9Wo/v6Zp8TViB6KPDrHC2B5X6IfU6/WnVlE1NK2Re9dSMNY9rIkFGg65ZqrO+dsJ8DEL1Uq5NSyUMEXYPkDpWri/xQgSyo/bK+6yzpxBSHEUgNi5Pex9DfB2bEI+QjtXX+xya1Em6hyW/WCzApXb+uUYCDl3D3exo4SuUorCo+F6l/JtPkpksFn3TNufR3lh6TU/WxVvG3aK/F2oK3GVa438rXh9UCgYEA/rQ//YDH6YQu5RTU+AUa+rvF07V0qy/lOEdj8ELKjQ0U3ITjhYSY6mSb8zx6VOIEibk/Zz7SVDBH2IIj4i8F9cRDXIR9Yd9DUsIdTXiHEyelwWLgQMzwdtKeSyUHKmfx3wRhqZL4uEN2qRKFWc/9eBE8yC0GZI9IEQ20sh/JAPcCgYEA2O7CadRyrYvchZqezQEt7zNuCuKLpUbfo9yODwrr9FcW7VibwLwZiSeRfkeATF3wnzVqeZCoIUVBBe86F5AN35VPEXOv0dDzwXTNFjRGGrrsNZMBAlr48IeU/qOEg4A0nEsiQEb4AWbrD6B3MmeGahHDidUPotEZC2l9B/34BfcCgYEAkXH82JYhkoeY/9fVhYvkzClN3odQCDFhg7HAsAJ0B/fgYczA4A3IBJFPPKbcdHxcewj928dT9B9d55TpSM/xZ1ab4MkHkEwd9bRDZ2ncOGP7k2MTC+37ot7C5cZPQTf6NpAZ9OuofBCKQiIBZDgRVe8B9o0/iU0IqjRZax4beiECgYAaaT88OURZAr0vevq4hNs90yojox9TWQSjY5fcFv1RO0UsUmxsE7qcKzgYgsYJBirkh1FWSZleq5rWCSEuezhlLz14J3iRd/olZH4PpP+gA3/8qdAmpELIrgki02+vvu0o1jDuWHWbXhdiSKwac+Y2hcEjx8/mh8Sf0hEJ5w7KUwKBgQCUu2T9GSc2iYf+z1xd6qtMIt5mXr1t6r1eLLubio5lUHLk4t4ClrFXRHiGK0bc8C40xCkmXDKhRUOQ4bKQOqu4Dn0ccBwkJo/dr1H9+wWKz/8dB216WvzyMKZ7+geGt6xmeNbOTGO/mZlaDXmitHqJG6zi6M6hNxWj6PY7zs5fUw==";
    private static final String APP_ID = "2016091900547300";
    private static final String CHARSET = "utf-8";
    private static final String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAvGiVTDH3ehTZcanQXUbIkqI6QCs5UwiQSRvoUL+rtlVI8nyCRvvgvP9KhRqIFEFaN75ZuyWZfHYwmM/rze2dJWnDaDFq08g/wtCviDyrzt/HK1RS9l9ZhgsAcvVOhsD/aIISP3nlRzH1TSD+5gPu/QIOXNmTCqnhzk2JeM2LpAUeFs6FSfeV1KVgXzyImgs6eQQ2lld5Hw3Y87sTXdpPlHFDKi36b32za/ugA/EDrthL7zRO9FWfWLd11a3CSUswPgecD+rW/uSEY6TFEAFfc1/s9DIEBg3dmPF6KBJlxOg1vRp/rJuhYlmuUfA1yXIp95Wc+4tb8LV7UsDvghEPgwIDAQAB";
    @Autowired
    private AlipayConfig alipayConfig;

    @Override
    public void prepareAndPay(PayContext payContext) throws IOException, AlipayApiException {
//        AlipayClient alipayClient = new DefaultAlipayClient(
//                alipayConfig.getOpenApiDomain(),
//                alipayConfig.getAppId(),
//                alipayConfig.getMerchantPrivateKey(),
//                alipayConfig.getFormat(),
//                alipayConfig.getCharset(),
//                alipayConfig.getAlipayPublicKey(),
//                alipayConfig.getSignType()); //获得初始化的AlipayClient

        OrderForm order = payContext.getOrders();

        BookDetails book = payContext.getBookInfos().get(0);

        HttpServletResponse httpResponse = payContext.getResponse();

        //实例化客户端
        AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do", APP_ID, APP_PRIVATE_KEY, "json", CHARSET, ALIPAY_PUBLIC_KEY, "RSA2");
        AlipayOpenPublicTemplateMessageIndustryModifyRequest request = new AlipayOpenPublicTemplateMessageIndustryModifyRequest();
        request.setBizContent("  {" +
                "    \"primary_industry_name\":\"IT科技/IT软件与服务\"," +
                "    \"primary_industry_code\":\"10001/20102\"," +
                "    \"secondary_industry_code\":\"10001/20102\"," +
                "    \"secondary_industry_name\":\"IT科技/IT软件与服务\"" +
                " }");
        AlipayOpenPublicTemplateMessageIndustryModifyResponse response = alipayClient.execute(request);
//调用成功，则处理业务逻辑
        if(response.isSuccess()){
            System.out.println("11");
            //.....

        }

        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();//创建API对应的request
        alipayRequest.setReturnUrl(alipayConfig.getReturnUrl());
        alipayRequest.setNotifyUrl(alipayConfig.getNotifyUrl());//在公共参数中设置回跳和通知地址
        alipayRequest.setBizContent("{" +
                "    \"out_trade_no\":\""+order.getOrderFormId()+"\"," +
                "    \"product_code\":\"FAST_INSTANT_TRADE_PAY\"," +
                "    \"total_amount\":\""+order.getAmountOfRealPay()+"\"," +
                "    \"subject\":\""+book.getBookOutline()+"\"," +
                "    \"body\":\""+book.getBookOutline()+"\"," +
                "    \"passback_params\":\"merchantBizType%3d3C%26merchantBizNo%3d2016010101111\"," +
                "    \"extend_params\":{" +
                "    \"sys_service_provider_id\":\"2088511833207846\"" +
                "    }"+
                "  }");//填充业务参数
        String form="";
        try {

            form = alipayClient.pageExecute(alipayRequest).getBody(); //调用SDK生成表单

        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        httpResponse.setContentType("text/html;charset=" + alipayConfig.getCharset());
        httpResponse.getWriter().write(form);//直接将完整的表单html输出到页面
        httpResponse.getWriter().flush();
        httpResponse.getWriter().close();
    }

    @Override
    public void afterPay(PayContext payContext) {
    }
}
