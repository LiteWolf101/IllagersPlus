package com.litewolf101.utils;

import net.minecraft.entity.Entity;

import java.util.Comparator;

/**
 * Simple distance based comparator implementation.<br>
 * Sorts the given collection of item entity from farthest to nearest.<br>
 * <br>
 * This class was provided for Villagers+ with permission from the author.<br>
 * This class may not be edited, moved or renamed without explicit permission.<br>
 * This header may not be removed.
 *
 * @since 13/06/2019
 * @author KitsuneAlex
 */
public class DistanceComparator<E extends Entity> implements Comparator<E> {

    private final Entity entity;

    public DistanceComparator(Entity entity) {
        this.entity = entity;
    }

    @Override
    public int compare(E e1, E e2) {
        float d1 = e1.getDistance(entity);
        float d2 = e2.getDistance(entity);
        return Float.compare(d1, d2);
    }

}
