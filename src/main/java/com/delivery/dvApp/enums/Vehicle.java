package com.delivery.dvApp.enums;

/**
 * Defines the mode of transport used by a courier.
 * <p>
 * This enum is utilized to determine delivery speed, range, and
 * capacity constraints during the order assignment process.
 */
public enum Vehicle {
    /** * Human-powered delivery. Best for high-density urban areas
     * with heavy traffic and short distances.
     */
    BICYCLE,

    /** * Standard automobile. Offers the highest capacity and range
     * but is most susceptible to traffic congestion and parking delays.
     */
    CAR,

    /** * Motorized two-wheeler. Provides a balance of speed and
     * maneuverability in heavy traffic.
     */
    MOTORBIKE,

    /** * Electric scooter. A modern, eco-friendly option for fast
     * short-range urban deliveries.
     */
    E_SCOOTER
}
