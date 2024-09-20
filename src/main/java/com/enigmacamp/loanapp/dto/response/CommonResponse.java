package com.enigmacamp.loanapp.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommonResponse <T> {
    private String message;
    private Integer status;
    private T data;
    private PagingResponse paging;
}
