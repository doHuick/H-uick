package com.dohit.huick.global.handler;

import static com.dohit.huick.domain.auth.repository.OAuth2AuthorizationRequestBasedOnCookieRepository.*;

import java.io.IOException;
import java.net.URI;
import java.util.Collection;
import java.util.Date;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import com.dohit.huick.domain.auth.constant.Role;
import com.dohit.huick.domain.auth.constant.SocialType;
import com.dohit.huick.domain.auth.entity.RefreshToken;
import com.dohit.huick.domain.auth.repository.OAuth2AuthorizationRequestBasedOnCookieRepository;
import com.dohit.huick.domain.auth.repository.RefreshTokenRepository;
import com.dohit.huick.domain.user.entity.OAuth2UserInfo;
import com.dohit.huick.domain.user.entity.OAuth2UserInfoFactory;
import com.dohit.huick.domain.user.repository.UserRepository;
import com.dohit.huick.global.property.AppProperties;
import com.dohit.huick.global.token.AuthToken;
import com.dohit.huick.global.token.AuthTokenProvider;
import com.dohit.huick.global.util.CookieUtil;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

	private final AuthTokenProvider tokenProvider;
	private final AppProperties appProperties;
	private final RefreshTokenRepository refreshTokenRepository;
	private final UserRepository userRepository;
	private final OAuth2AuthorizationRequestBasedOnCookieRepository authorizationRequestRepository;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
		Authentication authentication) throws IOException, ServletException {
		String targetUrl = determineTargetUrl(request, response, authentication);

		if (response.isCommitted()) {
			logger.debug("Response has already been committed. Unable to redirect to " + targetUrl);
			return;
		}

		clearAuthenticationAttributes(request, response);
		getRedirectStrategy().sendRedirect(request, response, targetUrl);
	}

	protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response,
		Authentication authentication) {
		Optional<String> redirectUri = CookieUtil.getCookie(request, REDIRECT_URI_PARAM_COOKIE_NAME)
			.map(Cookie::getValue);

		if (redirectUri.isPresent() && !isAuthorizedRedirectUri(redirectUri.get())) {
			throw new IllegalArgumentException(
				"Sorry! We've got an Unauthorized Redirect URI and can't proceed with the authentication");
		}

		String targetUrl = redirectUri.orElse(getDefaultTargetUrl());

		OAuth2AuthenticationToken authToken = (OAuth2AuthenticationToken)authentication;
		SocialType socialType = SocialType.valueOf(authToken.getAuthorizedClientRegistrationId().toUpperCase());

		OidcUser user = ((OidcUser)authentication.getPrincipal());
		OAuth2UserInfo userInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(socialType, user.getAttributes());
		Long userId = userRepository.findBySocialId(userInfo.getId()).get().getUserId();
		Collection<? extends GrantedAuthority> authorities = ((OidcUser)authentication.getPrincipal()).getAuthorities();

		Role role = hasAuthority(authorities, Role.ADMIN.getCode()) ? Role.ADMIN : Role.USER;

		Date now = new Date();
		AuthToken accessToken = tokenProvider.createAuthToken(
			String.valueOf(userId),
			role.getCode(),
			new Date(now.getTime() + appProperties.getAuth().getTokenExpiry())
		);

		// refresh 토큰 설정
		long refreshTokenExpiry = appProperties.getAuth().getRefreshTokenExpiry();

		AuthToken refreshToken = tokenProvider.createAuthToken(
			appProperties.getAuth().getTokenSecret(),
			new Date(now.getTime() + refreshTokenExpiry)
		);
		RefreshToken newRefreshToken = RefreshToken.of(userId, refreshToken.getToken());

		// DB 저장
		RefreshToken oldRefreshToken = refreshTokenRepository.findByUserId(userId).orElse(null);
		if (oldRefreshToken == null) {
			refreshTokenRepository.save(newRefreshToken);
		} else {
			refreshTokenRepository.updateRefreshToken(userId, newRefreshToken);
		}

		int cookieMaxAge = (int)refreshTokenExpiry / 60;

		CookieUtil.deleteCookie(request, response, REFRESH_TOKEN);
		CookieUtil.addCookie(response, REFRESH_TOKEN, refreshToken.getToken(), cookieMaxAge);
		return UriComponentsBuilder.fromUriString(targetUrl)
			.queryParam("token", accessToken.getToken())
			.queryParam("refresh", refreshToken.getToken())
			.build().toUriString();
	}

	protected void clearAuthenticationAttributes(HttpServletRequest request, HttpServletResponse response) {
		super.clearAuthenticationAttributes(request);
		authorizationRequestRepository.removeAuthorizationRequestCookies(request, response);
	}

	private boolean hasAuthority(Collection<? extends GrantedAuthority> authorities, String authority) {
		if (authorities == null) {
			return false;
		}

		for (GrantedAuthority grantedAuthority : authorities) {
			if (authority.equals(grantedAuthority.getAuthority())) {
				return true;
			}
		}
		return false;
	}

	private boolean isAuthorizedRedirectUri(String uri) {
		URI clientRedirectUri = URI.create(uri);

		return appProperties.getOauth2().getAuthorizedRedirectUris()
			.stream()
			.anyMatch(authorizedRedirectUri -> {
				// Only validate host and port. Let the clients use different paths if they want to
				URI authorizedURI = URI.create(authorizedRedirectUri);
				return authorizedURI.getHost().equalsIgnoreCase(clientRedirectUri.getHost())
					&& authorizedURI.getPort() == clientRedirectUri.getPort();
			});
	}

}
