/*package com.baufest.Libreria.service;

import com.baufest.Libreria.models.CuentaCorriente;
import com.baufest.Libreria.repository.CuentaCorrienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CuentaCorrienteService {

    @Autowired
    ClienteService clienteService;

    @Autowired
    CuentaCorrienteRepository cuentaCorrienteRepository;

    public CuentaCorriente addCuentaCorriente(CuentaCorriente cuentaCorriente){
        return cuentaCorrienteRepository.save(cuentaCorriente);
    }

    public List<CuentaCorriente> findAll(){
        List<CuentaCorriente> cuentasCorrientes = cuentaCorrienteRepository.findAll();
        return cuentasCorrientes;
    }

    public int deleteById (Optional<CuentaCorriente> cuentaEncontrada) {
        try {
            cuentaCorrienteRepository.delete(cuentaEncontrada.get());
            return 1;
        } catch (EmptyResultDataAccessException e) {
            System.out.println("No se encontró la cuenta");
            return 0;
        }

    }

    public Optional<CuentaCorriente> getCuentaCorrienteById(Integer id){
        return cuentaCorrienteRepository.findById(id);

    }

    /*
    public Optional<CuentaCorriente> getCuentaCorrienteByClienteId(Integer id){
        return cuentaCorrienteRepository.findByClienteId(id);
    }


  public Optional<CuentaCorriente> updateCuentaCorriente (Integer id, CuentaCorriente cuentaCorriente){
        CuentaCorriente cuentaToUpdate = cuentaCorrienteRepository.findById(id).get();
        cuentaToUpdate.setId(id);
        cuentaCorrienteRepository.save(cuentaToUpdate);
        return cuentaCorrienteRepository.findById(id);
    }



}
*/