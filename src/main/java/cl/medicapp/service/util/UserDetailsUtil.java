package cl.medicapp.service.util;

import cl.medicapp.service.document.RoleDocument;
import cl.medicapp.service.document.UserDocument;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Clase utilitaria para detalles de usuario
 */
public class UserDetailsUtil {

    /**
     * Builder de objeto User a UserDetails
     * @param user Usuario a mapear
     * @return UserDetails
     */
    public static UserDetails build(UserDocument user) {
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), user.getEnabled(), true, true, true, getAuthorities(user));
    }

    /**
     * Obtiene los authorities del usuario
     * @param retrievedUserDocument usuario
     * @return Set con roles/autorizaciones del usuario
     */
    private static Set<? extends GrantedAuthority> getAuthorities(UserDocument retrievedUserDocument) {
        List<RoleDocument> roleEntities = retrievedUserDocument.getRoleEntities();
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        roleEntities.forEach(roleDocument -> authorities.add(new SimpleGrantedAuthority(roleDocument.getName())));
        return authorities;
    }

    /**
     * Constructor privado para no permitir crear instancias de la clase
     */
    private UserDetailsUtil() {

    }

}