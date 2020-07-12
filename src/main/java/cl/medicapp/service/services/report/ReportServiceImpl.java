package cl.medicapp.service.services.report;

import cl.medicapp.service.annotation.FormatArgs;
import cl.medicapp.service.annotation.UpperCase;
import cl.medicapp.service.constants.Constants;
import cl.medicapp.service.document.ReportDocument;
import cl.medicapp.service.document.UserDocument;
import cl.medicapp.service.dto.GenericResponseDto;
import cl.medicapp.service.dto.ReportDto;
import cl.medicapp.service.repository.report.ReportRepository;
import cl.medicapp.service.repository.user.UserRepository;
import cl.medicapp.service.util.GenericResponseUtil;
import cl.medicapp.service.util.ReportUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
//TODO aplicar logica utilizando la clase DocumentsHolder para evitar carga a la bd
public class ReportServiceImpl implements ReportService {

    private final ReportRepository reportRepository;
    private final UserRepository userRepository;


    @Override
    public List<ReportDto> getAll() {
        List<ReportDto> reportDtoList = new ArrayList<>();
        reportRepository.findAll().forEach(report -> reportDtoList.add(ReportUtil.toReporDto(report)));
        return reportDtoList;
    }

    @Override
    public List<ReportDto> getByFromUserId(String id) {

        Optional<UserDocument> userDocumentOptional = userRepository.findById(id);

        if (!userDocumentOptional.isPresent()) {
            throw GenericResponseUtil.buildGenericException(HttpStatus.NOT_FOUND, HttpStatus.NOT_FOUND.getReasonPhrase(), String.format(Constants.ROLE_X_NOT_FOUND, id));
        }


        return reportRepository.findByFrom(userDocumentOptional.get()).stream().map(ReportUtil::toReporDto).collect(Collectors.toList());
    }

    @Override
    public List<ReportDto> getByToUserId(String id) {
        Optional<UserDocument> userDocumentOptional = userRepository.findById(id);

        if (!userDocumentOptional.isPresent()) {
            throw GenericResponseUtil.buildGenericException(HttpStatus.NOT_FOUND, HttpStatus.NOT_FOUND.getReasonPhrase(), String.format(Constants.ROLE_X_NOT_FOUND, id));
        }


        return reportRepository.findByTo(userDocumentOptional.get()).stream().map(ReportUtil::toReporDto).collect(Collectors.toList());
    }

    @Override
    @FormatArgs
    public ReportDto save(ReportDto request) {

        Optional<UserDocument> fromUserOptional = userRepository.findById(request.getFrom().getId());
        Optional<UserDocument> toUserOptional = userRepository.findById(request.getTo().getId());

        if (!fromUserOptional.isPresent() || !toUserOptional.isPresent()) {
            throw GenericResponseUtil.buildGenericException(HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.getReasonPhrase(), String.format(Constants.ROLE_X_ALREADY_EXIST, request.getMessage()));
        }

        reportRepository.save(ReportUtil.toReportDocument(request));

        return request;
    }

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

    @Override
    @FormatArgs
    public GenericResponseDto deleteById(@UpperCase String name) {
        reportRepository.deleteById(name);

        return GenericResponseUtil.buildGenericResponse(HttpStatus.NOT_FOUND.getReasonPhrase(), String.format(Constants.ROLE_X_NOT_FOUND, name));
    }
}
