package me.dbogda.trackingpostalitemsapplication.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import me.dbogda.trackingpostalitemsapplication.model.PostOffice;
import me.dbogda.trackingpostalitemsapplication.service.PostOfficeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/offices")
@AllArgsConstructor
@ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Запрос выполнен успешно"),
        @ApiResponse(responseCode = "400", description = "Ошибка в параметрах запроса"),
        @ApiResponse(responseCode = "404", description = "Несуществующий URL"),
        @ApiResponse(responseCode = "500", description = "Ошибка со стороны сервера")
})
public class PostOfficeController {

    private final PostOfficeService postOfficeService;

    @PostMapping
    @Operation(summary = "Создание почтового отделения в БД")
    public ResponseEntity<PostOffice> create (@RequestParam Integer index,
                                              @RequestParam String name,
                                              @RequestParam String recipientsAddress){
        PostOffice postOffice = postOfficeService.create(new PostOffice(index, name, recipientsAddress));
        return ResponseEntity.ok(postOffice);
    }

    @GetMapping("/get_all")
    @Operation(summary = "Получение списка всех почтовых отеделений")
    public List<PostOffice> getAll(){
        return postOfficeService.getAll();
    }

    @GetMapping("/id")
    @Operation(summary = "Получение почтового отделения по его ID")
    public PostOffice getById(@RequestParam Long id){
        return postOfficeService.getById(id);
    }

    @DeleteMapping
    @Operation(summary = "Удаление почтового отделения по его ID")
    public ResponseEntity<String> delete (@RequestParam Long id){
        return ResponseEntity.ok(postOfficeService.deleteById(id));
    }

    @PutMapping
    @Operation(summary = "Обновление почтового отделения по его ID")
    public ResponseEntity<PostOffice> update (@RequestParam Long id,
                                              @RequestParam Integer index,
                                              @RequestParam String name,
                                              @RequestParam String recipientsAddress){
        PostOffice old = postOfficeService.getById(id);
        PostOffice result = postOfficeService.update(new PostOffice(id, index, name, recipientsAddress, old.getPostalItems()));
        return ResponseEntity.ok(result);
    }
}
