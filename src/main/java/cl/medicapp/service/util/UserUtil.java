package cl.medicapp.service.util;

import cl.medicapp.service.document.CommuneDocument;
import cl.medicapp.service.document.ParamedicDetailsDocument;
import cl.medicapp.service.document.RoleDocument;
import cl.medicapp.service.document.UserDetailsDocument;
import cl.medicapp.service.document.UserDocument;
import cl.medicapp.service.dto.RoleDto;
import cl.medicapp.service.dto.UserDto;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Clase util para UserDto, UserDocument
 */
public class UserUtil {

    /**
     * Transforma UserDocument -> UserDto
     *
     * @param userDocument target
     * @return UserDto
     */
    public static UserDto toUserDto(UserDocument userDocument) {
        UserDto user = UserDto.builder()
                .id(userDocument.getId())
                .email(userDocument.getEmail())
                .rut(userDocument.getUserDetails().getRut())
                .firstName(userDocument.getUserDetails().getFirstName())
                .lastName(userDocument.getUserDetails().getLastName())
                .birthDay(userDocument.getUserDetails().getBirthDay())
                .commune(CommuneUtil.toCommuneDto(userDocument.getUserDetails().getCommune()))
                .region(RegionUtil.toRegionDto(userDocument.getUserDetails().getCommune().getRegion()))
                .showAddress(userDocument.getUserDetails().isShowAddress())
                .address(userDocument.getUserDetails().getAddress())
                .aboutMe(userDocument.getUserDetails().getAboutMe())
                .isParamedic(hasRoleParamedic(userDocument.getRoleEntities()))
                .createdOn(userDocument.getCreatedOn())
                .attempts(userDocument.getAttempts())
                .enabled(userDocument.getEnabled())
                .roleEntities(roleListDto(userDocument.getRoleEntities()))
                .profileImage(userDocument.getUserDetails().getProfileImageURI())
                .build();

        if (user.isParamedic()) {
            user.setGraduationYear(userDocument.getParamedicDetails().getGraduationYear());
            user.setTitleImage(userDocument.getParamedicDetails().getTitleImageURI());
            user.setCertificateNationalHealth(userDocument.getParamedicDetails().getCertificateNationalHealthURI());
            user.setCarnetImage(userDocument.getParamedicDetails().getCarnetImageURI());
        }

        return user;
    }

    private static boolean hasRoleParamedic(List<RoleDocument> roles) {
        return roles
                .stream()
                .anyMatch(roleDocument -> roleDocument.getName().equals("ROLE_PARAMEDIC"));
    }

    private static List<RoleDto> roleListDto(List<RoleDocument> roles) {
        return roles.stream().map(RoleUtil::toRoleDto).collect(Collectors.toList());
    }

    /**
     * Transforma UserDto -> UserDocument
     *
     * @param userDto target
     * @return UserDocument
     */
    public static UserDocument toUserDocument(UserDto userDto) {
        return UserDocument.builder()
                .email(userDto.getEmail())
                .enabled(!userDto.isParamedic())
                .attempts(0)
                .build();
    }

    public static UserDocument merge(UserDto newUser, UserDocument actualUser) {
        return UserDocument.builder()
                .id(actualUser.getId())
                .paramedicDetails(newUser.isParamedic() ? buildParamedicDetailsDocument(newUser) : null)
                .attempts(actualUser.getAttempts())
                .email(newUser.getEmail())
                .enabled(newUser.getEnabled())
                .createdOn(actualUser.getCreatedOn())
                .roleEntities(actualUser.getRoleEntities())
                .userDetails(buildUserDetailsDocument(newUser, CommuneUtil.toCommuneDocument(newUser.getCommune())))
                .lastLogin(actualUser.getLastLogin())
                .build();
    }

    /**
     * Genera UserDetailsDocument a partir de un UserDto y CommuneDocument
     *
     * @param newUser         usuario nuevo
     * @param communeDocument Comuna referencia
     * @return UserDetailsDocument
     */
    public static UserDetailsDocument buildUserDetailsDocument(UserDto newUser, CommuneDocument communeDocument) {
        return UserDetailsDocument.builder()
                .rut(newUser.getRut())
                .firstName(newUser.getFirstName())
                .lastName(newUser.getLastName())
                .birthDay(newUser.getBirthDay())
                .profileImageURI(newUser.getProfileImage())
                .aboutMe(newUser.getAboutMe())
                .commune(communeDocument)
                .showAddress(newUser.isShowAddress())
                .address(newUser.getAddress())
                .build();
    }

    /**
     * Genera ParamedicDetailsDocument a partir de un UserDto
     *
     * @param newUser         usuario nuevo
     * @return UserDetailsDocument
     */
    public static ParamedicDetailsDocument buildParamedicDetailsDocument(UserDto newUser) {

        return ParamedicDetailsDocument.builder()
                .graduationYear(newUser.getGraduationYear())
                .carnetImageURI(newUser.getCarnetImage())
                .titleImageURI(newUser.getTitleImage())
                .certificateNationalHealthURI(newUser.getCertificateNationalHealth())
                .build();
    }

    /**
     * Obtiene el email del usuario logeado
     *
     * @return email usuario logeado
     */
    public static String getEmailUserLogged() {
        return ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
    }

    /**
     * Constructor privado para no permitir crear instancias de la clase
     */
    private UserUtil() {

    }

}
