package com.example.Banco.Autenticacao;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AutenticacaoService {

    private final UsuarioRepository usuarioRepository;
    private final AuthTokenRepository authTokenRepository;

    // TTL em minutos (ajuste conforme necessidade)
    private static final long TOKEN_TTL_MINUTES = 60 * 24; // 24 horas

    public AutenticacaoService(UsuarioRepository usuarioRepository,
                               AuthTokenRepository authTokenRepository) {
        this.usuarioRepository = usuarioRepository;
        this.authTokenRepository = authTokenRepository;
    }

    /**
     * Cadastrar usuário: salva senha hasheada no banco.
     */
    @Transactional
    public String cadastrarUsuario(Usuario usuario) {
        if (usuario == null || usuario.getEmail() == null || usuario.getSenha() == null) {
            throw new RuntimeException("Email e senha são obrigatórios.");
        }

        if (usuarioRepository.existsByEmail(usuario.getEmail())) {
            throw new RuntimeException("Já existe usuário cadastrado com este email.");
        }

        String hashed = BCrypt.hashpw(usuario.getSenha(), BCrypt.gensalt());
        usuario.setSenha(hashed);
        usuarioRepository.save(usuario);
        return "Usuário cadastrado com sucesso!";
    }

    /**
     * Lista usuários sem expor senha (remove hash antes de retornar).
     * Em produção, prefira DTOs.
     */
    @Transactional(readOnly = true)
    public List<Usuario> listarUsuarios() {
        List<Usuario> all = usuarioRepository.findAll();
        // remove senha para não expor hash
        return all.stream().map(u -> {
            Usuario safe = new Usuario();
            safe.setId(u.getId());
            safe.setEmail(u.getEmail());
            // NÃO setSenha
            return safe;
        }).collect(Collectors.toList());
    }

    /**
     * Login: compara senha com hash e cria token persistido.
     */
    @Transactional
    public String login(Usuario dados) {
        var maybe = usuarioRepository.findByEmail(dados.getEmail());
        if (maybe.isEmpty()) {
            throw new RuntimeException("Usuário não encontrado.");
        }

        Usuario salvo = maybe.get();
        if (!BCrypt.checkpw(dados.getSenha(), salvo.getSenha())) {
            throw new RuntimeException("Usuário ou senha inválidos.");
        }

        String token = UUID.randomUUID().toString();
        Instant now = Instant.now();
        Instant expires = now.plus(TOKEN_TTL_MINUTES, ChronoUnit.MINUTES);

        AuthToken authToken = new AuthToken(token, salvo, now, expires);
        authTokenRepository.save(authToken);
        return token;
    }

    /**
     * Validação de token: verifica existência e expiração.
     */
    @Transactional(readOnly = true)
    public void validarToken(String token) {
        var maybe = authTokenRepository.findByToken(token);
        if (maybe.isEmpty()) {
            throw new RuntimeException("Token inválido.");
        }
        AuthToken at = maybe.get();
        if (at.getExpiresAt() != null && Instant.now().isAfter(at.getExpiresAt())) {
            throw new RuntimeException("Token expirado.");
        }
    }

    /**
     * Logout / revogação do token.
     */
    @Transactional
    public void logout(String token) {
        authTokenRepository.deleteByToken(token);
    }
}
