package io.github.justfoxx.teacup.v1.event

import io.github.justfoxx.teacup.v1.registry.SetRegistry


/**
 * An implementation of the Event interface that can be used when both registry and invoker are different type.
 * @param <V> The type of the value passed to the event.
 * @param <I> The type of the invoker lambda used to trigger the event.
 */
class EventKey<V, I>(
    registry: SetRegistry<V> = SetRegistry(),
    invoker: (Set<V>) -> I
) : AbstractEvent<V, I>(registry, invoker) {

    /**
     * Returns a set containing all values registered in the event.
     * @return The set of registered values.
     */
    override fun getAll(): Set<V> {
        return registry.all
    }

    /**
     * Returns the invoker lambda used to trigger the event.
     * @return The invoker lambda.
     */
    override fun invoker(): I {
        return invoker.invoke(getAll())
    }

    /**
     * Called when the event is triggered with the given value.
     * @param value The value passed to the event.
     */
    override fun onEvent(value: V) {
        registry.add(value)
    }
}