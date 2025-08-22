package com.uade.tpo.demo.repository;

import com.uade.tpo.demo.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    
    // ===== BÚSQUEDAS PRINCIPALES (lo que realmente necesitas) =====
    
    // Buscar órdenes por usuario
    List<Order> findByUserId(Long userId);
    Page<Order> findByUserId(Long userId, Pageable pageable);
    
    // Buscar por estado
    List<Order> findByStatus(String status);
    Page<Order> findByStatus(String status, Pageable pageable);
    
    // Búsquedas combinadas importantes
    List<Order> findByUserIdAndStatus(Long userId, String status);
    Page<Order> findByUserIdAndStatus(Long userId, String status, Pageable pageable);
    
    // Buscar por rango de fechas
    List<Order> findByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate);
    List<Order> findByUserIdAndCreatedAtBetween(Long userId, LocalDateTime startDate, LocalDateTime endDate);
    
    // Buscar por precio mínimo/máximo
    List<Order> findByTotalPriceGreaterThanEqual(BigDecimal minPrice);
    List<Order> findByTotalPriceLessThanEqual(BigDecimal maxPrice);
    List<Order> findByTotalPriceBetween(BigDecimal minPrice, BigDecimal maxPrice);
    
    // ===== BÚSQUEDAS CON ORDENAMIENTO =====
    List<Order> findByUserIdOrderByCreatedAtDesc(Long userId);
    List<Order> findByStatusOrderByCreatedAtDesc(String status);
    List<Order> findByUserIdAndStatusOrderByCreatedAtDesc(Long userId, String status);
    
    // ===== VERIFICACIONES DE EXISTENCIA (solo las necesarias) =====
    boolean existsByUserId(Long userId);
    boolean existsByUserIdAndStatus(Long userId, String status);
    
    // ===== CONTEOS ÚTILES =====
    long countByUserId(Long userId);
    long countByStatus(String status);
    long countByUserIdAndStatus(Long userId, String status);
    
    // ===== CONSULTAS PERSONALIZADAS =====
    
    // Órdenes recientes del usuario
    @Query("SELECT o FROM Order o WHERE o.userId = :userId AND o.createdAt >= :fromDate ORDER BY o.createdAt DESC")
    List<Order> findRecentOrdersByUser(@Param("userId") Long userId, @Param("fromDate") LocalDateTime fromDate);
    
    // Órdenes por encima de cierto monto
    @Query("SELECT o FROM Order o WHERE o.userId = :userId AND o.totalPrice >= :minAmount")
    List<Order> findHighValueOrdersByUser(@Param("userId") Long userId, @Param("minAmount") BigDecimal minAmount);
    
    // Total gastado por usuario
    @Query("SELECT COALESCE(SUM(o.totalPrice), 0) FROM Order o WHERE o.userId = :userId AND o.status = 'COMPLETED'")
    BigDecimal getTotalSpentByUser(@Param("userId") Long userId);
    
    // Órdenes del último mes
    @Query("SELECT o FROM Order o WHERE o.createdAt >= :fromDate")
    List<Order> findOrdersFromLastMonth(@Param("fromDate") LocalDateTime fromDate);
    
    // Estadísticas por estado
    @Query("SELECT o.status, COUNT(o), SUM(o.totalPrice) FROM Order o GROUP BY o.status")
    List<Object[]> getOrderStatsByStatus();
    
    // ===== OPERACIONES DE ACTUALIZACIÓN =====
    
    @Query("UPDATE Order o SET o.status = :status WHERE o.id = :orderId")
    @org.springframework.data.jpa.repository.Modifying
    void updateOrderStatus(@Param("orderId") Long orderId, @Param("status") String status);
    
    // ===== BÚSQUEDAS AVANZADAS =====
    
    // Buscar órdenes por múltiples estados
    List<Order> findByStatusIn(List<String> statuses);
    List<Order> findByUserIdAndStatusIn(Long userId, List<String> statuses);
    
    // Top órdenes por precio
    List<Order> findTop10ByOrderByTotalPriceDesc();
    List<Order> findTop10ByUserIdOrderByTotalPriceDesc(Long userId);
}


