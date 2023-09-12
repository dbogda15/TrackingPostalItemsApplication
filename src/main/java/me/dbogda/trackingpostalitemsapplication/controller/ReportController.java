package me.dbogda.trackingpostalitemsapplication.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import me.dbogda.trackingpostalitemsapplication.model.Report;
import me.dbogda.trackingpostalitemsapplication.service.ReportService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reports")
@AllArgsConstructor
@ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Запрос выполнен успешно"),
        @ApiResponse(responseCode = "400", description = "Ошибка в параметрах запроса"),
        @ApiResponse(responseCode = "404", description = "Несуществующий URL"),
        @ApiResponse(responseCode = "500", description = "Ошибка со стороны сервера")
})
public class ReportController {

    private final ReportService reportService;

    @PostMapping
    @Operation(summary = "Создание нового отчета")
    public ResponseEntity<Report> create(@RequestParam String message,
                                         @RequestParam Long itemId){
        return ResponseEntity.ok(reportService.create(new Report(message, itemId)));
    }

    @DeleteMapping
    @Operation(summary = "Удаление отчета по его ID")
    public ResponseEntity<String> delete (@RequestParam Long id){
        return ResponseEntity.ok(reportService.deleteById(id));
    }

    @PutMapping
    @Operation(summary = "обновление отчета по его ID")
    public ResponseEntity<Report> update (@RequestParam Long id,
                                          @RequestParam String message,
                                          @RequestParam Long itemId){
        return ResponseEntity.ok(reportService.update(new Report(id, message, itemId)));
    }

    @GetMapping
    @Operation(summary = "Получение списка отчетов по ID почтового отправления")
    public ResponseEntity<List<Report>> getAllByItemId(@RequestParam Long itemId){
        return ResponseEntity.ok(reportService.getAllByItemId(itemId));
    }
}
