package com.example.demo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

@Schema(description = "DTO para cadastro de cliente")
public class ClienteDTO {
    @NotBlank
    @Schema(description = "Nome completo do cliente", example = "João Silva")
    private String nome;
    
    @NotBlank
    @Email
    @Schema(description = "E-mail do cliente", example = "joao@example.com")
    private String email;
    
    @NotBlank
    @Pattern(regexp = "^\\(?(\\d{2})\\)?[\\s-]?\\d{4,5}[\\s-]?\\d{4}$", 
             message = "Telefone deve estar no formato (XX) XXXX-XXXX ou (XX) XXXXX-XXXX")
    @Schema(description = "Telefone do cliente", example = "(11) 99999-9999")
    private String telefone;

    // Getters e Setters
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }
}