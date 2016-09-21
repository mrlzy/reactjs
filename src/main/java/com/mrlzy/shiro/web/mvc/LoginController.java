package com.mrlzy.shiro.web.mvc;

import com.mrlzy.shiro.plugin.dto.BaseDto;
import com.mrlzy.shiro.plugin.dto.Dto;
import com.mrlzy.shiro.service.OAuth2Service;
import com.mrlzy.shiro.service.WeiXinService;
import com.mrlzy.shiro.session.ShiroSessionUtils;
import com.mrlzy.shiro.weixin.WeiXinConfig;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.oltu.oauth2.as.issuer.MD5Generator;
import org.apache.oltu.oauth2.as.issuer.OAuthIssuer;
import org.apache.oltu.oauth2.as.issuer.OAuthIssuerImpl;
import org.apache.oltu.oauth2.as.request.OAuthAuthzRequest;
import org.apache.oltu.oauth2.as.request.OAuthTokenRequest;
import org.apache.oltu.oauth2.as.response.OAuthASResponse;
import org.apache.oltu.oauth2.common.OAuth;
import org.apache.oltu.oauth2.common.error.OAuthError;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.OAuthResponse;
import org.apache.oltu.oauth2.common.message.types.GrantType;
import org.apache.oltu.oauth2.common.message.types.ResponseType;
import org.apache.oltu.oauth2.common.utils.OAuthUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;


@Controller
public class LoginController {


    @RequestMapping("/login")
    public String login(HttpServletRequest request) {
        return "login_WITH_jsp";
    }

    @RequestMapping("/image")
    public String image(HttpServletRequest request) {
        return "image_WITH_jsp";
    }




    @Autowired
    private OAuth2Service oAuth2Service;

    @RequestMapping("/oauth2/authorize")
    public Object authorize(HttpServletRequest request) throws OAuthSystemException, URISyntaxException {
        OAuthAuthzRequest oauthRequest=null ;
        System.out.println("yoyo");
        try {

             oauthRequest = new OAuthAuthzRequest(request);
            if(!oAuth2Service.checkClient(oauthRequest)){
                OAuthResponse response =OAuthASResponse.errorResponse(HttpServletResponse.SC_BAD_REQUEST)
                                .setError(OAuthError.TokenResponse.INVALID_CLIENT)
                                .setErrorDescription("客户端验证失败,错误的client_id/client_secret")
                                .buildJSONMessage();
                return   new ResponseEntity(response.getBody(), HttpStatus.valueOf(response.getResponseStatus()));
            }

            if(!ShiroSessionUtils.login(request)){
                    return "login_WITH_jsp";
            }

            String username=(String)ShiroSessionUtils.getSubject().getPrincipal();

            String authorizationCode = null;
            String responseType = oauthRequest.getParam(OAuth.OAUTH_RESPONSE_TYPE);
            if (responseType.equals(ResponseType.CODE.toString())) {
                OAuthIssuerImpl oauthIssuerImpl = new OAuthIssuerImpl(new MD5Generator());
                authorizationCode = oauthIssuerImpl.authorizationCode();
                oAuth2Service.addAuthCode(username,authorizationCode);
            }
            //进行OAuth响应构建
            OAuthASResponse.OAuthAuthorizationResponseBuilder builder =
                    OAuthASResponse.authorizationResponse(request, HttpServletResponse.SC_FOUND);
            //设置授权码
            builder.setCode(authorizationCode);
            //得到到客户端重定向地址
            String redirectURI = oauthRequest.getParam(OAuth.OAUTH_REDIRECT_URI);
            //构建响应
             OAuthResponse response = builder.location(redirectURI).buildQueryMessage();
            //根据OAuthResponse返回ResponseEntity响应
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(new URI(response.getLocationUri()));
            return new ResponseEntity(headers, HttpStatus.valueOf(response.getResponseStatus()));

        } catch (OAuthProblemException e) {
            String redirectUri = e.getRedirectUri();
            if (OAuthUtils.isEmpty(redirectUri)) {
                return new ResponseEntity("OAuth callback url needs to be provided by client!!!", HttpStatus.NOT_FOUND);
            }
             OAuthResponse response =
                    OAuthASResponse.errorResponse(HttpServletResponse.SC_FOUND)
                            .error(e).location(redirectUri).buildQueryMessage();
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(new URI(response.getLocationUri()));
            return new ResponseEntity(headers, HttpStatus.valueOf(response.getResponseStatus()));
        }
    }


