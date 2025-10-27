package com.bleudev.words.util

import com.bleudev.words.custom.data.datafixers.WPair

// Generators
fun <F, S> Collection<F>.toPairsWith(seconds: List<S>, ignoreAssert: Boolean = false): List<WPair<F, S>> {
    if (!ignoreAssert) assert(this.size == seconds.size)
    return ArrayList<WPair<F, S>>().apply { this@toPairsWith.forEachIndexed { i, v ->
        this@apply.add(WPair(v, seconds[i]))
    } }
}

/**
 * Make new [Map] where keys are `this` collection and values are [other] collection
 *
 * @param other Other collection
 * @param ignoreAssert Should this function do not throw `AssertionError` when `this.size != other.size`?
 *
 * @throws AssertionError if `ignoreAssert == false && (this.size != other.size)`
 * */
fun <K, V> Collection<K>.toMapWith(other: List<V>, ignoreAssert: Boolean = false): HashMap<K, V> {
    return HashMap<K, V>().apply {
        this@toMapWith.toPairsWith(other, ignoreAssert).forEach { p ->
            this@apply[p.first] = p.second
        }
    }
}

/**
 * Make [Map]`<K, Pair<FV, SV>>` with given [firsts] values and [seconds] values for [Pair]s
 * */
fun <K, FV, SV> Collection<K>.toMapWith(firsts: List<FV>, seconds: List<SV>, ignoreAssert: Boolean = false): HashMap<K, WPair<FV, SV>> {
    return this.toMapWith(firsts.toPairsWith(seconds, ignoreAssert), ignoreAssert)
}

fun <F, S> Collection<WPair<F, S>>.extract(): WPair<List<F>, List<S>> {
    val firsts = ArrayList<F>()
    val seconds = ArrayList<S>()
    this.forEach { (f, s) -> firsts.add(f); seconds.add(s) }
    return WPair(firsts, seconds)
}
fun <F, S> Collection<WPair<F, S>>.extractFirsts(): List<F> {
    return this.extract().first
}
fun <F, S> Collection<WPair<F, S>>.extractSeconds(): List<S> {
    return this.extract().second
}

// Operations
fun <T> ArrayList<T>.popFirstIf(predicate: (value: T) -> Boolean): T? {
    for (i in 0..<this.size) {
        if (predicate(this[i]))
            return this.removeAt(i)
    }
    return null
}

fun <T> MutableList<T>.addNullable(new: T?): Boolean {
    if (new == null) return false
    if (this.contains(new))
        return false
    return this.add(new)
}
fun <T> MutableList<T>.removeNullable(old: T?): Boolean {
    if (old == null) return false
    return this.remove(old)
}
