package com.swp391.koibe.responses;

import com.swp391.koibe.models.Category;
import com.swp391.koibe.responses.base.BaseResponse;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@NoArgsConstructor
public class CategoryResponse extends BaseResponse<Category> {
}
