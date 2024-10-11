package com.swp391.koibe.responses;

import com.swp391.koibe.responses.base.BaseResponse;
import java.util.Map;
import lombok.experimental.SuperBuilder;

@SuperBuilder
public class ExceptionResponse extends BaseResponse<Object> {

    private Map<String, Object> details;

}
