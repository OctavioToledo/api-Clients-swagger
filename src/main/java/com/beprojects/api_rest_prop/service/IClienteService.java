package com.beprojects.api_rest_prop.service;

import com.beprojects.api_rest_prop.model.dto.ClienteDto;
import com.beprojects.api_rest_prop.model.entity.Cliente;

import java.util.List;

public interface IClienteService {
    List<Cliente> listAll();

    Cliente save(ClienteDto cliente);
    Cliente findById(Long id);

    void delete(Cliente cliente);

    boolean existsById(Long id);


}
