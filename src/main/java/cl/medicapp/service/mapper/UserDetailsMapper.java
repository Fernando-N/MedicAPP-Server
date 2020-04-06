package cl.medicapp.service.mapper;

import cl.medicapp.service.entity.RoleEntity;
import cl.medicapp.service.entity.UserEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Clase map para UserDetails
 */
@Slf4j
public class UserDetailsMapper {

    /**
     * Builder de objeto User a UserDetails
     * @param user Usuario a mapear
     * @return UserDetails
     */
    public static UserDetails build(UserEntity user) {
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), user.getEnabled(), true, true, true, getAuthorities(user));
    }

    /**
     * Obtiene los authorities del usuario
     * @param retrievedUserEntity usuario
     * @return Set con roles/autorizaciones del usuario
     */
    private static Set<? extends GrantedAuthority> getAuthorities(UserEntity retrievedUserEntity) {
        List<RoleEntity> roleEntities = retrievedUserEntity.getRoleEntities();
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        roleEntities.forEach(roleEntity -> authorities.add(new SimpleGrantedAuthority(roleEntity.getName())));
        return authorities;
    }
}