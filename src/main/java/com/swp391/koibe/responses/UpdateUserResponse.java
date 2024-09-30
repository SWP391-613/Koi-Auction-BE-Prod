package com.swp391.koibe.responses;

import com.swp391.koibe.models.User;
import com.swp391.koibe.responses.base.BaseResponse;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@NoArgsConstructor
@Setter
public class UpdateUserResponse extends BaseResponse<User> {

    private UserResponse userResponse;

}
