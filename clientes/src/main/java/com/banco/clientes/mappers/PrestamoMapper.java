package com.banco.clientes.mappers;

import com.banco.clientes.dto.ClienteDTO;
import com.banco.clientes.dto.PrestamoDTO;
import com.banco.clientes.model.Cliente;
import com.banco.clientes.model.Prestamo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface PrestamoMapper {
    PrestamoMapper instancia= Mappers.getMapper(PrestamoMapper.class);

    Prestamo prestamoDTOAPrestamo(PrestamoDTO prestamoDTO);
    PrestamoDTO prestamoAPrestamoDTO(Prestamo prestamo);
    List<PrestamoDTO> listaPrestamoAListaPrestamoDTO(List<Prestamo> listaPrestamos);
}
