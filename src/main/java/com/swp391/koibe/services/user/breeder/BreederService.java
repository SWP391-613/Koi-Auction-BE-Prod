package com.swp391.koibe.services.user.breeder;

import com.swp391.koibe.exceptions.BreederNotFoundException;
import com.swp391.koibe.exceptions.base.DataNotFoundException;
import com.swp391.koibe.models.User;
import com.swp391.koibe.repositories.UserRepository;
import com.swp391.koibe.responses.UserResponse;
import com.swp391.koibe.utils.DTOConverter;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BreederService implements IBreederService {

    private final UserRepository userRepository;

    @Override
    public Page<UserResponse> getAllBreeders(Pageable pageable) {
        Page<User> breeders = userRepository.findByRoleName("BREEDER", pageable);
        return breeders.map(DTOConverter::convertToUserDTO);
    }

    @Override
    public List<UserResponse> getAllBreeders() {
        return userRepository.findAll()
            .stream()
            .filter(breeder -> breeder.getRole().getId() == 3)
            .map(DTOConverter::convertToUserDTO)
            .toList();
    }

    @Override
    public User findById(long breederId) throws DataNotFoundException {
        Optional<User> breeder = userRepository.findById(breederId);
        User existingBreeder;
        if(breeder.isEmpty()){
            throw new BreederNotFoundException("Breeder not found");
        }else{
            existingBreeder = breeder.get();
            //3 is id of breeder in db
            if(existingBreeder.getRole().getId() != 3){
                throw new BreederNotFoundException("Breeder not found");
            }
        }
        return existingBreeder;
    }

    @Override
    public User create(User breeder) {
        return null;
    }

    @Override
    public User update(long breederId, User breeder) {
        return null;
    }

    @Override
    public void delete(long id) {

    }
}
