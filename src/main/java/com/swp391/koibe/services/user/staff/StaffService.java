package com.swp391.koibe.services.user.staff;

import com.swp391.koibe.dtos.UserDTO;
import com.swp391.koibe.enums.UserStatus;
import com.swp391.koibe.exceptions.StaffNotFoundException;
import com.swp391.koibe.exceptions.base.DataAlreadyExistException;
import com.swp391.koibe.exceptions.base.DataNotFoundException;
import com.swp391.koibe.models.Role;
import com.swp391.koibe.models.User;
import com.swp391.koibe.repositories.RoleRepository;
import com.swp391.koibe.repositories.UserRepository;
import com.swp391.koibe.responses.UserResponse;
import com.swp391.koibe.utils.DTOConverter;
import com.swp391.koibe.utils.DateTimeUtils;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StaffService implements IStaffService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Override
    public Page<UserResponse> getAllStaffs(Pageable pageable) {
        Page<User> staffs = userRepository.findByRoleName("STAFF", pageable);
        return staffs.map(DTOConverter::convertToUserDTO);
    }

    @Override
    public User findById(long id) throws DataNotFoundException {
        return userRepository
            .findStaffById(id)
            .orElseThrow(() -> new StaffNotFoundException(String.format("Staff with id %d not found", id)));
    }

    @Override
    public User updateMemberToStaff(long id) {
        Optional<User> user = userRepository.findById(id);
        if(user.isEmpty())
            throw new DataNotFoundException("User not found");
        if(userRepository.findStaffById(id).isPresent())
            throw new DataAlreadyExistException("User is already a staff");

        User newStaff = user.get();
        newStaff.setRole(new Role(2L, "STAFF"));

        return userRepository.save(newStaff);
    }

    @Override
    public User update(long id, UserDTO staff) {
        Optional<User> existingStaff = userRepository.findStaffById(id);
        if(existingStaff.isEmpty())
            throw new DataNotFoundException(String.format("Staff with id %d not found", id));

        if(userRepository.findByEmail(staff.email()).isPresent())
            throw new DataAlreadyExistException("Email already exist");

        if(userRepository.findByPhoneNumber(staff.phoneNumber()).isPresent())
            throw new DataAlreadyExistException("Phone number already exist");

        User staffToUpdate = existingStaff.get();
        staffToUpdate.setFirstName(staff.firstName());
        staffToUpdate.setLastName(staff.lastName());
        staffToUpdate.setPhoneNumber(staff.phoneNumber());
        staffToUpdate.setEmail(staff.email());
        staffToUpdate.setAddress(staff.address());
        staffToUpdate.setPassword(staff.password());
        staffToUpdate.setActive(staff.isActive());
        staffToUpdate.setSubscription(staff.isSubscription());
        staffToUpdate.setStatus(UserStatus.valueOf(staff.statusName()));
        staffToUpdate.setDob(DateTimeUtils.convertFromString(staff.dob()));
        staffToUpdate.setAvatarUrl(staff.avatarUrl());
        staffToUpdate.setGoogleAccountId(staff.googleAccountId());
        staffToUpdate.setAccountBalance(staff.accountBalance());

        return userRepository.save(staffToUpdate);
    }

    @Override
    public void delete(long id) {
        userRepository.findStaffById(id)
            .orElseThrow(() -> new StaffNotFoundException(String.format("Staff with id %d not found", id)));
        //update isActive to false
        Optional<User> existingStaff = userRepository.findById(id);
        if(existingStaff.isEmpty())
            throw new DataNotFoundException(String.format("Staff with id %d not found", id));
        User staff = existingStaff.get();
        staff.setActive(false);
        userRepository.save(staff);
    }
}
