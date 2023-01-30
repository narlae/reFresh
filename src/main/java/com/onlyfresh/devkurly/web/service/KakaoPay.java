package com.onlyfresh.devkurly.web.service;

import com.onlyfresh.devkurly.domain.order.Orders;
import com.onlyfresh.devkurly.web.dto.order.KakaoPayApprovalVO;
import com.onlyfresh.devkurly.web.dto.order.KakaoPayReadyVO;
import com.onlyfresh.devkurly.web.dto.order.OrderFormDto;
import lombok.extern.java.Log;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;

@Service
@Log
public class KakaoPay {

    private static final String HOST = "https://kapi.kakao.com";
    private KakaoPayReadyVO kakaoPayReadyVO;
    private KakaoPayApprovalVO kakaoPayApprovalVO;

    public String kakaoPayReady(Orders order) {

        RestTemplate restTemplate = new RestTemplate();

        // 서버로 요청할 Header
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "KakaoAK " + "e086d2e854b6d780b5740387160e74f3");
        headers.add("Accept", MediaType.APPLICATION_JSON_UTF8_VALUE);
        headers.add("Content-Type", MediaType.APPLICATION_FORM_URLENCODED_VALUE + ";charset=UTF-8");

        // 서버로 요청할 Body
        MultiValueMap<String, Object> params = new LinkedMultiValueMap<String, Object>();
        params.add("cid", "TC0ONETIME");
        params.add("partner_order_id", order.getOrderId()); // 이부분 해야한다.
        params.add("partner_user_id", order.getMember().getUserId());
        params.add("item_name", order.getItem_name());
        params.add("quantity", order.getQuantity());
        params.add("total_amount", order.getTotal_amount());
        params.add("tax_free_amount", order.getTotal_amount());
        params.add("approval_url", "http://localhost:8080/order/kakaoPaySuccess");
        params.add("cancel_url", "http://localhost:8080/order/kakaoPayCancel");
        params.add("fail_url", "http://localhost:8080/order/kakaoPaySuccessFail");

        HttpEntity<MultiValueMap<String, Object>> body = new HttpEntity<MultiValueMap<String, Object>>(params, headers);

        try {
            kakaoPayReadyVO = restTemplate.postForObject(new URI(HOST + "/v1/payment/ready"), body, KakaoPayReadyVO.class);

            log.info("" + kakaoPayReadyVO);

            if (kakaoPayReadyVO != null) {
                return kakaoPayReadyVO.getNext_redirect_pc_url();
            }

        } catch (RestClientException | URISyntaxException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return "/cart";

    }

    public KakaoPayApprovalVO kakaoPayInfo(String pg_token, OrderFormDto orderFormDto, Long userId) {

        log.info("KakaoPayInfoVO............................................");
        log.info("-----------------------------");

        RestTemplate restTemplate = new RestTemplate();

        // 서버로 요청할 Header
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "KakaoAK " + "e086d2e854b6d780b5740387160e74f3");
        headers.add("Accept", MediaType.APPLICATION_JSON_UTF8_VALUE);
        headers.add("Content-Type", MediaType.APPLICATION_FORM_URLENCODED_VALUE + ";charset=UTF-8");

        // 서버로 요청할 Body
        MultiValueMap<String, Object> params = new LinkedMultiValueMap<String, Object>();
        params.add("cid", "TC0ONETIME");
        params.add("tid", kakaoPayReadyVO.getTid());
        params.add("partner_order_id", orderFormDto.getOrderId());
        params.add("partner_user_id", userId);
        params.add("pg_token", pg_token);
        params.add("total_amount", orderFormDto.getTotal_amount());

        HttpEntity<MultiValueMap<String, Object>> body = new HttpEntity<MultiValueMap<String, Object>>(params, headers);

        try {
            kakaoPayApprovalVO = restTemplate.postForObject(new URI(HOST + "/v1/payment/approve"), body, KakaoPayApprovalVO.class);
            log.info("" + kakaoPayApprovalVO);

            return kakaoPayApprovalVO;

        } catch (RestClientException | URISyntaxException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return null;
    }

}