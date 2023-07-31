package com.example.todoapp.ui.TodoList

import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoapp.data.Todo
import com.example.todoapp.data.TodoRepo
import com.example.todoapp.util.Routes
import com.example.todoapp.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TodoListViewModel @Inject constructor(
    private val repo: TodoRepo
) : ViewModel() {


    val todos = repo.getTodos()
    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()
    private var deletedTodo: Todo? = null


    fun onEvent(event: TodoListEvent) {
        when (event) {
            is TodoListEvent.OnTodoClick -> {

                sendUiEvent(UiEvent.Navigate(Routes.ADD_EDIT_TODO))

            }
            is TodoListEvent.OnAddTodoClick -> {
                sendUiEvent(UiEvent.Navigate(Routes.ADD_EDIT_TODO + "?todoId=${event.todo.id}"))
            }
            is TodoListEvent.OnUndoDeleteClick -> {

                deletedTodo?.let { todo ->
                    viewModelScope.launch {
                        repo.insertTodo(todo)
                    }
                }
            }
            is TodoListEvent.OnDeleteTodo -> {

                viewModelScope.launch {
                    deletedTodo = event.todo

                    repo.deleteTodo(event.todo)

                    sendUiEvent(UiEvent.ShowSnackbar(message = "Todo Deleted", action = "Undo"))
                }

            }
            is TodoListEvent.OnDoneChange -> {
                viewModelScope.launch {
                    repo.insertTodo(event.todo.copy(isDone = event.isDone))
                }
            }
        }
    }

    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}