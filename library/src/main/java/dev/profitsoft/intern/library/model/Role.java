package dev.profitsoft.intern.library.model;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class Role {

    private RoleEnum id;

//    private List<PrivilegeEnum> privileges;
    private List<String> privileges;

}
