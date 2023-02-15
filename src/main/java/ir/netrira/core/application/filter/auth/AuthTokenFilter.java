package ir.netrira.core.application.filter.auth;

import ir.netrira.core.application.filter.auth.service.UserDetailsServiceImpl;
import ir.netrira.core.application.filter.auth.util.JwtUtils;
import ir.netrira.core.application.filter.access.FilterCodes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

public class AuthTokenFilter extends OncePerRequestFilter {
  @Autowired
  private JwtUtils jwtUtils;

  @Autowired
  private UserDetailsServiceImpl userDetailsService;

  private static final Logger logger = LoggerFactory.getLogger(AuthTokenFilter.class);

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
      logger.info(AuthTokenFilter.class.toString());
      String jwt = parseJwt(request);
      if (Objects.nonNull(jwt) && !FilterCodes.NULL_STRING.equals(jwt) && jwtUtils.validateJwtToken(jwt)) {
        String username = jwtUtils.getUserNameFromJwtToken(jwt);
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        UsernamePasswordAuthenticationToken authentication =
            new UsernamePasswordAuthenticationToken(
                userDetails,
                null,
                userDetails.getAuthorities());
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authentication);
      }
    filterChain.doFilter(request, response);
  }

  private String parseJwt(HttpServletRequest request) {
    String authHeader = request.getHeader(FilterCodes.AUTHORIZATION);
    return StringUtils.hasText(authHeader) ? authHeader : request.getParameter(FilterCodes.AUTHORIZATION);
  }
}
