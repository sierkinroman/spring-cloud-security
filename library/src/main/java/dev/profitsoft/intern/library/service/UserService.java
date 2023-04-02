package dev.profitsoft.intern.library.service;

import dev.profitsoft.intern.library.dto.BookInfoDto;
import dev.profitsoft.intern.library.dto.UserSaveDto;
import dev.profitsoft.intern.library.exception.NotFoundException;
import dev.profitsoft.intern.library.model.Book;
import dev.profitsoft.intern.library.model.Role;
import dev.profitsoft.intern.library.model.RoleEnum;
import dev.profitsoft.intern.library.model.User;
import dev.profitsoft.intern.library.repository.RoleRepository;
import dev.profitsoft.intern.library.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final BookService bookService;

    @PostConstruct
    public void initUsers() {
        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        userRepository.save(User.builder()
                .username("admin")
                .password(encoder.encode("admin"))
                .role(RoleEnum.ADMIN)
                .enabled(true)
                .build());
        userRepository.save(User.builder()
                .username("user")
                .password(encoder.encode("user"))
                .role(RoleEnum.USER)
                .enabled(true)
                .build());
        userRepository.save(User.builder()
                .username("user2")
                .password(encoder.encode("user2"))
                .role(RoleEnum.USER)
                .enabled(true)
                .build());
    }

    @Transactional
    public long save(UserSaveDto dto) {
        User user = new User();
        updateUserFromDto(user, dto);
        return userRepository.save(user).getId();
    }

    @Transactional
    public void addBookToUser(long userId, long bookId) {
        User user = getOrThrow(userId);
        Book book = bookService.findById(bookId);
        user.getBooks().add(book);
        userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public Set<BookInfoDto> getBooks(long id) {
        return getOrThrow(id).getBooks().stream()
                .map(bookService::convertToBookInfo)
                .collect(Collectors.toSet());
    }

    private void updateUserFromDto(User user, UserSaveDto dto) {
        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        user.setUsername(dto.getUsername());
        user.setPassword(encoder.encode(dto.getPassword()));
        user.setRole(RoleEnum.USER);
        user.setEnabled(dto.getEnabled());
    }

    @Transactional(readOnly = true)
    public long getIdByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException("User with username '%s' not found".formatted(username)))
                .getId();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .map(this::toUserDetails)
                .orElseThrow(() -> new UsernameNotFoundException("User with username '%s' not found".formatted(username)));
    }

    private UserDetails toUserDetails(User user) {
        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPassword())
                .authorities(collectAuthorities(user.getRole()))
                .disabled(!user.isEnabled())
                .build();
    }

    private List<GrantedAuthority> collectAuthorities(RoleEnum role) {
        return roleRepository.getRole(role)
                .map(Role::getPrivileges)
                .stream().flatMap(Collection::stream)
                .map(priv -> new SimpleGrantedAuthority("PRIV_" + priv))
                .collect(Collectors.toList());
    }

    private User getOrThrow(long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User with id '%d' not found".formatted(id)));
    }
}
