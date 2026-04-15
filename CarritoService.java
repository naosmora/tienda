package com.tienda.service;

import com.tienda.domain.Factura;
import com.tienda.domain.EstadoFactura;
import com.tienda.domain.Producto;
import com.tienda.domain.Usuario;
import com.tienda.domain.Venta;
import com.tienda.repository.FacturaRepository;
import com.tienda.repository.ProductoRepository;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.annotation.SessionScope;

@Service
@SessionScope
public class CarritoService {

    private final ProductoRepository productoRepository;
    private final FacturaRepository facturaRepository;
    private Map<Integer, Venta> carrito = new HashMap<>();

    public CarritoService(ProductoRepository productoRepository,
            FacturaRepository facturaRepository) {
        this.productoRepository = productoRepository;
        this.facturaRepository = facturaRepository;
    }

    public void agregarProducto(Integer idProducto, Integer cantidad) {
        var productoOpt = productoRepository.findById(idProducto);
        if (productoOpt.isPresent()) {
            Producto producto = productoOpt.get();
            if (carrito.containsKey(idProducto)) {
                Venta venta = carrito.get(idProducto);
                venta.setCantidad(venta.getCantidad() + cantidad);
            } else {
                Venta venta = new Venta();
                venta.setProducto(producto);
                venta.setCantidad(cantidad);
                venta.setPrecioHistorico(producto.getPrecio());
                carrito.put(idProducto, venta);
            }
        }
    }

    public void eliminarProducto(Integer idProducto) {
        carrito.remove(idProducto);
    }

    public List<Venta> getItems() {
        return new ArrayList<>(carrito.values());
    }

    public BigDecimal getTotal() {
        return carrito.values().stream()
                .map(v -> v.getPrecioHistorico().multiply(BigDecimal.valueOf(v.getCantidad())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public int getCantidadItems() {
        return carrito.values().stream().mapToInt(Venta::getCantidad).sum();
    }

    @Transactional
    public Factura facturar(Usuario usuario) {
        Factura factura = new Factura();
        factura.setUsuario(usuario);
        factura.setFecha(new Date());
        factura.setTotal(getTotal());
        factura.setEstado(EstadoFactura.Activa);
        factura = facturaRepository.save(factura);

        for (Venta venta : carrito.values()) {
            venta.setFactura(factura);
        }
        factura.setVentas(new ArrayList<>(carrito.values()));
        facturaRepository.save(factura);
        carrito.clear();
        return factura;
    }
}