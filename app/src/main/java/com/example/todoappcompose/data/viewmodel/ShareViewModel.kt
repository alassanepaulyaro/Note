package com.example.todoappcompose.data.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoappcompose.data.models.Priority
import com.example.todoappcompose.data.models.ToDoTask
import com.example.todoappcompose.data.repository.DataStoreRepository
import com.example.todoappcompose.data.repository.ToDoRepository
import com.example.todoappcompose.utils.Action
import com.example.todoappcompose.utils.Constants.MAX_TITLE_LENGTH
import com.example.todoappcompose.utils.RequestState
import com.example.todoappcompose.utils.SearchAppBarState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Share ViewModel
 */
@HiltViewModel
class ShareViewModel @Inject constructor(
    private val repository: ToDoRepository,
    private val dataSoreRepository : DataStoreRepository
) : ViewModel() {
    val action : MutableState<Action> = mutableStateOf(Action.NO_ACTION)

    val id : MutableState<Int> = mutableStateOf(0)
    val title : MutableState<String> = mutableStateOf("")
    val description : MutableState<String> = mutableStateOf("")
    val priority : MutableState<Priority> = mutableStateOf(Priority.LOW)

    val searchAppBarState: MutableState<SearchAppBarState> = mutableStateOf(SearchAppBarState.CLOSED)
    val searchTextState: MutableState<String> = mutableStateOf("")

    private val _allTasks = MutableStateFlow<RequestState<List<ToDoTask>>>(RequestState.Idle)
    val allTasks: StateFlow<RequestState<List<ToDoTask>>> = _allTasks

    private val _searchedTasks= MutableStateFlow<RequestState<List<ToDoTask>>>(RequestState.Idle)
    val searchedTasks: StateFlow<RequestState<List<ToDoTask>>> = _searchedTasks

    val lowPriorityTask : StateFlow<List<ToDoTask>> =
        repository.sortByLowPriority.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        emptyList()
    )

    val highPriorityTask : StateFlow<List<ToDoTask>> =
        repository.sortByHighPriority.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        emptyList()
    )

    private val _sortState= MutableStateFlow<RequestState<Priority>>(RequestState.Idle)
    val sortState: StateFlow<RequestState<Priority>> = _sortState

    /**
     * Read Sort State
     */
    fun readSortState() {
        _sortState.value = RequestState.Loading
        try {
            viewModelScope.launch {
                dataSoreRepository.readSortState
                    .map { Priority.valueOf(it) }
                    .collect {
                    _sortState.value = RequestState.Success(it)
                }
            }
        } catch (e : Exception) {
            _sortState.value =  RequestState.Error(e)
        }
    }

    /**
     * Persist Sort State
     */
    fun persistSortState(priority: Priority) {
        viewModelScope.launch (Dispatchers.IO) {
            dataSoreRepository.persistSortState(priority = priority)
        }
    }

    /**
     * Gel All tasks
     */
    fun getAllTasks() {
        _allTasks.value = RequestState.Loading
        try {
            viewModelScope.launch {
                repository.getAllTasks.collect {
                    _allTasks.value = RequestState.Success(it)
                }
            }
        } catch (e : Exception) {
            _allTasks.value =  RequestState.Error(e)
        }
    }

    /**
     * Search tasks
     */
    fun searchDatabase(searchQuery : String) {
        _searchedTasks.value = RequestState.Loading
        try {
            viewModelScope.launch {
                repository.searchDatabase(searchQuery = "%$searchQuery%")
                    .collect{ searchedTasks ->
                        _searchedTasks.value = RequestState.Success(searchedTasks)
                    }
            }
        } catch (e : Exception) {
            _searchedTasks.value =  RequestState.Error(e)
        }
        searchAppBarState.value = SearchAppBarState.TRIGGERED
    }

    private val _selectedTask: MutableStateFlow<ToDoTask?> = MutableStateFlow(null)
    val selectedTask : StateFlow<ToDoTask?> = _selectedTask

    /**
     * Get selected task
     */
    fun getSelectedTask(taskId: Int) {
        viewModelScope.launch (Dispatchers.IO) {
            repository.getSelectedTask(taskId).collect { task  ->
                _selectedTask.value = task
            }
        }
    }

    /**
     * Add task
     */
    private fun addTask() {
        viewModelScope.launch (Dispatchers.IO) {
            val toDoTask = ToDoTask(
                title = title.value,
                description = description.value,
                priority = priority.value
            )
            repository.addTask(toDoTask = toDoTask)
        }
        searchAppBarState.value = SearchAppBarState.CLOSED
    }

    /**
     * Update Task
     */
    private fun updateTask() {
        viewModelScope.launch (Dispatchers.IO) {
            val toDoTask = ToDoTask(
                id = id.value,
                title = title.value,
                description = description.value,
                priority = priority.value
            )
            repository.updateTask(toDoTask = toDoTask)
        }
    }

    /**
     * Delete Task
     */
    private fun deleteTask () {
        viewModelScope.launch (Dispatchers.IO) {
            val toDoTask = ToDoTask(
                id = id.value,
                title = title.value,
                description = description.value,
                priority = priority.value
            )
            repository.deleteTask(toDoTask = toDoTask)
        }
    }

    /**
     * Delete All Tasks
     */
    private fun deleteAllTasks() {
        viewModelScope.launch (Dispatchers.IO) {
            repository.deleteAllTasks()
        }
    }

    /**
     * Handle Database Actions CRUD
     */
    fun handleDatabaseActions(action: Action) {
        when(action) {
            Action.ADD -> {
                addTask()
            }
            Action.UPDATE -> {
                updateTask()
            }
            Action.DELETE -> {
                deleteTask()
            }
            Action.DELETE_ALL -> {
                deleteAllTasks()
            }
            Action.UNDO -> {
                addTask()
            }
            else -> {

            }
        }
        this.action.value = Action.NO_ACTION
    }

    /**
     *  update task field
     */
    fun updateTaskFields(selectedTask: ToDoTask?) {
        if (selectedTask != null) {
            id.value = selectedTask.id
            title.value = selectedTask.title
            description.value = selectedTask.description
            priority.value = selectedTask.priority
        } else {
            id.value = 0
            title.value = ""
            description.value = ""
            priority.value = Priority.LOW
        }
    }

    /**
     *  title length limit
     */
    fun updateTitle(newTitle : String) {
        if (newTitle.length < MAX_TITLE_LENGTH) {
            title.value = newTitle
        }
    }

    /**
     * check validate field
     */
    fun validateField(): Boolean {
        return title.value.isNotEmpty() &&  description.value.isNotEmpty()
    }
}