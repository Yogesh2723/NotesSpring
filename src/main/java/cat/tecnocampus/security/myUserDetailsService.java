package cat.tecnocampus.security;

import cat.tecnocampus.databaseRepositories.UserLabRepository;
import cat.tecnocampus.domain.UserLab;
import cat.tecnocampus.exceptions.UserLabNotFoundException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by roure on 27/10/2016.
 */
@Service
public class myUserDetailsService implements UserDetailsService {
    private UserSecurityRepository userSecurityRepository;

    public myUserDetailsService(UserSecurityRepository userSecurityRepository) {
        this.userSecurityRepository = userSecurityRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UserLabNotFoundException {
        UserSecurity user = userSecurityRepository.findOne(username);

        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        for (String role : user.getRoles()){
            grantedAuthorities.add(new SimpleGrantedAuthority(role));
        }

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), grantedAuthorities);
    }
}
