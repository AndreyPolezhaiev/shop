package com.polezhaiev.shop.service.user.impl;

import com.polezhaiev.shop.dto.user.UserRegistrationRequestDto;
import com.polezhaiev.shop.dto.user.UserResponseDto;
import com.polezhaiev.shop.exception.RegistrationException;
import com.polezhaiev.shop.mapper.UserMapper;
import com.polezhaiev.shop.model.Role;
import com.polezhaiev.shop.model.ShoppingCart;
import com.polezhaiev.shop.model.User;
import com.polezhaiev.shop.repository.role.RoleRepository;
import com.polezhaiev.shop.repository.shopcart.ShoppingCartRepository;
import com.polezhaiev.shop.repository.user.UserRepository;
import com.polezhaiev.shop.service.user.UserService;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final ShoppingCartRepository shoppingCartRepository;

    @Override
    public UserResponseDto register(UserRegistrationRequestDto requestDto)
            throws RegistrationException {
        if (userRepository.findByEmail(requestDto.getEmail()).isPresent()) {
            throw new RegistrationException("There is already user with such email");
        }
        User user = userMapper.toModel(requestDto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Role userRole = roleRepository.findByName(Role.RoleName.USER.name());
        user.setRoles(Set.of(userRole));
        User saved = userRepository.save(user);

        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setUser(saved);
        shoppingCart.setCartItems(Set.of());
        shoppingCartRepository.save(shoppingCart);
        return userMapper.toResponseDto(saved);
    }
}
