package com.desafio.firstdecision.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Builder
public class UsuarioDTO {

    @Schema(description = "Id do usuário", example = "1")
    private Long id;

    @NotNull(message = "Campo nome não é permitido valor nulo. Por favor, informe um nome válido.")
    @Size(min = 3, max = 50, message = "Campo nome deve ter entre 3 e 50 caracteres.")
    @Schema(description = "Nome do usuário", example = "Fulando Teste da Silva")
    private String nome;

    @NotNull(message = "Campo email não é permitido valor nulo. Por favor, informe e-mail válido.")
    @Pattern(regexp = "[A-Za-z0-9+_.-]+@(.+)$", message = "Campo email inválido. Por favor, informe um email válido.")
    @Size(max = 50, message = "Campo email deve até 255 caracteres.")
    @Schema(description = "E-mail do usuário", example = "teste@teste.com")
    private String email;

    @NotNull(message = "Campo senha não é permitido valor nulo. Por favor, informe uma senha válida.")
    @Size(min = 6, max = 20, message = "Campo senha deve ter entre 6 e 20 caracteres.")
    @Schema(description = "Senha do usuário", example = "123456")
    private String senha;

    @NotNull(message = "Campo confirmacaoSenha não é permitido valor nulo. Por favor, repita a senha de confirmação.")
    @Size(min = 6, max = 20, message = "Campo confirmacaoSenha deve ter entre 6 e 20 caracteres.")
    @Schema(description = "Confirmação de senha do usuário", example = "123456")
    private String confirmaSenha;

}
