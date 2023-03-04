package org.shawty.Utilities;

import org.bukkit.entity.ArmorStand;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.EulerAngle;
import org.shawty.Minions;

public class Animations {
    /**
     The Desciption of the method to explain what the method does
     @param stand  The ArmorStand from minion to animate.
     @param animation The animation to use on this ArmorStand
     @return The amount of ticks it takes to finish this animation
     */
    public static int performAnimation(ArmorStand stand, Animation animation) {
        new BukkitRunnable() {
            int counter = 0;
            final int frames = animation.angleAnimation.length;


            public void run() {
                if (counter >= frames) cancel();
                else {
                    if (animation.type.equals(AnimationType.HEAD)) stand.setHeadPose(animation.angleAnimation[counter]);
                    if (animation.type.equals(AnimationType.LEFT_ARM))
                        stand.setLeftArmPose(animation.angleAnimation[counter]);
                    if (animation.type.equals(AnimationType.RIGHT_ARM))
                        stand.setRightArmPose(animation.angleAnimation[counter]);
                    counter++;
                }
            }
        }.runTaskTimer(Minions.getPlugin(), 0L, 1L);
        return animation.angleAnimation.length;
    }

    public enum Animation {
        HEAD_YAWN(AnimationType.HEAD, new EulerAngle[]{new EulerAngle(6.28, 0.0, 0.0), new EulerAngle(6.25, 0.02, 0.01), new EulerAngle(6.2, 0.04, 0.02), new EulerAngle(6.15, 0.06, 0.03), new EulerAngle(6.1, 0.08, 0.04), new EulerAngle(6.05, 0.1, 0.05), new EulerAngle(6.0, 0.12, 0.06), new EulerAngle(5.95, 0.14, 0.07), new EulerAngle(5.9, 0.16, 0.08), new EulerAngle(5.85, 0.18, 0.09), new EulerAngle(5.8, 0.2, 0.1), new EulerAngle(5.75, 0.22, 0.11), new EulerAngle(5.7, 0.24, 0.12), new EulerAngle(5.6, 0.26, 0.13), new EulerAngle(5.5, 0.3, 0.14), new EulerAngle(5.4, 0.32, 0.15), new EulerAngle(5.3, 0.34, 0.16), new EulerAngle(5.28, 0.34, 0.18), new EulerAngle(5.25, 0.34, 0.2), new EulerAngle(5.25, 0.34, 0.2), new EulerAngle(5.28, 0.34, 0.18), new EulerAngle(5.3, 0.34, 0.16), new EulerAngle(5.4, 0.32, 0.15), new EulerAngle(5.5, 0.3, 0.14), new EulerAngle(5.6, 0.26, 0.13), new EulerAngle(5.7, 0.24, 0.12), new EulerAngle(5.75, 0.22, 0.11), new EulerAngle(5.8, 0.2, 0.1), new EulerAngle(5.85, 0.18, 0.09), new EulerAngle(5.9, 0.16, 0.08), new EulerAngle(5.95, 0.14, 0.07), new EulerAngle(6.0, 0.12, 0.06), new EulerAngle(6.05, 0.1, 0.05), new EulerAngle(6.1, 0.08, 0.04), new EulerAngle(6.15, 0.06, 0.03), new EulerAngle(6.2, 0.04, 0.02), new EulerAngle(6.25, 0.02, 0.01)}),
        RIGHT_ARM_YAWN(AnimationType.RIGHT_ARM, new EulerAngle[]{new EulerAngle(0.03, 0.05, 0.1), new EulerAngle(0.05, 0.1, 0.2), new EulerAngle(0.1, 0.15, 0.3), new EulerAngle(0.12, 0.2, 0.4), new EulerAngle(0.13, 0.26, 0.5), new EulerAngle(0.15, 0.27, 0.6), new EulerAngle(0.18, 0.28, 0.7), new EulerAngle(0.2, 0.29, 0.9), new EulerAngle(0.22, 0.3, 1.0), new EulerAngle(0.23, 0.31, 1.1), new EulerAngle(0.24, 0.32, 1.3), new EulerAngle(0.25, 0.34, 1.5), new EulerAngle(0.26, 0.37, 1.6), new EulerAngle(0.27, 0.4, 1.8), new EulerAngle(0.29, 0.46, 1.9), new EulerAngle(0.3, 0.5, 2.1), new EulerAngle(0.3, 0.52, 2.15), new EulerAngle(0.31, 0.57, 2.2), new EulerAngle(0.32, 0.6, 2.23), new EulerAngle(0.34, 0.57, 2.25), new EulerAngle(0.32, 0.6, 2.23), new EulerAngle(0.31, 0.57, 2.2), new EulerAngle(0.3, 0.52, 2.15), new EulerAngle(0.3, 0.5, 2.1), new EulerAngle(0.29, 0.46, 1.9), new EulerAngle(0.27, 0.4, 1.8), new EulerAngle(0.26, 0.37, 1.6), new EulerAngle(0.25, 0.34, 1.5), new EulerAngle(0.24, 0.32, 1.3), new EulerAngle(0.23, 0.31, 1.1), new EulerAngle(0.22, 0.3, 1.0), new EulerAngle(0.2, 0.29, 0.9), new EulerAngle(0.18, 0.28, 0.7), new EulerAngle(0.13, 0.26, 0.5), new EulerAngle(0.12, 0.2, 0.4), new EulerAngle(0.1, 0.15, 0.3), new EulerAngle(0.05, 0.1, 0.2), new EulerAngle(0.03, 0.05, 0.1)}),
        LEFT_ARM_YAWN(AnimationType.LEFT_ARM, new EulerAngle[]{new EulerAngle(0.03, 0.05, 6.28), new EulerAngle(0.05, 0.1, 6.2), new EulerAngle(0.1, 0.15, 6.1), new EulerAngle(0.12, 0.2, 6.0), new EulerAngle(0.13, 0.26, 5.9), new EulerAngle(0.15, 0.27, 5.8), new EulerAngle(0.18, 0.28, 5.5), new EulerAngle(0.2, 0.29, 5.4), new EulerAngle(0.22, 0.3, 5.3), new EulerAngle(0.23, 0.31, 5.2), new EulerAngle(0.24, 0.32, 5.1), new EulerAngle(0.25, 0.34, 5.0), new EulerAngle(0.26, 0.37, 4.9), new EulerAngle(0.27, 0.4, 4.8), new EulerAngle(0.29, 0.46, 4.7), new EulerAngle(0.3, 0.5, 4.6), new EulerAngle(0.3, 0.52, 4.55), new EulerAngle(0.31, 0.57, 4.5), new EulerAngle(0.32, 0.6, 4.45), new EulerAngle(0.34, 0.57, 4.4), new EulerAngle(0.32, 0.6, 4.45), new EulerAngle(0.31, 0.57, 4.5), new EulerAngle(0.3, 0.52, 4.55), new EulerAngle(0.3, 0.5, 4.6), new EulerAngle(0.29, 0.46, 4.7), new EulerAngle(0.27, 0.4, 4.8), new EulerAngle(0.26, 0.37, 4.9), new EulerAngle(0.25, 0.34, 5.0), new EulerAngle(0.24, 0.32, 5.1), new EulerAngle(0.23, 0.31, 5.2), new EulerAngle(0.22, 0.3, 5.3), new EulerAngle(0.2, 0.29, 5.4), new EulerAngle(0.18, 0.28, 5.5), new EulerAngle(0.15, 0.27, 5.8), new EulerAngle(0.13, 0.26, 5.9), new EulerAngle(0.12, 0.2, 6.0), new EulerAngle(0.1, 0.15, 6.1), new EulerAngle(0.05, 0.1, 6.2)});
        private final EulerAngle[] angleAnimation;
        private final AnimationType type;

        Animation(AnimationType type, EulerAngle[] angleAnimation) {
            this.angleAnimation = angleAnimation;
            this.type = type;
        }

        public EulerAngle[] getAngleAnimation() {
            return angleAnimation;
        }

        public AnimationType getType() {
            return type;
        }
    }

    enum AnimationType {
        HEAD, RIGHT_ARM, LEFT_ARM,
    }
}

