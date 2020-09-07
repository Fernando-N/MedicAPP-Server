package cl.medicapp.service.services.report;

import cl.medicapp.service.dto.GenericResponseDto;
import cl.medicapp.service.dto.ReportDto;

import java.util.List;

/**
 * Interfaz de servicio de reportes
 */
public interface ReportService {

    /**
     * @see ReportServiceImpl#getAll()
     */
    List<ReportDto> getAll();

    /**
     * @see ReportServiceImpl#getByFromUserId(String)
     */
    List<ReportDto> getByFromUserId(String id);

    /**
     * @see ReportServiceImpl#getByToUserId(String)
     */
    List<ReportDto> getByToUserId(String id);

    /**
     * @see ReportServiceImpl#save(ReportDto)
     */
    ReportDto save(ReportDto request);

    /**
     * @see ReportServiceImpl#update(String, ReportDto)
     */
    ReportDto update(String id, ReportDto newReportDto);

    /**
     * @see ReportServiceImpl#resolveReportId(String)
     */
    GenericResponseDto resolveReportId(String id);

    /**
     * @see ReportServiceImpl#deleteById(String)
     */
    GenericResponseDto deleteById(String id);

}
