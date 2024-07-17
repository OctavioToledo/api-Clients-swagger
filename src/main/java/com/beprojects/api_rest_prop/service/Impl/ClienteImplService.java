package com.beprojects.api_rest_prop.service.Impl;

import com.beprojects.api_rest_prop.model.dao.ClienteDao;
import com.beprojects.api_rest_prop.model.dto.ClienteDto;
import com.beprojects.api_rest_prop.model.entity.Cliente;
import com.beprojects.api_rest_prop.service.IClienteService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ClienteImplService implements IClienteService {

    private final ClienteDao clienteDao;

    public ClienteImplService(ClienteDao clienteDao) {
        this.clienteDao = clienteDao;
    }

    @Override
    public List<Cliente> listAll() {
        return (List)clienteDao.findAll();
    }

    @Transactional
    @Override
    public Cliente save(ClienteDto clienteDto) {
        Cliente cliente = Cliente.builder()
                .idCliente(clienteDto.getIdCliente())
                .nombre(clienteDto.getNombre())
                .apellido(clienteDto.getApellido())
                .correo(clienteDto.getCorreo())
                .fechaRegistro(clienteDto.getFechaRegistro())
                .build();
        return clienteDao.save(cliente);
    }

    @Transactional(readOnly = true)
    @Override
    public Cliente findById(Long id) {
        return clienteDao.findById(id).orElse(null);
    }
    @Transactional
    @Override
    public void delete(Cliente cliente) {
        clienteDao.delete(cliente);
    }

    @Override
    public boolean existsById(Long id) {
        return clienteDao.existsById(id);
    }
}
