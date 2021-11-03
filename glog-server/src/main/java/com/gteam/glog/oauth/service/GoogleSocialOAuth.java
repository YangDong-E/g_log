package com.gteam.glog.oauth.service;

import com.gteam.glog.domain.enums.SocialLoginType;
import com.gteam.glog.domain.dto.oauth.GoogleOAuthRequestDTO;
import com.gteam.glog.domain.dto.oauth.GoogleOAuthResponseDTO;
import com.gteam.glog.domain.entity.OAuthInfo;
import com.gteam.glog.oauth.repository.OAuthRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;




@Service
@Log4j2
public class GoogleSocialOAuth implements SocialOAuth {
    @Value("oauth.redirectionRootUrl")
    private String redirectionRootUrl;
    private  final OAuthRepository oAuthRepository;
    private  final RestTemplate restTemplate = new RestTemplate();
    @Autowired
    public GoogleSocialOAuth(OAuthRepository oAuthRepository) {
        this.oAuthRepository = oAuthRepository;
    }

    /**
     * google Authorization 해더 반환
     *
     * @param token
     * @return Authorization 해더
     */
    @Override
    public HttpEntity createHeaders(String token) {
        String authHeader = "Bearer " + token;
        return new HttpEntity(new HttpHeaders(){{
            set("Authorization", authHeader);
        }});
    }


    /**
     * Google OAuth Request Access Token
     * 사용자 Access Code를 사용하여 Access Token 발급
     *
     * @param code
     * @return
     */
    @Override
    public String requestAccessToken(String code) {
        try {
            OAuthInfo oAuthInfo = oAuthRepository.FindUserOAuthCode(SocialLoginType.GOOGLE).get();
            GoogleOAuthRequestDTO googleOAuthRequestDTO = new GoogleOAuthRequestDTO(
                    oAuthInfo.getClientId(),
                    oAuthInfo.getClientSecret(),
                    code,
                    "authoriztion_code",
                    redirectionRootUrl+"/google/callback"
            );


            // send data id, secret key , access code , grant type, redirect url
            GoogleOAuthResponseDTO token = restTemplate.postForObject(
                    oAuthInfo.getUrl(),
                    googleOAuthRequestDTO,
                    GoogleOAuthResponseDTO.class);
            if (token != null) {
                return token.getAccess_token();
            } else {
                return null;
            }
        } catch (IllegalArgumentException e){
            log.error("Null Exception : "+ e.getMessage());
            return null;
        } catch ( HttpStatusCodeException e){
            log.error("Http Status Exception : " + e.getMessage());
            return null;
        }
    }
}