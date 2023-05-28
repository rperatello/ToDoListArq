package br.edu.ifsp.scl.sdm.pa2.todolistarq.controller

import android.os.AsyncTask
import androidx.room.Room
import br.edu.ifsp.scl.sdm.pa2.todolistarq.model.database.ToDoListArqDatabase
import br.edu.ifsp.scl.sdm.pa2.todolistarq.model.entity.Tarefa
import br.edu.ifsp.scl.sdm.pa2.todolistarq.view.ListaTarefasFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ListaTarefasController(private val listaTarefasFragment: ListaTarefasFragment) {
    private val database: ToDoListArqDatabase
    init {
        database = Room.databaseBuilder(
            listaTarefasFragment.requireContext(),
            ToDoListArqDatabase::class.java,
            ToDoListArqDatabase.Constantes.DB_NAME
        ).build()
    }

    fun buscarTarefas() {
        GlobalScope.launch {
            val listaTarefas = database.getTarefaDao().recuperarTarefas()
            listaTarefasFragment.atualizarListaTarefas(listaTarefas.toMutableList())
        }
    }

    fun removerTarefa(tarefa: Tarefa){
        GlobalScope.launch {
            database.getTarefaDao().removerTarefa(tarefa)
        }
    }
}