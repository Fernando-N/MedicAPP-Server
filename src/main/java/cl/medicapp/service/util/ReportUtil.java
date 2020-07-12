package cl.medicapp.service.util;

import cl.medicapp.service.document.ReportDocument;
import cl.medicapp.service.dto.ReportDto;
import org.dozer.DozerBeanMapper;

/**
 * Clase util para RoleDto y RoleDocument
 */
public class ReportUtil {

    private static final DozerBeanMapper mapper = new DozerBeanMapper();

    public static ReportDto toReporDto(ReportDocument reportDocument) {
        return mapper.map(reportDocument, ReportDto.class);
    }

    public static ReportDocument toReportDocument(ReportDto reportDto) {
        return mapper.map(reportDto, ReportDocument.class);
    }

    /**
     * Constructor privado para no permitir crear instancias de la clase
     */
    private ReportUtil() {

    }

}
