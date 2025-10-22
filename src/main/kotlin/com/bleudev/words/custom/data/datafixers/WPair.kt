package com.bleudev.words.custom.data.datafixers

import com.mojang.serialization.Codec
import com.mojang.serialization.codecs.RecordCodecBuilder

class WPair<T1, T2>(var first: T1, var second: T2) {

    operator fun component1(): T1 = this.first
    operator fun component2(): T2 = this.second

    operator fun contains(v: Any?): Boolean =
        this.first == v || this.second == v
    override operator fun equals(other: Any?): Boolean {
        if (other != null && other is WPair<T1, T2>)
            return this.first == other.first && this.second == other.second
        return false
    }

    override fun hashCode(): Int =
        31 * (first?.hashCode() ?: 0) + (second?.hashCode() ?: 0)
    override fun toString(): String = "($first, $second)"

    /**
     * Returns a [Pair] representation of [WPair]
     * */
    fun toPair(): Pair<T1, T2> = Pair(first, second)
    /**
     * Returns a [List] with [first] and [second] values
     * */
    fun toList(): List<Any?> = listOf(first, second)

    companion object {
        /**
         * Returns a [WPair]'s [Codec] with codecs of [first] and [second] values
         * */
        fun <T1, T2> CODEC(codec1: Codec<T1>, codec2: Codec<T2>): Codec<WPair<T1, T2>> = RecordCodecBuilder.create { instance ->
            instance.group(
                codec1.fieldOf("first").forGetter(WPair<T1, T2>::first),
                codec2.fieldOf("second").forGetter(WPair<T1, T2>::second)
            ).apply(instance, ::WPair) }
    }

}