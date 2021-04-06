package ae.innovativesolutions.security.jwt;

import ae.innovativesolutions.model.User;
import ae.innovativesolutions.payload.AuthData;
import ae.innovativesolutions.payload.LoginRequest;
import ae.innovativesolutions.payload.SigninPayload;
import ae.innovativesolutions.repositoriy.UserRepository;
import io.jsonwebtoken.*;
import io.jsonwebtoken.impl.DefaultClock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

/**
 * Created by Syed.
 */
@Component
public class JwtTokenUtil implements Serializable {

    private Clock clock = DefaultClock.INSTANCE;

    @Value("${jwt.secret}")
    private String secret;
    
    private Map<String,String> userTokensMap = new HashMap();

    private static final Logger logger = LoggerFactory.getLogger(JwtTokenUtil.class);

    @Autowired
	private UserRepository userRepository;



    public String getUsernameFromToken(String token) {
    	//if(!getUserTokensMap().containsValue(token)) {
    	//	throw new ExpiredJwtException(null, null, "Token has been revoked!");
    	//}
        return getClaimFromToken(token, Claims::getSubject);
    }


    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser()
            .setSigningKey(secret)
            .parseClaimsJws(token)
            .getBody();
    }

    public String generateToken(String username) {
        Map<String, Object> claims = new HashMap<>();
        return doGenerateToken(claims, username);
    }
    
    private String doGenerateToken(Map<String, Object> claims, String subject) {
        final Date createdDate = clock.now();
        String token = Jwts.builder()
            .setClaims(claims)
            .setSubject(subject)
            .setIssuedAt(createdDate)
            .signWith(SignatureAlgorithm.HS512, secret)
            .compact();
        //Add token to DB
        Optional<User> user = userRepository.findByUsername(subject);
        user.get().setToken(token);
        userRepository.save(user.get());
        
        getUserTokensMap().put(subject, token);
        
        return token;
    }

    public void invalidateToken(String username) {
    	getUserTokensMap().remove(username);
    	//Remove token from DB
        Optional<User> user = userRepository.findByUsername(username);
        user.get().setToken("");
        userRepository.save(user.get());
    }

	public Map<String, String> getUserTokensMap() {
		//query will be called once when jwtUtil created//
		if(userTokensMap.isEmpty()) {
		   userRepository.findAll().forEach( (user) -> {
			   userTokensMap.put(user.getUsername(),user.getToken());
		   } );
		}
		return userTokensMap;
	}

    public boolean validateToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(secret).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException ex) {
            logger.error("Invalid JWT signature");
        } catch (MalformedJwtException ex) {
            logger.error("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            logger.error("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            logger.error("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            logger.error("JWT claims string is empty.");
        }
        return false;
    }

    public SigninPayload logInUser(LoginRequest authData, UserDetailsService userDetailsService) throws Exception {
        User user = userRepository.findByUsername(authData.getUsername()).get();
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if (encoder.matches(authData.getPassword(), user.getPassword())) {
            logger.info("match");
            UserDetails userDetails = userDetailsService.loadUserByUsername(authData.getUsername());
            logger.info("userdetails: {}", userDetails);
            Authentication authentication = new UsernamePasswordAuthenticationToken(authData.getUsername(), authData.getPassword(), userDetails.getAuthorities());
            logger.info("authentication: {}", authentication);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            //context.getRequest().get().getSession(true).setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());
            logger.info("context is ready");
            return new SigninPayload(generateToken(user.getUsername()), user);
        }
        throw new Exception("Invalid credentials");

    }

}
