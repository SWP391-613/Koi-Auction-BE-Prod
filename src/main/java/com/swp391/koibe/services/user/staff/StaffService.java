package com.swp391.koibe.services.user.staff;

import com.swp391.koibe.exceptions.base.DataNotFoundException;
import com.swp391.koibe.exceptions.StaffNotFoundException;
import com.swp391.koibe.models.User;
import com.swp391.koibe.repositories.UserRepository;
import com.swp391.koibe.responses.UserResponse;
import com.swp391.koibe.utils.DTOConverter;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StaffService implements IStaffService {

    private final UserRepository userRepository;

    @Override
    public Page<UserResponse> getAllStaffs(Pageable pageable) {
        Page<User> staffs = userRepository.findByRoleName("STAFF", pageable);
        return staffs.map(DTOConverter::convertToUserDTO);
    }

    @Override
    public User findById(long id) throws DataNotFoundException {
        Optional<User> staffs = userRepository.findById(id);
        User existingStaff;
        if(staffs.isEmpty()){
            throw new StaffNotFoundException("Staff not found");
        }else{
            existingStaff = staffs.get();
            //3 is id of staffs in db
            if(existingStaff.getRole().getId() != 2){
                throw new StaffNotFoundException("Staff not found");
            }
        }
        return existingStaff;
    }

    @Override
    public User create(User staff) {
        return null;
    }

    @Override
    public User update(long id, User staff) {
        return null;
    }

    @Override
    public void delete(long id) {

    }
}
