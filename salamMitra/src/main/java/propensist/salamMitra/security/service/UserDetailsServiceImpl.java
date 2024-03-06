// package propensist.salamMitra.security.service;
// import jakarta.transaction.Transactional;
// import propensist.salamMitra.model.Pengguna;
// import propensist.salamMitra.repository.PenggunaDb;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.security.core.GrantedAuthority;
// import org.springframework.security.core.authority.SimpleGrantedAuthority;
// import org.springframework.security.core.userdetails.User;
// import org.springframework.security.core.userdetails.UserDetails;
// import org.springframework.security.core.userdetails.UserDetailsService;
// import org.springframework.security.core.userdetails.UsernameNotFoundException;
// import org.springframework.stereotype.Service;

// import java.util.HashSet;
// import java.util.Set;

// @Service
// public class UserDetailsServiceImpl implements UserDetailsService {
//     @Autowired
//     private PenggunaDb penggunaDb;

//     @Override
//     public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
//         Pengguna user = penggunaDb.findByUsername(username);
//         Set<GrantedAuthority> grantedAuthoritySet = new HashSet<GrantedAuthority>();
//         grantedAuthoritySet.add(new SimpleGrantedAuthority(user.getRole()));
//         return new User(user.getUsername(), user.getPassword(), grantedAuthoritySet);
//     }
// }
