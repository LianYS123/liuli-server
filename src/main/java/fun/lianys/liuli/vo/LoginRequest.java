package fun.lianys.liuli.vo;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class LoginRequest {
    @NotNull
    private String username;
    @NotNull
    private String password;
}
