package cl.medicapp.service.services.report;

import cl.medicapp.service.dto.GenericResponseDto;
import cl.medicapp.service.dto.ReportDto;

import java.util.List;

public interface ReportService {

    List<ReportDto> getAll();

    List<ReportDto> getByFromUserId(String id);

    List<ReportDto> getByToUserId(String id);

    ReportDto save(ReportDto request);

    ReportDto update(String id, ReportDto newReportDto);

    GenericResponseDto resolveReportId(String id);

    GenericResponseDto deleteById(String id);

}
