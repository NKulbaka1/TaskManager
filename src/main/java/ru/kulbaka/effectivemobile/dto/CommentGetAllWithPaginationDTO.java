package ru.kulbaka.effectivemobile.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "Форма получения конментариев к задаче с пагинацией")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentGetAllWithPaginationDTO {

    @Schema(description = "Номер страницы", example = "0")
    @Min(value = 0, message = "Offset must be at least 0")
    @NotNull(message = "offset does not have to be null")
    private Integer offset;

    @Schema(description = "Количество комментариев на странице", example = "10")
    @Min(value = 1, message = "Limit must be at least 1")
    @Min(value = 0, message = "Limit must be less then 100")
    @NotNull(message = "limit does not have to be null")
    private Integer limit;
}
