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

    fun atualizaTarefa(tarefa: Tarefa) {
        object: AsyncTask<Tarefa, Unit, Unit>(){
            override fun doInBackground(vararg params: Tarefa?) {
                params[0]?.let { tarefaEditada ->
                    database.getTarefaDao().atualizarTarefa(tarefa)
                }
            }

            override fun onPostExecute(result: Unit?) {
                super.onPostExecute(result)
                tarefaFragment.retornaTarefa(tarefa)
            }
        }.execute(tarefa)
    }

    fun insereTarefa(tarefa: Tarefa) {
        object: AsyncTask<Tarefa, Unit, Long>(){
            override fun doInBackground(vararg params: Tarefa?): Long {
                params[0]?.let { novaTarefa ->
                    return database.getTarefaDao().inserirTarefa(tarefa)
                }
                return tarefaFragment.ID_INEXISTENTE
            }

            override fun onPostExecute(result: Long?) {
                super.onPostExecute(result)
                result?.let { novoId ->
                    tarefaFragment.retornaTarefa(
                        Tarefa(
                            novoId.toInt(),
                            tarefa.nome,
                            tarefa.realizada
                        )
                    )
                }
            }
        }.execute(tarefa)
    }

}