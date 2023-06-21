package com.tesla.framework.component.compress.constraint

class Compression {
    internal val constraints: MutableList<Constraint> = mutableListOf()

    fun constraint(constraint: Constraint) {
        constraints.add(constraint)
    }
}