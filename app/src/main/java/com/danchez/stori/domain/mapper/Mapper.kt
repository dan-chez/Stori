package com.danchez.stori.domain.mapper

interface Mapper<Input, Output> {
    fun map(input: Input): Output
}
