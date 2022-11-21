package com.zksy.reservationsystem.controller;

import com.zksy.reservationsystem.common.CommonResult;
import com.zksy.reservationsystem.service.ReserveTypeService;
import com.zksy.reservationsystem.util.annotation.AuthAdmin;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 预约类型选项控制层
 *
 * @author kkkoke
 * @since 2022/11/21
 */
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/type")
public class ReserveTypeController {

    private final ReserveTypeService reserveTypeService;

    /**
     * 新增预约类型选项
     */
    @AuthAdmin
    @PostMapping("/insertReserveType")
    public CommonResult<?> insertReserveType(@NotBlank(message = "type can not be null") String type) {
        if (reserveTypeService.insertReserveType(type)) {
            return CommonResult.success();
        }
        return CommonResult.failed();
    }

    /**
     * 删除预约类型选项
     */
    @AuthAdmin
    @PostMapping("/deleteReserveType")
    public CommonResult<?> deleteReserveType(@NotNull(message = "typeId can not be null") Integer typeId) {
        if (reserveTypeService.deleteReserveType(typeId)) {
            return CommonResult.success();
        }
        return CommonResult.failed();
    }

    /**
     * 获取预约类型选项列表
     */
    @GetMapping("/queryReserveTypeList")
    public CommonResult<?> queryReserveTypeList() {
        return CommonResult.success(reserveTypeService.queryReserveTypeList());
    }

    /**
     * 通过访谈选项id获取访谈类型选项
     */
    @GetMapping("/queryReserveTypeById")
    public CommonResult<?> queryReserveTypeByTypeId(@NotNull(message = "typeId can not be null") Integer typeId) {
        return CommonResult.success(reserveTypeService.queryReserveTypeByTypeId(typeId));
    }
}
