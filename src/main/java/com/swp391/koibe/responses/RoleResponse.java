package com.swp391.koibe.responses;

import com.swp391.koibe.models.Role;
import com.swp391.koibe.responses.base.BaseResponse;
import java.util.List;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@NoArgsConstructor
@Setter
public class RoleResponse extends BaseResponse<Role> {

}
