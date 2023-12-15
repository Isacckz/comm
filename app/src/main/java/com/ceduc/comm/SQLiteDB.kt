package com.ceduc.comm

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper


class DatabaseHelper(context: Context?) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase) {
        // Crear la tabla de productos
        val CREATE_TABLE_PRODUCTOS = ("CREATE TABLE " + TABLE_PRODUCTOS + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_CODIGO + " TEXT,"
                + COLUMN_DESCRIPCION + " TEXT,"
                + COLUMN_PRECIO + " REAL)")
        db.execSQL(CREATE_TABLE_PRODUCTOS)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Manejar actualizaciones de la base de datos si es necesario
    }


    fun agregarProducto(codigo: String?, descripcion: String?, precio: Double) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COLUMN_CODIGO, codigo)
        values.put(COLUMN_DESCRIPCION, descripcion)
        values.put(COLUMN_PRECIO, precio)
        db.insert(TABLE_PRODUCTOS, null, values)
        db.close()
    }

    // Método para obtener todos los productos de la base de datos
    fun obtenerTodosProductos(): List<Producto> {
        val productos: MutableList<Producto> = ArrayList<Producto>()
        val SELECT_ALL_PRODUCTOS = "SELECT * FROM " + TABLE_PRODUCTOS
        val db = this.writableDatabase
        val cursor = db.rawQuery(SELECT_ALL_PRODUCTOS, null)
        if (cursor.moveToFirst()) {
            do {
                val producto = Producto()
                producto.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)))
                producto.setCodigo(cursor.getString(cursor.getColumnIndex(COLUMN_CODIGO)))
                producto.setDescripcion(cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPCION)))
                producto.setPrecio(cursor.getDouble(cursor.getColumnIndex(COLUMN_PRECIO)))
                productos.add(producto)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return productos
    }


    fun borrarProducto(productoId: Int) {
        val db = this.writableDatabase
        db.delete(TABLE_PRODUCTOS, COLUMN_ID + " = ?", arrayOf(productoId.toString()))
        db.close()
    }


    fun actualizarProducto(
        productoId: Int,
        nuevoCodigo: String?,
        nuevaDescripcion: String?,
        nuevoPrecio: Double
    ) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COLUMN_CODIGO, nuevoCodigo)
        values.put(COLUMN_DESCRIPCION, nuevaDescripcion)
        values.put(COLUMN_PRECIO, nuevoPrecio)
        db.update(TABLE_PRODUCTOS, values, COLUMN_ID + " = ?", arrayOf(productoId.toString()))
        db.close()
    }

    companion object {
        private const val DATABASE_NAME = "mi_base_de_datos"
        private const val DATABASE_VERSION = 1
        private const val TABLE_PRODUCTOS = "productos"
        private const val COLUMN_ID = "id"
        private const val COLUMN_CODIGO = "codigo"
        private const val COLUMN_DESCRIPCION = "descripcion"
        private const val COLUMN_PRECIO = "precio"
    }
}

