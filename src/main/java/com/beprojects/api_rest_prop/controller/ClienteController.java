package com.beprojects.api_rest_prop.controller;
import com.beprojects.api_rest_prop.model.dto.ClienteDto;
import com.beprojects.api_rest_prop.model.entity.Cliente;
import com.beprojects.api_rest_prop.model.payload.MessageResponse;
import com.beprojects.api_rest_prop.service.IClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1")
public class ClienteController {
    @Autowired
    private IClienteService iClienteService;

    @GetMapping("/clientes")
    public ResponseEntity<?> showAll(){
        List<Cliente> getList = iClienteService.listAll();
        if (getList == null){
            return new ResponseEntity<>(MessageResponse.builder()
                    .message("No hay registros")
                    .object(null)
                    .build(), HttpStatus.OK);
        }
        return new ResponseEntity<>(
                MessageResponse.builder()
                        .message("")
                        .object(getList)
                        .build(), HttpStatus.OK);
    }

    @PostMapping("/cliente")
    public ResponseEntity<?> create(@RequestBody ClienteDto clienteDto){
        Cliente clienteSave = null;
        try {
            clienteSave = iClienteService.save(clienteDto);
            clienteDto = ClienteDto.builder()
                    .idCliente(clienteSave.getIdCliente())
                    .nombre(clienteSave.getNombre())
                    .apellido(clienteSave.getApellido())
                    .correo(clienteSave.getCorreo())
                    .fechaRegistro(clienteSave.getFechaRegistro())
                    .build();

            return new ResponseEntity<>( MessageResponse.builder()
                    .message("Guardado Correctamente")
                    .object(clienteDto)
                    .build(), HttpStatus.CREATED);

        }catch (DataAccessException exDt){
            return new ResponseEntity<>(MessageResponse.builder()
                    .message(exDt.getMessage())
                    .object(null)
                    .build(), HttpStatus.METHOD_NOT_ALLOWED);
    }
    }
    @PutMapping("/cliente/{id}")
    public ResponseEntity<?> update(@RequestBody ClienteDto clienteDto, @PathVariable Long id){
        Cliente clienteUpdate = null;
        try {
            if (iClienteService.existsById(id)){
                clienteDto.setIdCliente(id);
                clienteUpdate = iClienteService.save(clienteDto);
                clienteDto = ClienteDto.builder()
                        .idCliente(clienteUpdate.getIdCliente())
                        .nombre(clienteUpdate.getNombre())
                        .apellido(clienteUpdate.getApellido())
                        .correo(clienteUpdate.getCorreo())
                        .fechaRegistro(clienteUpdate.getFechaRegistro())
                        .build();
                return new ResponseEntity<>( MessageResponse.builder()
                        .message("Guardado Correctamente")
                        .object(clienteDto)
                        .build(), HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>(MessageResponse.builder()
                        .message("Usuario no encontrado")
                        .object(null)
                        .build(), HttpStatus.NOT_FOUND);
            }
        }catch (DataAccessException exDt){
            return new ResponseEntity<>(
                    MessageResponse.builder()
                            .message(exDt.getMessage())
                            .object(null)
                            .build()
                    , HttpStatus.METHOD_NOT_ALLOWED);
    }
    }
    @DeleteMapping("/cliente/{id}")
    public ResponseEntity <?> delete(@PathVariable Long id){
      try {
          Cliente clienteDelete = iClienteService.findById(id);
          iClienteService.delete(clienteDelete);
          return new ResponseEntity<>(clienteDelete, HttpStatus.NO_CONTENT);
      }catch (DataAccessException exDt){
          return new ResponseEntity<>(MessageResponse.builder()
                  .message(exDt.getMessage())
                  .object(null)
                  .build(), HttpStatus.METHOD_NOT_ALLOWED);
      }
    }
    @GetMapping("/cliente/{id}")
    public ResponseEntity<?> showById(@PathVariable Long id){
        Cliente cliente =  iClienteService.findById(id);
        if (cliente == null){
            return new ResponseEntity<>(MessageResponse.builder()
                    .message("El registro que busca no se encuentra")
                    .object(null)
                    .build(), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(
                MessageResponse.builder()
                .message("")
                .object(ClienteDto.builder()
                 .idCliente(cliente.getIdCliente())
                  .nombre(cliente.getNombre())
                  .apellido(cliente.getApellido())
                   .correo(cliente.getCorreo())
                  .fechaRegistro(cliente.getFechaRegistro())
                   .build())
                        .build(), HttpStatus.OK);
    }
}
