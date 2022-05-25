package com.ldh.edu.wangguan.config;

import com.alibaba.fastjson.JSONObject;
import com.sun.jndi.toolkit.url.Uri;
import org.bouncycastle.asn1.ocsp.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpCookie;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class LoginCheckFilter implements GlobalFilter , Ordered {
    @Autowired
    @Qualifier("stringRedisTemplate")
    RedisTemplate<String,String> redisTemplate;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        MultiValueMap<String, String> queryParams = request.getQueryParams();
        String needLogin = queryParams.getFirst("needLogin");
        if(needLogin!=null&& "false".equals(needLogin)){
            return chain.filter(exchange);
        }
        URI uri = request.getURI();
        if(!needLogin(uri)){
            System.out.println("不需要登录");
            return chain.filter(exchange);
        }
        MultiValueMap<String, HttpCookie> cookies = request.getCookies();
        HttpCookie cookie = cookies.getFirst("token");
        String str="";
        if(cookie!=null)
            str = cookie.getValue();
        if(str!=null && str.length()!=0){
            Object obj = redisTemplate.opsForValue().get(str);
            if(obj!=null){
                System.out.println("已经登录");
                return chain.filter(exchange);
            }
        }
        return withoutLogin(exchange);
    }

    public static Mono<Void> withoutLogin(ServerWebExchange exchange){
        Map<String,Object> map = Status.builder()
                                    .addDataValue("loginFalse")
                                    .addExtra("您还未登陆，应转到登陆或者注册页面")
                                    .addStatus(401)
                                    .addMessage("请重新登陆授权")
                                    .map();
        byte[] bytes = JSONObject.toJSONBytes(map);
        ServerHttpResponse response = exchange.getResponse();
        response.getHeaders().add("Content-Type", MediaType.APPLICATION_JSON_VALUE);
        DataBuffer wrap = response.bufferFactory().wrap(bytes);
        return response.writeWith(Flux.just(wrap));

    }

    public static boolean needLogin(URI uri){
        String path = uri.getPath();
        if(path.contains("login")||path.contains("register")){
            return false;
        }
        List<String> paths = new ArrayList<>();
        //添加不拦截的路径：
        for(String str : paths){
            if(str.equals(path)){
                return false;
            }
        }
        return true;
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
