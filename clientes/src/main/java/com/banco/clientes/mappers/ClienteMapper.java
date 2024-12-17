package com.banco.clientes.mappers;

import com.banco.clientes.dto.ClienteDTO;
import com.banco.clientes.model.Cliente;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ClienteMapper {
    ClienteMapper instancia= Mappers.getMapper(ClienteMapper.class);

    Cliente clienteDTOACliente(ClienteDTO clienteDTO);
    ClienteDTO clienteAClienteDTO(Cliente cliente);
    List<ClienteDTO> listaClienteAListaClienteDTO(List<Cliente> listaClientes);
}
