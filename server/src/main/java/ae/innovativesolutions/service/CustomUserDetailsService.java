package ae.innovativesolutions.service;

import ae.innovativesolutions.model.User;
import ae.innovativesolutions.repositoriy.UserRepository;
import ae.innovativesolutions.security.jwt.JwtUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.ExpressionException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Created by Syed.
 */

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    JwtUserDetailsService jwtUserDetailsService;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        Optional<User> user= userRepository.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException(String.format("The username %s doesn't exist", username));
        }

        return jwtUserDetailsService.getJwtUser(user.get());
    }

    @Transactional
    public UserDetails loadUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new ExpressionException("User"+"id"+id)
        );

        return  jwtUserDetailsService.getJwtUser(user);
    }
}