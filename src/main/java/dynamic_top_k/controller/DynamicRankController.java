package dynamic_top_k.controller;

import dynamic_top_k.common.Result;
import dynamic_top_k.common.ResultEnum;
import dynamic_top_k.entity.Mention;
import dynamic_top_k.service.DynamicRankService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/api/v1")
@Tag(name = "Dynamic TopK Ranking Service", description = "Update/Query")
public class DynamicRankController {
    @Resource
    private DynamicRankService dynamicRankService;

    @Operation(summary ="Update the word in the rank list.")
    @PostMapping("/update")
    public Result<Mention> update(@RequestBody @NotNull Map<String, Object> hashmap) {
        if (!hashmap.containsKey("target")) {
            return Result.error(ResultEnum.BAD_REQUEST, "Cannot find the target.");
        }
        if (hashmap.get("target") == null) {
            return Result.error(ResultEnum.BAD_REQUEST, "The target is null.");
        }
        if (!(hashmap.get("target") instanceof String target)) {
            return Result.error(ResultEnum.BAD_REQUEST, "The target is not a string.");
        }
        if (hashmap.size() != 1) {
            return Result.error(ResultEnum.BAD_REQUEST, "The JSON object contains invalid number of keys.");
        }
        return Result.success(dynamicRankService.update(target));
    }

    @Operation(summary ="Query the top-k words from the rank list.")
    @PostMapping("/query")
    public Result<List<Mention>> query(@RequestBody @NotNull Map<String, Object> hashmap) {
        if (!hashmap.containsKey("top")) {
            return Result.error(ResultEnum.BAD_REQUEST, "Cannot find the target.");
        }
        if (hashmap.get("top") == null) {
            return Result.error(ResultEnum.BAD_REQUEST, "The target is null.");
        }
        if (!(hashmap.get("top") instanceof String rank)) {
            return Result.error(ResultEnum.BAD_REQUEST, "The target is not a string.");
        }
        if (hashmap.size() != 1) {
            return Result.error(ResultEnum.BAD_REQUEST, "The JSON object contains invalid number of keys.");
        }
        try {
            int rankInteger = Integer.parseInt(rank);
            List<Mention> rankedList = dynamicRankService.query(rankInteger);
            return Result.success(rankedList);
        } catch (NumberFormatException e) {
            return Result.error(ResultEnum.BAD_REQUEST, "The rank number is ill-formated.");
        } catch (IllegalArgumentException e) {
            return Result.error(ResultEnum.BAD_REQUEST, e.getMessage());
        }
    }
}
