package com.tesla.framework.component.mvicore

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.whenStarted
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlin.reflect.KProperty1

/**
 * flow部分
 */
fun <T, A> StateFlow<T>.observeState(
    lifecycleOwner: LifecycleOwner,
    prop1: KProperty1<T, A>,
    action: (A) -> Unit
) {
    lifecycleOwner.lifecycleScope.launch {
        lifecycleOwner.whenStarted {
            this@observeState.map {
                StateTuple1(prop1.get(it))
            }.distinctUntilChanged().collect { (a) ->
                action.invoke(a)
            }
        }
    }
}

fun <T, A, B> StateFlow<T>.observeState(
    lifecycleOwner: LifecycleOwner,
    prop1: KProperty1<T, A>,
    prop2: KProperty1<T, B>,
    action: (A, B) -> Unit
) {
    lifecycleOwner.lifecycleScope.launch {
        lifecycleOwner.whenStarted {
            this@observeState.map {
                StateTuple2(prop1.get(it), prop2.get(it))
            }.distinctUntilChanged().collect { (a, b) ->
                action.invoke(a, b)
            }
        }
    }
}

fun <T, A, B, C> StateFlow<T>.observeState(
    lifecycleOwner: LifecycleOwner,
    prop1: KProperty1<T, A>,
    prop2: KProperty1<T, B>,
    prop3: KProperty1<T, C>,
    action: (A, B, C) -> Unit
) {
    lifecycleOwner.lifecycleScope.launch {
        lifecycleOwner.whenStarted{
            this@observeState.map {
                StateTuple3(prop1.get(it), prop2.get(it), prop3.get(it))
            }.distinctUntilChanged().collect { (a, b, c) ->
                action.invoke(a, b, c)
            }
        }
    }
}

fun <T> MutableStateFlow<T>.setState(reducer: T.() -> T) {
    this.value = this.value.reducer()
}

inline fun <T, R> withState(state: StateFlow<T>, block: (T) -> R): R {
    return state.value.let(block)
}

suspend fun <T> SharedFlowEvents<T>.setEvent(vararg values: T) {
    val eventList = values.toList()
    this.emit(eventList)
}

fun <T> SharedFlow<List<T>>.observeEvent(lifecycleOwner: LifecycleOwner, action: (T) -> Unit) {
    lifecycleOwner.lifecycleScope.launchWhenStarted {
        this@observeEvent.collect {
            it.forEach { event ->
                action.invoke(event)
            }
        }
    }
}

typealias SharedFlowEvents<T> = MutableSharedFlow<List<T>>

@Suppress("FunctionName")
fun <T> SharedFlowEvents(): SharedFlowEvents<T> {
    return MutableSharedFlow()
}