package com.desafio.firstdecision.service;

import com.desafio.firstdecision.domain.Usuario;
import com.desafio.firstdecision.dto.UsuarioDTO;
import com.desafio.firstdecision.exception.SenhaException;
import com.desafio.firstdecision.exception.UsuarioInexistenteException;
import com.desafio.firstdecision.mapper.UsuarioMapper;
import com.desafio.firstdecision.repository.UsuarioRepository;
import com.desafio.firstdecision.util.ConstanteUtil;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    private UsuarioMapper usuarioMapper;
    private UsuarioRepository usuarioRepository;

    @Autowired
    protected UsuarioService(UsuarioMapper usuarioMapper, UsuarioRepository usuarioRepository) {
        this.usuarioMapper = usuarioMapper;
        this.usuarioRepository = usuarioRepository;
    }

    public Page<UsuarioDTO> listarUsuarios(Pageable pageable) {
        return this.usuarioRepository.findAll(pageable).map(this.usuarioMapper::converterEntidadeParaDTO);
    }

    @Transactional
    public UsuarioDTO salvarUsuario(UsuarioDTO usuarioDTO) {
        validarSenhas(usuarioDTO);
        var usuario = this.usuarioMapper.converterDTOParaEntidade(usuarioDTO);
        return this.usuarioMapper.converterEntidadeParaDTO(usuarioRepository.save(usuario));
    }

    @Transactional
    public void atualizarUsuario(Long id, UsuarioDTO usuarioDTO) {
        Usuario usuarioExistente = this.buscarUsuarioPorId(id);

        validarSenhas(usuarioDTO);

        usuarioExistente.setNome(usuarioDTO.getNome());
        usuarioExistente.setEmail(usuarioDTO.getEmail());
        usuarioExistente.setSenha(usuarioDTO.getSenha());

        var usuario = this.usuarioMapper.converterDTOParaEntidade(usuarioDTO);
        this.usuarioMapper.converterEntidadeParaDTO(usuarioRepository.save(usuario));
    }


    @Transactional
    public void deletarUsuario(Long id) {
        Usuario usuarioExistente = this.buscarUsuarioPorId(id);
        usuarioRepository.deleteById(usuarioExistente.getId());
    }

    private Usuario buscarUsuarioPorId(Long id) throws UsuarioInexistenteException {
        return this.usuarioRepository.findById(id)
                .orElseThrow(() -> new UsuarioInexistenteException(String.format(ConstanteUtil.MENSAGEM_USUARIO_INEXISTENTE, id)));
    }

    private void validarSenhas(UsuarioDTO usuarioDTO) {
        if (!usuarioDTO.getSenha().equals(usuarioDTO.getConfirmaSenha())) {
            throw new SenhaException(ConstanteUtil.MENSAGEM_SENHA_ERRO);
        }

    }
}
