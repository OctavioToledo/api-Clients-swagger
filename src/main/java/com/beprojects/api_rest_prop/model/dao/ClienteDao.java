package com.beprojects.api_rest_prop.model.dao;

import com.beprojects.api_rest_prop.model.entity.Cliente;
import org.springframework.data.repository.CrudRepository;

public interface ClienteDao extends CrudRepository<Cliente, Long> {


}
