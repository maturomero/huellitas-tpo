package com.uade.tpo.demo.repository;

import com.uade.tpo.demo.entity.Order;
import com.uade.tpo.demo.enums.StatusOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    
    // =========================
    // Búsquedas por relaciones
    // =========================
    
    // Buscar órdenes por el id del usuario (propiedad navegada: user.id)
    List<Order> findByUserId(Long userId);
    
    // Con ordenamiento por fecha de creación descendente
    List<Order> findByUserIdOrderByCreatedAtDesc(Long userId);
    
    // ===================
    // Búsquedas por estado
    // ===================
    
    // El estado es un enum (StatusOrder)
    List<Order> findByStatus(StatusOrder status);
    List<Order> findByUserIdAndStatus(Long userId, StatusOrder status);
    List<Order> findByStatusOrderByCreatedAtDesc(StatusOrder status);
    List<Order> findByUserIdAndStatusOrderByCreatedAtDesc(Long userId, StatusOrder status);
    
    // =========================
    // Búsquedas por fecha (Date)
    // =========================
    
    List<Order> findByCreatedAtBetween(Date startDate, Date endDate);
    List<Order> findByUserIdAndCreatedAtBetween(Long userId, Date startDate, Date endDate);
    
    // ==========================
    // Búsquedas por importe ($)
    // ==========================
    
    List<Order> findByTotalPriceGreaterThanEqual(Double minPrice);
    List<Order> findByTotalPriceLessThanEqual(Double maxPrice);
    List<Order> findByTotalPriceBetween(Double minPrice, Double maxPrice);
    
    // =====================
    // Existencia y conteos
    // =====================
    
    boolean existsByUserId(Long userId);
    boolean existsByUserIdAndStatus(Long userId, StatusOrder status);
    
    long countByUserId(Long userId);
    long countByStatus(StatusOrder status);
    long countByUserIdAndStatus(Long userId, StatusOrder status);
    
    // ======================
    // Consultas personalizadas
    // ======================
    
    // Órdenes recientes del usuario desde una fecha dada
    @Query("SELECT o FROM Order o WHERE o.user.id = :userId AND o.createdAt >= :fromDate ORDER BY o.createdAt DESC")
    List<Order> findRecentOrdersByUser(@Param("userId") Long userId, @Param("fromDate") Date fromDate);
    
    // Órdenes del usuario por encima de cierto monto
    @Query("SELECT o FROM Order o WHERE o.user.id = :userId AND o.totalPrice >= :minAmount")
    List<Order> findHighValueOrdersByUser(@Param("userId") Long userId, @Param("minAmount") Double minAmount);
    
    // Total gastado por usuario considerando órdenes APROBADAS
    @Query("SELECT COALESCE(SUM(o.totalPrice), 0) FROM Order o WHERE o.user.id = :userId AND o.status = com.uade.tpo.demo.enums.StatusOrder.APPROVED")
    Double getTotalSpentByUser(@Param("userId") Long userId);
    
    // Órdenes desde una fecha (p. ej., último mes)
    @Query("SELECT o FROM Order o WHERE o.createdAt >= :fromDate")
    List<Order> findOrdersFromDate(@Param("fromDate") Date fromDate);
    
    // Estadísticas por estado: [status, cantidad, suma total]
    @Query("SELECT o.status, COUNT(o), SUM(o.totalPrice) FROM Order o GROUP BY o.status")
    List<Object[]> getOrderStatsByStatus();
    
    // =======================
    // Operaciones de escritura
    // =======================
    
    // Actualizar el estado de una orden por id
    @Modifying
    @Transactional
    @Query("UPDATE Order o SET o.status = :status WHERE o.id = :orderId")
    int updateOrderStatus(@Param("orderId") Long orderId, @Param("status") StatusOrder status);
    
    // =====================
    // Búsquedas avanzadas
    // =====================
    
    // Buscar por múltiples estados
    List<Order> findByStatusIn(List<StatusOrder> statuses);
    List<Order> findByUserIdAndStatusIn(Long userId, List<StatusOrder> statuses);
    
    // Top órdenes por precio total (global y por usuario)
    List<Order> findTop10ByOrderByTotalPriceDesc();
    List<Order> findTop10ByUserIdOrderByTotalPriceDesc(Long userId);
}


