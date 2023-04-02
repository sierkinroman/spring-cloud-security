package dev.profitsoft.intern.library.repository;

import dev.profitsoft.intern.library.model.Role;
import dev.profitsoft.intern.library.model.RoleEnum;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Repository
public class RoleRepository {

    private static final Map<RoleEnum, Role> ROLES = List.of(
        Role.builder()
                .id(RoleEnum.USER)
                .privileges(List.of())
                .build(),
        Role.builder()
                .id(RoleEnum.ADMIN)
//                .privileges(List.of(PrivilegeEnum.USER_MANAGEMENT, PrivilegeEnum.BOOS_MANAGEMENT))
                .privileges(List.of("USER_MANAGEMENT", "BOOK_MANAGEMENT"))
                .build()
    )
    .stream()
    .collect(Collectors.toUnmodifiableMap(
            Role::getId,
            Function.identity()
    ));

    public Optional<Role> getRole(RoleEnum id) {
        return Optional.ofNullable(ROLES.get(id));
    }

}
