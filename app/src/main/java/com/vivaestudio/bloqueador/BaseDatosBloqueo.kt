// Archivo: app/src/main/java/com/vivaestudio/bloqueador/db/BaseDatosBloqueo.kt
package com.vivaestudio.bloqueador.db

import androidx.room.*
import android.content.Context

@Entity(tableName = "rangos_bloqueados")
data class RangoBloqueado(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val numeroInicio: Long,
    val numeroFin: Long
)

@Dao
interface RangoDao {
    @Insert
    fun insertarRango(rango: RangoBloqueado)

    @Query("SELECT * FROM rangos_bloqueados")
    fun obtenerTodos(): List<RangoBloqueado>
}

@Database(entities = [RangoBloqueado::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun rangoDao(): RangoDao

    companion object {
        @Volatile private var INSTANCE: AppDatabase? = null
        fun obtenerBaseDatos(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "db_filtro_privado"
                ).allowMainThreadQueries().build() // Simplificado para este ejemplo
                INSTANCE = instance
                instance
            }
        }
    }
}
