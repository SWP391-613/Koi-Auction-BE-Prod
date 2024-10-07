package com.swp391.koibe.utils;

import com.swp391.koibe.models.User;
import java.util.List;

public class FilterUtils {

    public static List<User> filterBreeders(List<User> breeders) {
        return breeders.stream()
            .filter(breeder -> breeder.getRole().getId() == 3)
            .toList();
    }

    public static User findBreederById(List<User> breeders, long breederId) {
        return breeders.stream()
            .filter(breeder -> breeder.getId() == breederId)
            .findFirst()
            .orElse(null);
    }

    public static List<User> filterAdmins(List<User> admins) {
        return admins.stream()
            .filter(admin -> admin.getRole().getId() == 4)
            .toList();
    }

    public static User findAdminById(List<User> admins, long adminId) {
        return admins.stream()
            .filter(admin -> admin.getId() == adminId)
            .findFirst()
            .orElse(null);
    }

    public static List<User> filterMembers(List<User> members) {
        return members.stream()
            .filter(member -> member.getRole().getId() == 1)
            .toList();
    }

    public static User findMemberById(List<User> members, long memberId) {
        return members.stream()
            .filter(member -> member.getId() == memberId)
            .findFirst()
            .orElse(null);
    }

    public static List<User> filterStaffs(List<User> staffs) {
        return staffs.stream()
            .filter(staff -> staff.getRole().getId() == 2)
            .toList();
    }

    public static User findStaffById(List<User> staffs, long staffId) {
        return staffs.stream()
            .filter(staff -> staff.getId() == staffId)
            .findFirst()
            .orElse(null);
    }

}
