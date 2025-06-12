package com.jijy.music.configuration.security.service;

import com.jijy.music.configuration.security.oauth.OAuth2UserInfo;
import com.jijy.music.configuration.security.oauth.OAuth2UserInfoFactory;
import com.jijy.music.configuration.security.oauth.exceptions.OAuth2AuthenticationProcessingException;
import com.jijy.music.persistence.model.Genres;
import com.jijy.music.persistence.repository.GenresRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomOAuth2UserService extends DefaultOAuth2UserService {


    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        System.out.println("fer 2");
        OAuth2User oAuth2User = super.loadUser(userRequest);
        log.debug("User: {}", oAuth2User);
        log.info("esta en la infor del usuario");
        System.out.println("ghvjbliokñp´{jlfcbhnjkm,ñ.");

        try {
            return processOauth2User(userRequest, oAuth2User);
        } catch (Exception ex) {
            throw new InternalAuthenticationServiceException(ex.getMessage(), ex.getCause());
        }
    }

    private OAuth2User processOauth2User(OAuth2UserRequest oAuth2UserRequest, OAuth2User oAuth2User) {
        log.info("esta en la infor del usuario");
        System.out.println("gchvjbknlm,ñ.");
        OAuth2UserInfo oAuth2UserInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(
                oAuth2UserRequest.getClientRegistration().getRegistrationId(),
                oAuth2User.getAttributes()
        );

        if (StringUtils.isEmpty(oAuth2UserInfo.getAttributes().get("email"))) {
            throw new OAuth2AuthenticationProcessingException("email not found oauth2 from oauth 2 provider");
        }

        System.out.println(oAuth2UserInfo);


        return oAuth2User;

    }
}
