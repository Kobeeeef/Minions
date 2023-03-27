package org.shawty.Utilities;

import io.lumine.mythic.api.adapters.AbstractEntity;
import io.lumine.mythic.api.adapters.AbstractLocation;
import org.shawty.Core;

public class PathFinding {

    /**
     * Move the entity to the specified location
     *
     * @param entity
     * @param destination
     */
    public static void moveTo(AbstractEntity entity, AbstractLocation destination)
    {
        Core.getMythicMobs().getVolatileCodeHandler().getAIHandler().navigateToLocation(entity, destination, 1);
    }

    /**
     * Stop the entity at its location
     *
     * @param entity
     */
    public static void stop(AbstractEntity entity)
    {
        moveTo(entity, entity.getLocation());
    }

}