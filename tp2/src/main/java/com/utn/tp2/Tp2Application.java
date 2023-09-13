package com.utn.tp2;

import com.utn.tp2.entidades.*;
import com.utn.tp2.repositorios.ClienteRepository;
import com.utn.tp2.repositorios.RubroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Calendar;
import java.util.List;

@SpringBootApplication
public class Tp2Application {
	@Autowired
	ClienteRepository clienteRepository;
	@Autowired
	RubroRepository rubroRepository;

	public static void main(String[] args) {
		SpringApplication.run(Tp2Application.class, args);
	}

	@Bean
	CommandLineRunner init() {
		return args -> {
			//Creamos un cliente
			Cliente cliente = Cliente.builder()
					.apellido("Perez")
					.nombre("Enzo")
					.email("enzoperez@gmail.com")
					.telefono("2610000000")
					.build();

			//Creamos un domicilio
			Domicilio domicilio = Domicilio.builder()
					.calle("Avenida SiempreViva")
					.numero("742")
					.localidad("Springfield")
					.build();
			//Creamos otro domicilio
			Domicilio domicilio2 = Domicilio.builder()
					.calle("Avenida SiempreViva")
					.numero("742")
					.localidad("Springfield")
					.build();

			//usando el metodo dentro de cliente, le asignamos/guardamos ese domicilio
			cliente.agregarDomicilio(domicilio);
			cliente.agregarDomicilio(domicilio2);

			//Creamos un producto
			Producto producto = Producto.builder()
					.tipo("insumo")
					.tiempoEstimadoCocina(15)
					.denominacion("Hamburguesa")
					.precioVenta(2000.0)
					.precioCompra(1100.0)
					.stockActual(15)
					.stockMinimo(5)
					.unidadMedida("Unidad")
					.receta("Cocinar la carne, poner condimentos en el pan, poner queso, juntar todo")//receta tentaiva de una hamburguesa
					.build();

			//Creamos un detalle del producto anterior
			DetallePedido detallePedido = DetallePedido.builder()
					.cantidad(10)
					.subtotal(20000.0)
					//le agregamos el producto creado anteriormente
					.producto(producto)
					.build();

			//Para utilizar el atributo date usamos un calendario
			Calendar calendario = Calendar.getInstance();
			calendario.set(2023, 9, 3);

			//Creamos una factura
			Factura factura = Factura.builder()
					.numero(123456789)
					.fecha(calendario.getTime())
					.descuento(10)
					.formaPago("efectivo")
					.total(20000)
					.build();

			//Para utilizar el atributo date usamos un calendario
			Calendar calendario2 = Calendar.getInstance();
			calendario2.set(2023, 2, 1);
			Pedido pedido = Pedido.builder()
					.estado("iniciado")
					.fecha(calendario2.getTime())
					.tipoEnvio("delivery")
					.total(2500)
					.factura(factura) //agregar factura ya creada al pedido
					.build();

			//Creamos un rubro
			Rubro rubro = Rubro.builder()
					.denominacion("Comida Rapida")
					.build();

			pedido.agregarDetallePedido(detallePedido); //agrego el detalle del pedido al Pedido

			//asigno el tipo de rubro al que va pertenecer mi producto
			rubro.agregaProducto(producto); // agregando ese producto a la clase rubro
			rubroRepository.save(rubro);	//lo persisto

			cliente.agregarPedido(pedido);

			clienteRepository.save(cliente);

			Cliente clienteRecuperado = clienteRepository.findById(cliente.getId()).orElse(null);
			if (clienteRecuperado != null){ //En el caso de que no se encuentre el cliente
				System.out.println("Nombre: " + clienteRecuperado.getNombre());
				System.out.println("Apellido: " + clienteRecuperado.getApellido());
				System.out.println("Tel: " + clienteRecuperado.getTelefono());
				System.out.println("mail: " + clienteRecuperado.getEmail());
				clienteRecuperado.mostrarDomicilios();

				for (Pedido pedidoRecuperado : clienteRecuperado.getPedidos()){
					if (pedidoRecuperado.getFactura() != null) {
						System.out.println("/////////////////////////////////////////////////////////////");
						System.out.println("Se encontro una factura, detalles de la factura:");
						System.out.println("numero: " + pedidoRecuperado.getFactura().getNumero());
						System.out.println("Fecha: " + pedidoRecuperado.getFactura().getFecha());
						System.out.println("descuento: " + pedidoRecuperado.getFactura().getDescuento());
						System.out.println("forma de pago: " + pedidoRecuperado.getFactura().getFormaPago());
						System.out.println("total: " + pedidoRecuperado.getFactura().getTotal());
					}
					for (DetallePedido detallePedidoRecuperado : pedidoRecuperado.getDetallePedidos()){
						System.out.println("/////////////////////////////////////////////////////////////");
						System.out.println("Se encontro detalle pedido:");
						System.out.println("Cantidad: " + detallePedidoRecuperado.getCantidad());
						System.out.println("Subtotal: " + detallePedidoRecuperado.getSubtotal());
						System.out.println("/////////////////////////////////////////////////////////////");
						System.out.println("Detalle del prodcuto");
						System.out.println("Tipo: " + detallePedidoRecuperado.getProducto().getTipo());
						System.out.println("tiempoEstimadoCocina: " + detallePedidoRecuperado.getProducto().getTiempoEstimadoCocina());
						System.out.println("denominacion: " + detallePedidoRecuperado.getProducto().getDenominacion());
						System.out.println("precioVenta: " + detallePedidoRecuperado.getProducto().getPrecioVenta());
						System.out.println("precioCompra: " + detallePedidoRecuperado.getProducto().getPrecioCompra());
						System.out.println("stockActual: " + detallePedidoRecuperado.getProducto().getStockActual());
						System.out.println("stockMinimo: " + detallePedidoRecuperado.getProducto().getStockMinimo());
						System.out.println("unidadMedida: " + detallePedidoRecuperado.getProducto().getUnidadMedida());
						System.out.println("receta: " + detallePedidoRecuperado.getProducto().getReceta());
						System.out.println("/////////////////////////////////////////////////////////////");
						System.out.println("Rubro del producto");
					}
				}
			}
			List<Rubro> rubroRecuperados = rubroRepository.findAll();
			for (Rubro rubroRecuperado : rubroRecuperados){
				System.out.println("-------------------------------------------------------------------------");
				System.out.println("Rubro encontrado: denominacion: " + rubroRecuperado.getDenominacion());
				System.out.println("PRODUCTOS ASOCIADOS: ");
				for (Producto prod : rubroRecuperado.getProductos()){
					System.out.println("Tipo: " + prod.getTipo());
					System.out.println("Denominacion: " + prod.getDenominacion());
				}
			}
		};
	}
}
