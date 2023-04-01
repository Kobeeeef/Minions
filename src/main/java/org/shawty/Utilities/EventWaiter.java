package org.shawty.Utilities;

import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.shawty.Core;

import java.util.function.Consumer;

public class EventWaiter<T extends Event> implements Listener {
    private final Plugin plugin;
    private int taskId;

    public EventWaiter(Class<T> eventClass, Consumer<T> consumer) {
        this.plugin = Core.getPlugin();
        this.taskId = -1;
        plugin.getServer().getPluginManager().registerEvent(eventClass, this, EventPriority.NORMAL, (listener, event) -> {
            if (eventClass.isInstance(event)) {
                T castedEvent = eventClass.cast(event);
                consumer.accept(castedEvent);
                cancelTask();
            }
        }, plugin, false);
    }

    public void waitForEvent() {
        taskId = new BukkitRunnable() {
            @Override
            public void run() {
                if (done()) {
                    cancelTask();
                }
            }
        }.runTaskTimer(plugin, 0L, 1L).getTaskId();
    }

    public void waitForEvent(Long period, Runnable runnable) {
        taskId = new BukkitRunnable() {
            @Override
            public void run() {
                if (done()) {
                    cancelTask();
                }
            }
        }.runTaskTimer(plugin, 0L, 1L).getTaskId();
        new BukkitRunnable() {
            @Override
            public void run() {
                if (!done()) {
                    cancelTask();
                    runnable.run();
                }
            }
        }.runTaskLater(plugin, period);
    }

    public void waitForEvent(Long period) {
        taskId = new BukkitRunnable() {
            @Override
            public void run() {
                if (done()) {
                    cancelTask();
                }
            }
        }.runTaskTimer(plugin, 0L, 1L).getTaskId();
        new BukkitRunnable() {
            @Override
            public void run() {
                if (!done()) cancelTask();


            }
        }.runTaskLater(plugin, period);
    }

    private boolean done() {
        return taskId != -1 && !plugin.getServer().getScheduler().isQueued(taskId);
    }

    private void cancelTask() {
        HandlerList.unregisterAll(this);
        plugin.getServer().getScheduler().cancelTask(taskId);
        taskId = -1;
    }
}
