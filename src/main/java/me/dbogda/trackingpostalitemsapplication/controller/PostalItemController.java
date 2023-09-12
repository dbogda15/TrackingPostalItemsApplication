package me.dbogda.trackingpostalitemsapplication.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import me.dbogda.trackingpostalitemsapplication.model.PostalItem;
import me.dbogda.trackingpostalitemsapplication.model.Report;
import me.dbogda.trackingpostalitemsapplication.service.PostalItemService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/items")
@ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Запрос выполнен успешно"),
        @ApiResponse(responseCode = "400", description = "Ошибка в параметрах запроса"),
        @ApiResponse(responseCode = "404", description = "Несуществующий URL"),
        @ApiResponse(responseCode = "500", description = "Ошибка со стороны сервера")
})
public class PostalItemController {
    private final PostalItemService postalItemService;

    @PostMapping
    @Operation(summary = "Создание почтового отправления без привязки к почтовому отделению")
    public ResponseEntity<PostalItem> create(@RequestParam PostalItem.Type type,
                                             @RequestParam Integer recipientsIndex,
                                             @RequestParam String recipientsAddress,
                                             @RequestParam String recipientsName) {
        PostalItem newItem = new PostalItem (type, recipientsIndex, recipientsAddress, recipientsName, PostalItem.Status.NEW, 0L);
        postalItemService.create(newItem);
        return ResponseEntity.ok(newItem);
    }

    @PutMapping("/moving")
    @Operation(summary = "Перемещение почтового отправления (приём отправления в почтовом отделении, перемещение между отделениями и прибытие в нужное отделение)")
    public ResponseEntity<String> moving (@RequestParam Long itemId,
                                          @RequestParam Long officeId) {
        String message = postalItemService.moving(itemId, officeId);
        return ResponseEntity.ok(message);
    }

    @GetMapping("/item_id")
    @Operation(summary = "Получение информации по ID")
    public PostalItem getById(@RequestParam Long id){
        return postalItemService.getById(id);
    }

    @GetMapping("/office_id")
    @Operation(summary = "Получение информаии о всех отправлениях по ID почтового отделения")
    public List<PostalItem> getByOfficeId(@RequestParam Long id){
        return postalItemService.getAllByPostOfficeId(id);
    }

    @GetMapping("/getAll")
    @Operation(summary = "Получение информаии о всех отправлениях")
    private List<PostalItem> getAll(){
        return postalItemService.getAll();
    }

    @DeleteMapping
    @Operation(summary = "Удаление отправления по ID")
    private void delete (@RequestParam Long id){
        postalItemService.deleteById(id);
    }

    @PutMapping("/update")
    @Operation(summary = "Обновление информации об отправлении")
    private ResponseEntity<PostalItem> update (@RequestParam Long id,
                                               @RequestParam PostalItem.Type type,
                                               @RequestParam Integer recipientsIndex,
                                               @RequestParam String recipientsAddress,
                                               @RequestParam String recipientsName) {
        PostalItem old = postalItemService.getById(id);
        PostalItem result = postalItemService.update(new PostalItem(id, type, recipientsIndex, recipientsAddress, recipientsName, old.getStatus(), old.getPostOfficeId(), old.getTrackingStory()));
        return ResponseEntity.ok(result);
    }

    @GetMapping("/tracking")
    @Operation(summary = "Получение информации о перемещениях почтовго отправления")
    private List<Report> getTrackingByItemId(@RequestParam Long id){
        return postalItemService.getById(id).getTrackingStory();
    }

}

