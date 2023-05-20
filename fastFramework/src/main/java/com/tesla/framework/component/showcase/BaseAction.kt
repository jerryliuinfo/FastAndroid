package com.tesla.framework.component.showcase

interface BaseAction<State> {
    fun reduce(state: State): State
}
