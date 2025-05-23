package com.proyecto.centros.mtc.app.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.proyecto.centros.mtc.app.entity.UsuarioEntity;
import com.proyecto.centros.mtc.app.repository.UsuarioRepository;
import com.proyecto.centros.mtc.app.util.AppClasicEncrypt;

import java.util.Base64;
import java.util.Optional;

@Service
public class AutorizadorImpl implements Autorizador{

    @Value("${seg.user}")
    private String segUser;

    @Value("${seg.pass}")
    private String segPass;

    private final UsuarioRepository usuarioRepository;

    public AutorizadorImpl(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public boolean validaUsuario(String autorizacion){

        String credenciales=autorizacion.substring(6);

        byte[] decodedBytes = Base64.getDecoder().decode(credenciales);
        String decode = new String(decodedBytes);

        String []argCred=decode.split(":");

        String user=argCred[0];
        String password=argCred[1];

        Optional<UsuarioEntity> optUsuarioEntity=usuarioRepository.validar(user,AppClasicEncrypt.encrypt(password));
        return optUsuarioEntity.isPresent();
    }
}
