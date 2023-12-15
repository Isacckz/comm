package com.ceduc.comm

import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    private var catalogoProductos: List<Carrito>? = null
    private var productosEnCarrito: MutableList<Producto>? = null
    private var layoutCatalogo: LinearLayout? = null
    private var layoutCarrito: LinearLayout? = null
    private var textViewCarrito: TextView? = null
    private var databaseHelper: DatabaseHelper? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        databaseHelper = DatabaseHelper(this)
        catalogoProductos = obtenerCatalogoProductos()
        productosEnCarrito = obtenerProductosEnCarrito()

        // Configurar el catálogo de productos
        layoutCatalogo = findViewById<LinearLayout>(R.id.layoutCatalogo)
        configurarCatalogo()

        // Configurar el carrito de compras
        layoutCarrito = findViewById<LinearLayout>(R.id.layoutCarrito)
        textViewCarrito = findViewById<TextView>(R.id.textViewCarrito)
        configurarCarrito()

        // Configurar el botón de mostrar catálogo
        val botonMostrarCatalogo = findViewById<Button>(R.id.botonMostrarCatalogo)
        botonMostrarCatalogo.setOnClickListener { mostrarCatalogo() }

        // Configurar el botón de agregar producto al carrito
        val botonAgregarCarrito = findViewById<Button>(R.id.botonAgregarCarrito)
        botonAgregarCarrito.setOnClickListener { abrirFormulario() }
    }

    private fun obtenerCatalogoProductos(): List<Producto> {
        // Puedes cargar productos desde la base de datos o utilizar datos ficticios aquí.
        // En este ejemplo, simplemente devolvemos una lista vacía.
        return databaseHelper.obtenerTodosProductos()
    }

    private fun obtenerProductosEnCarrito(): MutableList<Producto> {
        // Puedes cargar productos desde la base de datos o utilizar datos ficticios aquí.
        // En este ejemplo, simplemente devolvemos una lista vacía.
        return databaseHelper.obtenerTodosProductos()
    }

    private fun configurarCatalogo() {
        // Aquí puedes agregar botones al layoutCatalogo y configurar su funcionalidad
    }

    private fun abrirFormulario() {
        // Aquí puedes abrir el formulario para gestionar productos.
        // Puedes iniciar otra actividad (FormularioActivity) o mostrar un diálogo.
        // En este ejemplo, simplemente agrego el producto al carrito directamente.
        agregarAlCarrito(Producto("001", "Producto de ejemplo", 19.99))
        Toast.makeText(this, "Producto agregado al carrito", Toast.LENGTH_SHORT).show()
    }

    private fun agregarAlCarrito(producto: Producto) {
        // Agregar el producto al carrito y a la base de datos
        databaseHelper.agregarProducto(
            producto.getCodigo(),
            producto.getDescripcion(),
            producto.getPrecio()
        )
        productosEnCarrito!!.add(producto)

        // Actualizar la visualización del carrito
        actualizarCarrito()
    }

    private fun configurarCarrito() {
        // Aquí puedes configurar la interfaz de usuario relacionada con el carrito
    }

    private fun actualizarCarrito() {
        // Actualizar la visualización del carrito
        if (productosEnCarrito!!.isEmpty()) {
            textViewCarrito!!.text = "Carrito vacío"
        } else {
            val carritoTexto = StringBuilder("Carrito:\n")
            for (producto in productosEnCarrito) {
                carritoTexto.append(producto.getDescripcion()).append("\n")
            }
            textViewCarrito!!.text = carritoTexto.toString()
        }
    }

    private fun mostrarCatalogo() {
        // Puedes implementar la lógica para mostrar el catálogo aquí.
        Toast.makeText(this, "Mostrar catálogo aquí", Toast.LENGTH_SHORT).show()
    }
}