    @RequestMapping("/oauth2/access_token")
    public HttpEntity token(HttpServletRequest request)
            throws URISyntaxException, OAuthSystemException {

        try {
            //构建OAuth请求
            OAuthTokenRequest oauthRequest = new OAuthTokenRequest(request);

            if(!oAuth2Service.checkClient(oauthRequest)){
                OAuthResponse response =OAuthASResponse.errorResponse(HttpServletResponse.SC_BAD_REQUEST)
                        .setError(OAuthError.TokenResponse.INVALID_CLIENT)
                        .setErrorDescription("客户端验证失败,错误的client_id/client_secret")
                        .buildJSONMessage();
                return   new ResponseEntity(response.getBody(), HttpStatus.valueOf(response.getResponseStatus()));
            }
            String username= (String)ShiroSessionUtils.getSubject().getPrincipal();

            String authCode = oauthRequest.getParam(OAuth.OAUTH_CODE);
            // 检查验证类型，此处只检查AUTHORIZATION_CODE类型，其他的还有PASSWORD或REFRESH_TOKEN
            if (oauthRequest.getParam(OAuth.OAUTH_GRANT_TYPE).equals(GrantType.AUTHORIZATION_CODE.toString())) {
                if (!oAuth2Service.checkAuthCode(username,authCode)) {
                    OAuthResponse response = OAuthASResponse
                            .errorResponse(HttpServletResponse.SC_BAD_REQUEST)
                            .setError(OAuthError.TokenResponse.INVALID_GRANT)
                            .setErrorDescription("错误的授权码")
                            .buildJSONMessage();
                    return new ResponseEntity(response.getBody(), HttpStatus.valueOf(response.getResponseStatus()));
                }
            }

            //生成Access Token
            OAuthIssuer oauthIssuerImpl = new OAuthIssuerImpl(new MD5Generator());
            final String accessToken = oauthIssuerImpl.accessToken();
            oAuth2Service.addAccessToken(username,accessToken);


            //生成OAuth响应
            OAuthResponse response = OAuthASResponse
                    .tokenResponse(HttpServletResponse.SC_OK)
                    .setAccessToken(accessToken)
                    .setExpiresIn(String.valueOf(7200))
                    .buildJSONMessage();

            //根据OAuthResponse生成ResponseEntity
            return new ResponseEntity(response.getBody(), HttpStatus.valueOf(response.getResponseStatus()));

        } catch (OAuthProblemException e) {
            //构建错误响应
            OAuthResponse res = OAuthASResponse.errorResponse(HttpServletResponse.SC_BAD_REQUEST).error(e)
                    .buildJSONMessage();
            return new ResponseEntity(res.getBody(), HttpStatus.valueOf(res.getResponseStatus()));
        }
    }



    @Autowired
    private WeiXinService weiXinService;

    @RequestMapping("/weixin/selectCode")
    @ResponseBody
    public String selectCode(@RequestParam("username") String username){
        Dto dto=new BaseDto(true,"");
        try {
            dto.setMsg(weiXinService.sendUserValiCode(username));
        }catch (Exception e){
            dto.setSuccess(false);
            dto.setMsg(e.getMessage());
        }
        //log.info(dto.toJson());
        return dto.toJson();
    }
    @RequestMapping("/weixin/login")
    public String toWeiXin(){
        return "weixin/login_WITH_jsp";
    }

    @RequestMapping(value = {"/weixin/author"},method = RequestMethod.GET)
    @ResponseBody
    public String  author(@RequestParam("signature") String signature, @RequestParam("timestamp") String timestamp,
                          @RequestParam("nonce") String nonce, @RequestParam("echostr") String echostr){

        String[] values = { WeiXinConfig.WEIXIN_TOKEN, timestamp, nonce };
        Arrays.sort(values);
        String sum=values[0]+values[1]+values[2];
        String sign = DigestUtils.sha1Hex(sum);
        if(sign.equalsIgnoreCase(signature)){
            return  echostr;
        }
        return "error";

    }

}
