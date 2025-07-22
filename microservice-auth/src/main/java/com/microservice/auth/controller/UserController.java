package com.microservice.auth.controller;

import com.flowshop.common.api.response.ApiSuccessResponse;
import com.flowshop.common.api.response.PageResponse;
import com.flowshop.common.util.ResponseBuilder;
import com.microservice.auth.dto.response.UserResponseDTO;
import com.microservice.auth.service.IUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final IUserService userService;

    @Operation(
            summary = "Retrieve all users (paginated)",
            description = "Returns a paginated list of all registered users. Requires ADMIN role."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Users retrieved successfully"),
            @ApiResponse(responseCode = "403", description = "Access denied. Only ADMINs are allowed")
    })
    @GetMapping
    public ResponseEntity<ApiSuccessResponse<PageResponse<UserResponseDTO>>> findAll(@PageableDefault(size = 3, sort = {"email"}) Pageable pageable,
                                                                                     HttpServletRequest request){
        Page<UserResponseDTO> page = userService.findAllUsers(pageable);

        PageResponse<UserResponseDTO> pageResponse = new PageResponse<>(
                page.getContent(),
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages(),
                page.isLast(),
                page.getNumberOfElements()
        );

        ApiSuccessResponse<PageResponse<UserResponseDTO>> response = ResponseBuilder.buildSuccessResponse(
                HttpStatus.OK.value(),
                "Users retrieved successfully",
                pageResponse,
                request
        );

        return ResponseEntity.ok(response);
    }
}
