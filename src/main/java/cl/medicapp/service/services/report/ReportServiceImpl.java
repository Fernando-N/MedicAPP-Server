package cl.medicapp.service.services.report;

import cl.medicapp.service.annotation.FormatArgs;
import cl.medicapp.service.constants.Constants;
import cl.medicapp.service.document.ReportDocument;
import cl.medicapp.service.document.UserDocument;
import cl.medicapp.service.dto.GenericResponseDto;
import cl.medicapp.service.dto.ReportDto;
import cl.medicapp.service.repository.report.ReportRepository;
import cl.medicapp.service.repository.user.UserRepository;
import cl.medicapp.service.util.GenericResponseUtil;
import cl.medicapp.service.util.ReportUtil;
import cl.medicapp.service.util.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Implementacion de servicio de reportes
 */
@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {

    /**
     * Bean de repositorio de reportes
     */
    private final ReportRepository reportRepository;

    /**
     * Bean de repositorio de usuarios
     */
    private final UserRepository userRepository;

    /**
     * Obtener todos los reportes
     * @return Lista de reportes
     */
    @Override
    public List<ReportDto> getAll() {
        List<ReportDto> reportDtoList = new ArrayList<>();
        reportRepository.findAll().forEach(report -> reportDtoList.add(ReportUtil.toReporDto(report)));
        return reportDtoList;
    }

    /**
     * Obtener reportes creados por un usuario
     * @param id Id usuario a buscar
     * @return Lista de reportes
     */
    @Override
    public List<ReportDto> getByFromUserId(String id) {

        Optional<UserDocument> userDocumentOptional = userRepository.findById(id);

        if (!userDocumentOptional.isPresent()) {
            throw GenericResponseUtil.buildGenericException(HttpStatus.NOT_FOUND, HttpStatus.NOT_FOUND.getReasonPhrase(), String.format(Constants.ROLE_X_NOT_FOUND, id));
        }

        return reportRepository.findByFrom(userDocumentOptional.get()).stream().map(ReportUtil::toReporDto).collect(Collectors.toList());
    }

    /**
     * Obtener reportes dirigidos a un usuario
     * @param id Id usuario a buscar
     * @return Lista de reportes
     */
    @Override
    public List<ReportDto> getByToUserId(String id) {
        Optional<UserDocument> userDocumentOptional = userRepository.findById(id);

        if (!userDocumentOptional.isPresent()) {
            throw GenericResponseUtil.buildGenericException(HttpStatus.NOT_FOUND, HttpStatus.NOT_FOUND.getReasonPhrase(), String.format(Constants.ROLE_X_NOT_FOUND, id));
        }

        return reportRepository.findByTo(userDocumentOptional.get()).stream().map(ReportUtil::toReporDto).collect(Collectors.toList());
    }

    /**
     * Guardar un reporte
     * @param request Objeto con datos a guardar
     * @return Reporte guardado
     */
    @Override
    @FormatArgs
    public ReportDto save(ReportDto request) {

        Optional<UserDocument> fromUserOptional = userRepository.findByEmailIgnoreCase(UserUtil.getEmailUserLogged());
        Optional<UserDocument> toUserOptional = userRepository.findById(request.getToUser().getId());

        if (!fromUserOptional.isPresent() || !toUserOptional.isPresent()) {
            throw GenericResponseUtil.buildGenericException(HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.getReasonPhrase(), String.format(Constants.ROLE_X_ALREADY_EXIST, request.getMessage()));
        }

        reportRepository.save(ReportUtil.toReportDocument(request, fromUserOptional.get(), toUserOptional.get()));

        return request;
    }

    /**
     * Actualizar reporte
     * @param idReport Id reporte
     * @param newReport Reporte con nuevos datos
     * @return Reporte actualizado
     */
    @Override
    public ReportDto update(String idReport, ReportDto newReport) {

        Optional<ReportDocument> reportDocumentOptional = reportRepository.findById(idReport);

        if (!reportDocumentOptional.isPresent()) {
            throw GenericResponseUtil.buildGenericException(HttpStatus.NOT_FOUND, HttpStatus.NOT_FOUND.getReasonPhrase(), String.format(Constants.ROLE_X_NOT_FOUND, idReport));
        }

        //TODO APLICAR LOGICA PARA PASAR NEW REPORT A REPORTE OBTENIDO DE REPOSITORY

        //reportRepository.save(role.get());

        return newReport;
    }

    /**
     * Marcar como resuelto un reporte
     * @param idReport Id reporte
     * @return Resultado
     */
    @Override
    public GenericResponseDto resolveReportId(String idReport) {
        Optional<ReportDocument> reportDocumentOptional = reportRepository.findById(idReport);

        if (!reportDocumentOptional.isPresent()) {
            throw GenericResponseUtil.buildGenericException(HttpStatus.NOT_FOUND, HttpStatus.NOT_FOUND.getReasonPhrase(), String.format(Constants.ROLE_X_NOT_FOUND, idReport));
        }

        reportDocumentOptional.get().setAlreadyRead(true);
        reportRepository.save(reportDocumentOptional.get());

        return GenericResponseDto.builder()
                .message("Report resolve successfully")
                .details(Collections.singletonList(String.format("Report ID %s resolve successfully", idReport)))
                .build();
    }

    /**
     * Eliminar reporte
     * @param idReporte Id reporte
     * @return Resultado
     */
    @Override
    public GenericResponseDto deleteById(String idReporte) {
        reportRepository.deleteById(idReporte);

        return GenericResponseUtil.buildGenericResponse(HttpStatus.NOT_FOUND.getReasonPhrase(), String.format(Constants.ROLE_X_NOT_FOUND, idReporte));
    }
}
