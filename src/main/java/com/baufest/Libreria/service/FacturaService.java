package com.baufest.Libreria.service;

import com.baufest.Libreria.models.*;
import com.baufest.Libreria.repository.CompraRepository;
import com.baufest.Libreria.repository.FacturaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FacturaService {

    @Autowired
    FacturaRepository facturaRepository;

    @Autowired
    CompraRepository compraRepository;


    @Autowired
    ClienteService clienteService;

    @Autowired
    DescuentoService descuentoService;

    @Autowired
    ProductoService productoService;

    public ResponseEntity<Factura> getFactura(Integer id) {
        return facturaRepository.getById(Factura.class,id);
    }

    public ResponseEntity<List<Factura>> getAllFactura() {
        return facturaRepository.getAll(Factura.class);
    }

    public ResponseEntity<Factura> updateFactura(Integer id, Factura factura) {
        Factura facturaUpdateable = getFactura(id).getBody();
        this.calcularMontoTotal(facturaUpdateable);
        return saveFactura(facturaUpdateable);
    }

    public ResponseEntity<Factura> saveFactura(Factura facturaVirtual) {
        facturaVirtual.cargarCliente(clienteService);
        facturaVirtual.cargarDescuentos(descuentoService);
        List<Compra> compras = facturaVirtual.getCompras();
        for (int i = 0; i < compras.size(); i++) {
            compras.get(i).cargarProducto(productoService);
        }
        this.calcularMontoTotal(facturaVirtual);
        Factura factura = (Factura) facturaRepository.save(facturaVirtual).getBody();
        for (int i = 0; i < compras.size(); i++) {
            compras.get(i).cargarProducto(productoService);
            compras.get(i).setFactura(factura);
            compraRepository.save((compras.get(i)));
        }
        return facturaRepository.save(factura);
    }

    public ResponseEntity<?> deleteFactura(Integer id) {
        return facturaRepository.delete(Factura.class,id);
    }

    public ResponseEntity<Factura> calcularMontoTotal(Factura factura) {
        double montoTotal = 0;
        List<Compra> compras = factura.getCompras();
        int cantCompras = compras.size();

        for (int i = 0; i < cantCompras; i++) {
            montoTotal += compras.get(i).total();
        }
        factura.setMontoTotal(montoTotal);
        factura.aplicarDescuentos();
        return ResponseEntity.ok(factura);
    }
    private void aplicarDescuentos(Factura factura) {
        factura.aplicarDescuentos();
    }
    public ResponseEntity<Factura> pagarFactura(Integer id) {
        Factura factura = this.getFactura(id).getBody();
        factura.pagar();
        return facturaRepository.save(factura);
    }
}
