package ae.innovativesolutions.security.jwt;


import ae.innovativesolutions.model.Role;
import ae.innovativesolutions.model.User;
import ae.innovativesolutions.repositoriy.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * Created by Syed.
 */
@Service
public class JwtUserDetailsService implements UserDetailsService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.info("load user...");
        Optional<User> user = userRepository.findByUsername(username);
        if (!user.isPresent()) {
            logger.info("user not found");
            throw new UsernameNotFoundException(String.format("User not found with username '%s'.", username));
        } else {
            logger.info("else:: ");
            return getJwtUser(user.get());
        }
    }


    public JwtUser getJwtUser(User user) {

        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        for (Role role : user.getRoles()){
            grantedAuthorities.add(new SimpleGrantedAuthority(role.getName().name()));
        }

        return new JwtUser(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getPassword(),
                grantedAuthorities);
    }
}
