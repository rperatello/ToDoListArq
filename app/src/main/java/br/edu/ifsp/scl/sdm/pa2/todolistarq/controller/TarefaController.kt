package br.edu.ifsp.scl.sdm.pa2.todolistarq.controller

import android.os.AsyncTask
import android.os.Bundle
import androidx.fragment.app.setFragmentResult
import androidx.room.Room
import br.edu.ifsp.scl.sdm.pa2.todolistarq.model.database.ToDoListArqDatabase
import br.edu.ifsp.scl.sdm.pa2.todolistarq.model.entity.Tarefa
import br.edu.ifsp.scl.sdm.pa2.todolistarq.view.BaseFragment
import br.edu.ifsp.scl.sdm.pa2.todolistarq.view.BaseFragment.Constantes.TAREFA_REQUEST_KEY
import br.edu.ifsp.scl.sdm.pa2.todolistarq.view.TarefaFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class TarefaController(private val tarefaFragment: TarefaFragment) {
    private val database: ToDoListArqDatabase

    init {
        database = Room.databaseBuilder(
            tarefaFragment.requireContext(),
            ToDoListArqDatabase::class.java,
            ToDoListArqDatabase.Constantes.DB_NAME
        ).build()
    }

    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    fun atualizaTarefa(tarefa: Tarefa) {
        coroutineScope.launch {
            database.getTarefaDao().atualizarTarefa(tarefa)
            tarefaFragment.retornaTarefa(tarefa)
        }
    }

    fun insereTarefa(tarefa: Tarefa) {
        coroutineScope.launch {
            database.getTarefaDao().inserirTarefa(tarefa)
            tarefaFragment.retornaTarefa(
                Tarefa(
                    tarefa.id,
                    tarefa.nome,
                    tarefa.realizada
                )
            )
        }
    }
}