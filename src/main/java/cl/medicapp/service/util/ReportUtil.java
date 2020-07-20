package cl.medicapp.service.util;

import cl.medicapp.service.document.ReportDocument;
import cl.medicapp.service.document.UserDocument;
import cl.medicapp.service.dto.ReportDto;

import java.util.Date;

/**
 * Clase util para reportes
 */
public class ReportUtil {

    public static ReportDto toReporDto(ReportDocument reportDocument) {
        return ReportDto.builder()
                .id(reportDocument.getId())
                .alreadyRead(reportDocument.isAlreadyRead())
                .date(reportDocument.getDate())
                .fromUser(UserUtil.toUserDto(reportDocument.getFrom()))
                .toUser(UserUtil.toUserDto(reportDocument.getTo()))
                .message(reportDocument.getMessage())
                .build();
    }

    public static ReportDocument toReportDocument(ReportDto reportDto, UserDocument from, UserDocument to) {
        return ReportDocument.builder()
                .message(reportDto.getMessage())
                .date(DateUtil.from(new Date()))
                .alreadyRead(false)
                .from(from)
                .to(to)
                .build();
    }

    /**
     * Constructor privado para no permitir crear instancias de la clase
     */
    private ReportUtil() {

    }

}
